package com.example.newsinpocket.model.news

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//this article is a table in database
@Entity(
    tableName = "articles"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var article_id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable