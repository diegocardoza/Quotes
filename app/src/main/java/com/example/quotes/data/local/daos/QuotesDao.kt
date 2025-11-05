package com.example.quotes.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quotes.data.local.entities.QUOTE_TABLE_NAME
import com.example.quotes.data.local.entities.QuoteEntity

@Dao
interface QuotesDao {

    @Query("SELECT * FROM $QUOTE_TABLE_NAME ORDER BY id ASC")
    suspend fun getQuotes(): List<QuoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotes: List<QuoteEntity>)

}