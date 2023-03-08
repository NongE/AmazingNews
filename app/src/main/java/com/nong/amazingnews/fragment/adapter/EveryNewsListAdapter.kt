package com.nong.amazingnews.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.nong.amazingnews.databinding.ItemEveryNewsListBinding
import com.nong.amazingnews.network.Articles

class EveryNewsListAdapter : PagingDataAdapter<Articles, EveryNewsListViewHolder>(diffCallback) {

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: EveryNewsListViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EveryNewsListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return EveryNewsListViewHolder(
            ItemEveryNewsListBinding.inflate(layoutInflater, parent, false),
            onItemClickListener
        )
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Articles>() {
            override fun areItemsTheSame(
                oldItem: Articles,
                newItem: Articles
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: Articles,
                newItem: Articles
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}