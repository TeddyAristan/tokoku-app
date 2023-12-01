package com.example.tokoku.model.usecase

class ValidationNominalPayment {
    fun execute(nominalPayment:String):ValidationResult{
        if(nominalPayment.isBlank() || nominalPayment==""){
            return ValidationResult(
                successful = false,
                errorMessage = "Nominal bill tidak boleh kosong"
            )
        }
        return ValidationResult(successful = true)
    }
}