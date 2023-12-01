package com.example.tokoku.model.usecase

class ValidationMinPrice {
    fun execute(minPrice:String):ValidationResult{
        if(minPrice.isBlank() || minPrice.isEmpty()){
            return ValidationResult(
                successful = false,
                errorMessage = "Harga batas bawah tidak boleh kosong"
            )
        }
        return ValidationResult(successful = true)
    }
}