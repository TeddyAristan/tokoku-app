package com.example.tokoku.model.usecase

import android.net.Uri

class ValidationImage {
    fun execute(imageUri: Uri?):ValidationResult{
        if(imageUri == null || imageUri.toString()==""){
            return ValidationResult(
                successful = false,
                errorMessage = "Foto barang tidak boleh kosong"
            )
        }
        return ValidationResult(successful = true)

    }
}