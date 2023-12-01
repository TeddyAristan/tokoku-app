package com.example.tokoku.model.usecase

class ValidationPaymentStatus {
    fun execute(paymentStatus:String):ValidationResult{
        if(paymentStatus.isBlank() || paymentStatus==""){
            return ValidationResult(
                successful = false,
                errorMessage = "Status bill tidak boleh kosong"
            )
        }
        return ValidationResult(successful = true)
    }
}