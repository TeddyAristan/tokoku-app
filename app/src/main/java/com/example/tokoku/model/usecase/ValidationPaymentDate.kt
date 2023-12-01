package com.example.tokoku.model.usecase

class ValidationPaymentDate {
    fun execute(paymentDate:String):ValidationResult{
        if(paymentDate.isBlank() || paymentDate==""){
            return ValidationResult(
                successful = false,
                errorMessage = "Tanggal bill tidak boleh kosong"
            )
        }
        return ValidationResult(successful = true)
    }
}