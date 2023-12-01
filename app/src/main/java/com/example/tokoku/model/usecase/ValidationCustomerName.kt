package com.example.tokoku.model.usecase

class ValidationCustomerName {
    fun execute(customerName:String):ValidationResult{
        if(customerName.isBlank() || customerName == ""){
            return ValidationResult(
                successful = false,
                errorMessage = "Nama Pelanggan tidak boleh kosong"
            )
        }
        return ValidationResult(successful = true)
    }
}
