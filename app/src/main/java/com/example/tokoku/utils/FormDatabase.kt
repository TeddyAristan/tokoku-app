package com.example.tokoku.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tokoku.entity.Customers
import com.example.tokoku.entity.Form


@Database(
    entities= [Form::class, Customers::class],
    version = 1, exportSchema = false
)
abstract class FormDatabase : RoomDatabase() {
    abstract val dao:ThingDao
}