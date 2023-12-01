package com.example.tokoku.model

import android.net.Uri
import com.example.tokoku.entity.Customers
import com.example.tokoku.entity.Form
import com.example.tokoku.utils.SortType

data class FormState(
    val forms: List<Form> = emptyList(),
    val sortType:SortType = SortType.NAME,
    val searchText:String= "",
    val successText:String="",

    val customers:List<Customers> = emptyList(),
    val customerName:String ="",
    val customerNameError:String?=null,
    val paymentStatus:String = "",
    val paymentStatusError:String?=null,
    val nominalPayment:String="",
    val nominalPaymentError:String?=null,
    val paymentDate:String="",
    val paymentDateError:String?=null,
    val isAddingCustomer:Boolean = false,
    val isEditingCustomer:Boolean = false,

    val name:String = "",
    val nameError:String?=null,
    val type:String = "",
    val typeError:String?=null,
    val quantity:String = "",
    val quantityError:String?=null,
    val minPrice: String = "",
    val minPriceError:String?=null,
    val maxPrice: String = "",
    val maxPriceError:String?=null,
    val imageUri: Uri? = null,
    val imageUriError:String?=null
)
