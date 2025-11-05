package com.example.quotes.data.helper

import com.example.quotes.data.network.model.QuoteDTO

object QuoteDTOHelper {

    fun simpleQuoteDTO(): QuoteDTO = buildQuoteDTOList().first()

    fun buildQuoteDTOList(): List<QuoteDTO> = listOf(
        QuoteDTO(
            id = 1,
            author = "Desarrollador Ágil",
            quote = "El código limpio es la mejor documentación."
        ),
        QuoteDTO(
            id = 2,
            author = "Guru del Testing",
            quote = "Si no está testeado, está roto."
        ),
        QuoteDTO(
            id = 3,
            author = "Filósofo del Debugging",
            quote = "Un error bien depurado es un paso hacia la sabiduría."
        ),
        QuoteDTO(
            id = 4,
            author = "Arquitecto de Sistemas",
            quote = "La simplicidad es la máxima sofisticación en el diseño."
        ),
        QuoteDTO(
            id = 5,
            author = "Product Owner",
            quote = "Entrega valor frecuentemente y escucha al usuario."
        ),
    )

}