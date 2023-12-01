package com.example.tokoku.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokoku.entity.Customers
import com.example.tokoku.entity.Form
import com.example.tokoku.model.usecase.*
import com.example.tokoku.utils.ThingDao
import com.example.tokoku.utils.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class FormViewModel(
    private val dao: ThingDao
):ViewModel(){
    private val validateName:ValidationName = ValidationName()
    private val validateType:ValidationType = ValidationType()
    private val validateQuantity:ValidationQuantity = ValidationQuantity()
    private val validateMinPrice:ValidationMinPrice = ValidationMinPrice()
    private val validateMaxPrice:ValidationMaxPrice = ValidationMaxPrice()
    private val validateImageUri:ValidationImage = ValidationImage()
    private val validateCustomerName:ValidationCustomerName = ValidationCustomerName()
    private val validatePaymentStatus:ValidationPaymentStatus = ValidationPaymentStatus()
    private val validateNominalPayment:ValidationNominalPayment = ValidationNominalPayment()
    private val validatePaymentDate:ValidationPaymentDate = ValidationPaymentDate()

    private val _state = MutableStateFlow( FormState() )

    private val _sortType = MutableStateFlow(SortType.NAME)
    private val _forms = _sortType.flatMapLatest { sortType ->
        when(sortType){
            SortType.NAME -> dao.getAllDataOrderedByName()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _searchText = MutableStateFlow("")

    private val searchResults = _searchText.flatMapLatest{text->
        if(text.isBlank() ){
            dao.getAllDataOrderedByName()
        }else{
            dao.searchData("%${_searchText.value}%")
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private var customerState:Flow<List<Customers>> =  dao.getAllDataCustomersOrderedByName()

    val state =combine(_state,searchResults,customerState){state,forms,custom->
        state.copy(
            forms = forms,
            customers = custom
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),FormState())


    fun onEvent(event: FormEvent){
        when(event){
            FormEvent.ShowDialog->{
                _state.update { it.copy(isAddingCustomer = true) }
            }
            FormEvent.HideDialog->{
                _state.update { it.copy(isAddingCustomer = false) }
                clearCustomerData()
            }

            is FormEvent.DeleteData->{
                viewModelScope.launch{
                    dao.deleteFormData(event.form)
                }
                clearFormData()
            }

            is FormEvent.SortData ->{
                _sortType.value = event.sortType
            }
            is FormEvent.NameChanged ->{
                _state.update { it.copy(
                    name = event.name,
                    nameError = null
                ) }
            }
            is FormEvent.TypeChanged ->{
                _state.update { it.copy(
                    type = event.type,
                    typeError = null
                ) }
            }
            is FormEvent.QuantityChanged ->{
                _state.update { it.copy(
                    quantity = event.quantity,
                    quantityError = null
                ) }
            }
            is FormEvent.MinPriceChanged ->{
                _state.update { it.copy(
                    minPrice = event.minPrice,
                    minPriceError = null
                ) }
            }
            is FormEvent.MaxPriceChanged ->{
                _state.update { it.copy(
                    maxPrice = event.maxPrice,
                    maxPriceError = null
                ) }
            }
            is FormEvent.ImageUriChanged ->{
                _state.update { it.copy(
                    imageUri = event.imageUri,
                    imageUriError = null
                ) }
                Log.d("data image url:","${event.imageUri}")
            }
            is FormEvent.SearchWordChanged->{
                _state.update { it.copy(
                    searchText = event.searchWord
                ) }
            }
            is FormEvent.SearchWord->{
                _searchText.value= _state.value.searchText
            }

            is FormEvent.CustomerNameChanged->{
                _state.update { it.copy(
                    customerName = event.customerName
                ) }
            }
            is FormEvent.PaymentStatusChanged->{_state.update { it.copy(paymentStatus = event.paymentStatus) }}
            is FormEvent.NominalPaymentChanged->{_state.update { it.copy(nominalPayment = event.nominalPayment) }}
            is FormEvent.PaymentDateChanged->{_state.update { it.copy(paymentDate = event.paymentDate) }}

            FormEvent.Submit ->{ submitData() }
            FormEvent.SubmitCustomerData->{
                val validated = validateCustomerData()
                if(validated == "error"){
                    return
                }else{
                    val customer = Customers(customerName = _state.value.customerName, nominalPayment = _state.value.nominalPayment, paymentStatus = _state.value.paymentStatus,
                    paymentDate = _state.value.paymentDate)
                    viewModelScope.launch { dao.upsertKasbonData(customer) }
                    _state.update { it.copy(isAddingCustomer = false)}
                    clearCustomerData()
                }
            }
            FormEvent.ShowEditDialog->{
                _state.update { it.copy(isEditingCustomer = true) }
            }
            FormEvent.HideEditDialog->{
                _state.update { it.copy(isEditingCustomer = false) }
                clearCustomerData()
            }
            is FormEvent.EditCustomerData->{
                val validated = validateCustomerData()
                if(validated != "error"){
                    val customer = Customers(id = event.customerID, customerName = _state.value.customerName, nominalPayment = _state.value.nominalPayment,
                    paymentStatus = _state.value.paymentStatus, paymentDate = _state.value.paymentDate)
                    viewModelScope.launch { dao.updateKasbonData(customer) }
                    _state.update { it.copy(isEditingCustomer = false)}
                    clearCustomerData()
                }
            }
            is FormEvent.DeleteCustomerData->{
                viewModelScope.launch { dao.deleteKasbonData(event.customer) }
                _state.update { it.copy(isEditingCustomer = false)}
                clearCustomerData()
            }
            is FormEvent.EditData->{
                val validated = validateData()
                if(validated != "error"){
                    val resultForm = Form(id=event.formId, name = _state.value.name, type = _state.value.type, quantity = _state.value.quantity
                    , minPrice = _state.value.minPrice, maxPrice = _state.value.maxPrice, imageURI = _state.value.imageUri.toString()
                    )
                    viewModelScope.launch {
                        dao.updateFormData(resultForm)
                    }
                    clearFormData()
                }
            }

            FormEvent.Clear ->{clearFormData()}
        }
    }
    private fun clearFormData(){
        _state.update { it.copy(
            name = "",type = "",quantity = "",minPrice = "",maxPrice = "",imageUri = null,
            nameError=null,typeError=null,quantityError=null,minPriceError=null,maxPriceError=null,imageUriError=null
        ) }
    }
    private fun clearCustomerData(){
        _state.update { it.copy(
            customerName = "", nominalPayment = "", paymentDate = "", paymentStatus = "",
            customerNameError = null, nominalPaymentError = null, paymentStatusError = null, paymentDateError = null
        ) }
    }
    private fun validateCustomerData(): String{
        val nameResult = validateCustomerName.execute(_state.value.customerName)
        val nominalResult = validateNominalPayment.execute(_state.value.nominalPayment)
        val payDate = validatePaymentDate.execute(_state.value.paymentDate)
        val payStatus = validatePaymentStatus.execute(_state.value.paymentStatus)
        val hasError = listOf(
            nameResult,nominalResult,payDate,payStatus
        ).any{!it.successful}
        if(hasError){
            _state.update{it.copy(
                customerNameError = nameResult.errorMessage,
                nominalPaymentError = nominalResult.errorMessage,
                paymentDateError = payDate.errorMessage,
                paymentStatusError = payStatus.errorMessage
            )}
            return "error"
        }
        return "validated"
    }
    private fun validateData(): String {
        val nameResult = validateName.execute(_state.value.name)
        val typeResult = validateType.execute(_state.value.type)
        val quantityResult = validateQuantity.execute(_state.value.quantity)
        val minPriceResult = validateMinPrice.execute(_state.value.minPrice)
        val maxPriceResult = validateMaxPrice.execute(_state.value.maxPrice)
        val imageUriResult = validateImageUri.execute(_state.value.imageUri)

        val hasError = listOf(
            nameResult,typeResult,quantityResult,minPriceResult,maxPriceResult,imageUriResult
        ).any{!it.successful}

        if(hasError){
            _state.update { it.copy(
                nameError = nameResult.errorMessage,
                typeError = typeResult.errorMessage,
                quantityError = quantityResult.errorMessage,
                minPriceError = minPriceResult.errorMessage,
                maxPriceError = maxPriceResult.errorMessage,
                imageUriError = imageUriResult.errorMessage
            ) }
            return "error"
        }
        return "validated"
    }
    private fun submitData() {
        val validated = validateData()
        if(validated == "error"){
            return
        }else{
            val form = Form(name = _state.value.name, type = _state.value.type, quantity = _state.value.quantity,
                minPrice = _state.value.minPrice, maxPrice = _state.value.maxPrice, imageURI = _state.value.imageUri.toString())
            viewModelScope.launch {
                dao.upsertFormData(form)
            }
            clearFormData()
        }
    }
}