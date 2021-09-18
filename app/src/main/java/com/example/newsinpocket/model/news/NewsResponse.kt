package com.example.newsinpocket.model.news

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)