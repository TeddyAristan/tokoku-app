package com.example.tokoku.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "customers")
@Serializable
data class Customers(
    val customerName:String,
    val paymentStatus:String,
    val nominalPayment:String,
    val paymentDate:String,
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
)
