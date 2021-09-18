package com.example.newsinpocket.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsinpocket.model.news.Article

//room knows that we have function here
@Dao
interface ArticleDao {

    //varsa replace
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun articleInsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}