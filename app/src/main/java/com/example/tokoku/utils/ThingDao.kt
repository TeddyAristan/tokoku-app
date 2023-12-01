package com.example.tokoku.utils

import androidx.room.*
import com.example.tokoku.entity.Customers
import com.example.tokoku.entity.Form
import kotlinx.coroutines.flow.Flow

@Dao
interface ThingDao {
    @Upsert
    suspend fun upsertFormData(form:Form)

    @Upsert
    suspend fun upsertKasbonData(customers: Customers)

    @Update
    suspend fun updateFormData(form: Form)

    @Update
    suspend fun updateKasbonData(customers: Customers)

    @Delete
    suspend fun deleteFormData(form:Form)

    @Delete
    suspend fun deleteKasbonData(customers: Customers)

    @Query("SELECT * FROM customers ORDER BY id ASC")
    fun getAllDataCustomersOrderedByName():Flow<List<Customers>>

    @Query("SELECT * FROM forms ORDER BY name ASC")
    fun getAllDataOrderedByName(): Flow<List<Form>>

    @Query("SELECT * FROM forms WHERE name LIKE :query COLLATE NOCASE ORDER BY name ASC")
    fun searchData(query:String): Flow<List<Form>>
}