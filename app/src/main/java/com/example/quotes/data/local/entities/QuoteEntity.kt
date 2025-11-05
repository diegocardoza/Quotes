package com.example.quotes.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.quotes.domain.model.QuoteModel

const val QUOTE_TABLE_NAME = "quote_table"

@Entity(
    tableName = QUOTE_TABLE_NAME,
    primaryKeys = ["id"]
)
data class QuoteEntity(
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("author")
    val author: String,
    @ColumnInfo("quote")
    val quote: String
)

fun QuoteEntity.toQuoteModel(): QuoteModel = QuoteModel(id = id, author = author, quote = quote)

fun List<QuoteEntity>.toQuoteModels(): List<QuoteModel> = map { it.toQuoteModel() }

fun QuoteModel.toQuoteEntity(): QuoteEntity = QuoteEntity(id = id, author = author, quote = quote)

fun List<QuoteModel>.toQuoteEntities(): List<QuoteEntity> = map { it.toQuoteEntity() }
