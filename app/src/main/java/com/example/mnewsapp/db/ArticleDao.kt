package com.example.mnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.mnewsapp.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: Article): Long

    @Update
    suspend fun update(article: Article)

    @Transaction
    suspend fun upsert(article: Article) {
        val existingArticle = getArticleByUrl(article.url ?: "")
        if (existingArticle == null) {
            insert(article)
        } else {
            article.id = existingArticle.id
            update(article)
        }
    }

    @Query("SELECT * FROM articles WHERE url = :url")
    suspend fun getArticleByUrl(url: String): Article?

    @Query("SELECT * FROM articles ORDER BY id")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}