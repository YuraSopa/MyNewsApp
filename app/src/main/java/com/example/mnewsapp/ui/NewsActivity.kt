package com.example.mnewsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mnewsapp.R
import com.example.mnewsapp.databinding.ActivityNewsBinding
import com.example.mnewsapp.db.ArticleDatabase
import com.example.mnewsapp.repository.NewsRepository

class NewsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityNewsBinding.inflate(layoutInflater)
    }

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val newsViewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(
            this,
            newsViewModelProviderFactory
        )[NewsViewModel::class.java]

//        val navController = findNavController(R.id.newsNavHostFragment)
//        binding.bottomNavigationView.setupWithNavController(navController)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}