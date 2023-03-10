package com.nong.amazingnews.fragment.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nong.amazingnews.databinding.ItemEveryNewsListBinding
import com.nong.amazingnews.network.Articles

class EveryNewsListViewHolder(
    private val binding: ItemEveryNewsListBinding,
    onItemClickListener: EveryNewsListAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init{
        itemView.setOnClickListener { onItemClickListener.onClick(it, adapterPosition) }
    }

    fun bind(articles: Articles) {
        Glide.with(this@EveryNewsListViewHolder.itemView.context).load(articles.urlToImage).into(binding.newsImage)
        binding.newsTitle.text = articles.title
        binding.newsPublishedAt.text = articles.publishedAt
    }
}