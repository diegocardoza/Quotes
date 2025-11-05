package com.example.quotes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quotes.data.local.daos.QuotesDao
import com.example.quotes.data.local.entities.QuoteEntity

@Database(
    entities = [QuoteEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getQuoteDao(): QuotesDao

}