package com.example.tokoku.model

import android.net.Uri
import com.example.tokoku.entity.Customers
import com.example.tokoku.entity.Form
import com.example.tokoku.utils.SortType

sealed interface FormEvent{
    data class NameChanged(val name:String):FormEvent
    data class TypeChanged(val type:String):FormEvent
    data class QuantityChanged(val quantity:String):FormEvent
    data class MinPriceChanged(val minPrice:String):FormEvent
    data class MaxPriceChanged(val maxPrice:String):FormEvent
    data class ImageUriChanged(val imageUri: Uri?):FormEvent
    data class SearchWordChanged(val searchWord:String):FormEvent

    object Submit:FormEvent
    object Clear:FormEvent

    object SearchWord:FormEvent

    data class SortData(val sortType:SortType):FormEvent
    data class DeleteData(val form:Form):FormEvent
    data class EditData(val formId: Long):FormEvent

    data class CustomerNameChanged(val customerName:String):FormEvent
    data class PaymentStatusChanged(val paymentStatus:String):FormEvent
    data class NominalPaymentChanged(val nominalPayment:String):FormEvent
    data class PaymentDateChanged(val paymentDate:String):FormEvent
    data class EditCustomerData(val customerID:Long):FormEvent
    data class DeleteCustomerData(val customer:Customers):FormEvent

    object SubmitCustomerData:FormEvent
    object ShowDialog:FormEvent
    object HideDialog:FormEvent

    object ShowEditDialog:FormEvent
    object HideEditDialog:FormEvent
}
