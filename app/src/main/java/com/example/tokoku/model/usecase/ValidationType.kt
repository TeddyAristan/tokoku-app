package com.example.tokoku.model.usecase

class ValidationType {
    fun execute(type:String):ValidationResult{
        if(type.isBlank() || type.isEmpty()){
            return ValidationResult(
                successful = false,
                errorMessage = "Tipe Kemasan barang tidak boleh kosong"
            )
        }
        return ValidationResult(successful = true)
    }
}