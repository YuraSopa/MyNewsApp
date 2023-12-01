package com.example.mnewsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.mnewsapp.databinding.ItemArticlePreviewBinding
import com.example.mnewsapp.models.Article

class NewsAdapter : Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(
        val binding: ItemArticlePreviewBinding
    ) : ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticlePreviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentArticle = differ.currentList[position]
        with(holder.binding) {
            Glide.with(root).load(currentArticle.urlToImage).into(ivArticleImage)
            tvSource.text = currentArticle.source.name
            tvTitle.text = currentArticle.title
            tvDescription.text = currentArticle.description
            tvPublishedAt.text = currentArticle.publishedAt
            setOnItemClickListener {
                onItemClickListener
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: ((Article) -> Unit)) {
        onItemClickListener = listener
    }
}