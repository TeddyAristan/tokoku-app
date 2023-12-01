package com.example.tokoku.model.usecase

class ValidationName {
    fun execute(name:String):ValidationResult{
        if(name.isBlank() || name.isEmpty()){
            return ValidationResult(
                successful = false,
                errorMessage = "Nama barang tidak boleh kosong"
            )
        }
        return ValidationResult(successful = true)
    }
}