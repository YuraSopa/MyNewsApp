package com.example.mnewsapp.ui

import androidx.fragment.app.Fragment
import com.example.mnewsapp.models.Article

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {
    fun launchBreakingNewsFragment(articles: List<Article>)
    fun launchSearchNewsFragment()
    fun launchArticleFragment(article: Article)
}