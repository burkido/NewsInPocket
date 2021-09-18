package com.example.newsinpocket.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsinpocket.model.news.Article
import com.example.newsinpocket.model.news.NewsResponse
import com.example.newsinpocket.repository.NewsRepository
import com.example.newsinpocket.util.Constants
import com.example.newsinpocket.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    /*For getting breaking news*/
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    /*For getting search news*/
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null


    init {
        getBreakingNews("tr")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun getSearchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.getSearchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body().let { resultMessage ->
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultMessage
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultMessage?.articles
                    newArticles?.let {
                        oldArticles?.addAll(it)
                    }
                }
                return Resource.Success(breakingNewsResponse ?: resultMessage)
            }
        }

        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body().let { resultMessage ->
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultMessage
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultMessage?.articles
                    newArticles?.let {
                        oldArticles?.addAll(it)
                    }
                }
                return Resource.Success(searchNewsResponse ?: resultMessage)
            }
        }

        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.articleInsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}