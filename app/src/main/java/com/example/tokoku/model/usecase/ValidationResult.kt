package com.example.tokoku.model.usecase

data class ValidationResult(
    val successful: Boolean,
    val errorMessage:String? = null
)