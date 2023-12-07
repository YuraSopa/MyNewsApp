package com.example.mnewsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.mnewsapp.R
import com.example.mnewsapp.databinding.FragmentArticleBinding
import com.example.mnewsapp.extension.parcelable
import com.example.mnewsapp.models.Article
import com.example.mnewsapp.ui.NewsActivity
import com.example.mnewsapp.ui.NewsViewModel
import com.example.mnewsapp.util.Constants.Companion.EXTRA_ARTICLE
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var viewModel: NewsViewModel
    private val tag = "ArticleFragment"
    private lateinit var article: Article

    private var _binding: FragmentArticleBinding? = null
    private val binding: FragmentArticleBinding
        get() = _binding ?: throw RuntimeException("$tag is null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        article = arguments?.parcelable<Article>(EXTRA_ARTICLE)
            ?: throw IllegalArgumentException("article is null")

        _binding = FragmentArticleBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        viewModel.getSavedNews().observe(viewLifecycleOwner) {
            for (savedArticle in it) {
                if (savedArticle.url == article.url) {
                    binding.fab.setImageResource(R.drawable.ic_favorite_added)
                }
            }
        }

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }

        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)

            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance(article: Article): ArticleFragment {
            val args = Bundle()
            args.putParcelable(EXTRA_ARTICLE, article)
            val fragment = ArticleFragment()
            fragment.arguments = args
            return fragment
        }
    }
}