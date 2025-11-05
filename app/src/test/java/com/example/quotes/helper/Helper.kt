package com.example.quotes.helper

import java.io.InputStreamReader

object Helper {

    fun readFileResource(filename: String): String {
        val input = Helper::class.java.getResourceAsStream(filename)
        val builder = StringBuilder()
        val reader = InputStreamReader(input, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }

}