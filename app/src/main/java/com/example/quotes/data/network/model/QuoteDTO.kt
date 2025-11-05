package com.example.quotes.data.network.model

import com.google.gson.annotations.SerializedName

data class QuoteDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("quote")
    val quote: String
)