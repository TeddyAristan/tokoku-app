package com.example.tokoku.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.serialization.Serializable

@Entity(tableName = "forms")
@Serializable
data class Form(
    val name:String,
    val type:String,
    val quantity:String,
    val minPrice:String,
    val maxPrice:String,
    val imageURI: String,
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
)