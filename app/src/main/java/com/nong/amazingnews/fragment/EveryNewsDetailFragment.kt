package com.nong.amazingnews.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.nong.amazingnews.R
import com.nong.amazingnews.databinding.FragmentEveryNewsBinding
import com.nong.amazingnews.databinding.FragmentEveryNewsDetailBinding
import com.nong.amazingnews.network.Articles

class EveryNewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentEveryNewsDetailBinding
    private lateinit var receivedArticles: Articles

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEveryNewsDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(receivedArticles.urlToImage).into(binding.newsImage)
        binding.newsTitle.text = receivedArticles.title
        binding.newsPublishedAt.text = receivedArticles.publishedAt
        binding.newsAuthor.text = receivedArticles.author
        binding.newsContent.text = receivedArticles.content

        binding.newsOriginalButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(receivedArticles.url)))
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        receivedArticles = if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("news_data", Articles::class.java)
        } else {
            arguments?.getParcelable<Articles>("news_data")
        }!!
    }
}