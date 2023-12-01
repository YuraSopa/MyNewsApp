package com.example.mnewsapp.repository

import com.example.mnewsapp.api.RetrofitInstance
import com.example.mnewsapp.db.ArticleDatabase

class NewsRepository(
    var db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)
}