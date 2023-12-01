package com.example.tokoku.model.usecase

class ValidationMaxPrice {
    fun execute(maxPrice:String):ValidationResult{
        if(maxPrice.isBlank() || maxPrice.isEmpty()){
            return ValidationResult(
                successful = false,
                errorMessage = "Harga batas atas tidak boleh kosong"
            )
        }
        return ValidationResult(successful = true)
    }
}