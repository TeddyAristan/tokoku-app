package com.example.tokoku.model.usecase

class ValidationQuantity {
    fun execute(quantity:String):ValidationResult{
        if(quantity.isBlank() || quantity.isEmpty()){
            return ValidationResult(
                successful = false,
                errorMessage = "Jumlah barang tidak boleh kosong"
            )
        }
        return ValidationResult(successful = true)
    }
}