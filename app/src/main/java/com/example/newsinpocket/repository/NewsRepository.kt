package com.example.newsinpocket.repository

import com.example.newsinpocket.api.RetrofitInstance
import com.example.newsinpocket.model.news.Article
import com.example.newsinpocket.room.ArticleDatabase

class NewsRepository(
    private val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun getSearchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.getSearchForNews(searchQuery, pageNumber)

    suspend fun articleInsert(article: Article) = db.getArticleDao().articleInsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}