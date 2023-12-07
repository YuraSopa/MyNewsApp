package com.example.mnewsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mnewsapp.R
import com.example.mnewsapp.databinding.ActivityNewsBinding
import com.example.mnewsapp.db.ArticleDatabase
import com.example.mnewsapp.models.Article
import com.example.mnewsapp.repository.NewsRepository
import com.example.mnewsapp.ui.fragments.ArticleFragment
import com.example.mnewsapp.ui.fragments.BreakingNewsFragment
import com.example.mnewsapp.ui.fragments.SavedNewsFragment
import com.example.mnewsapp.ui.fragments.SearchNewsFragment

class NewsActivity : AppCompatActivity(), Navigator {

    private val binding by lazy {
        ActivityNewsBinding.inflate(layoutInflater)
    }

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val newsRepository = NewsRepository(ArticleDatabase(this))
        val newsViewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        viewModel = ViewModelProvider(
            this,
            newsViewModelProviderFactory
        )[NewsViewModel::class.java]

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.newsNavHostFragment, BreakingNewsFragment())
                .commit()
        }

//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.breakingNewsFragment -> launchFragment(BreakingNewsFragment())
                R.id.savedNewsFragment -> launchFragment(SavedNewsFragment())
                R.id.searchNewsFragment -> launchFragment(SearchNewsFragment())
                else -> {}
            }
            true
        }
    }

    override fun launchBreakingNewsFragment(articles: List<Article>) {
        TODO("Not yet implemented")
    }


    override fun launchSearchNewsFragment() {
        launchFragment(SearchNewsFragment())
    }

    override fun launchArticleFragment(article: Article) {
        launchFragment(ArticleFragment.newInstance(article))
    }



    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .addToBackStack(null)
            .replace(R.id.newsNavHostFragment, fragment)
            .commit()
    }

}