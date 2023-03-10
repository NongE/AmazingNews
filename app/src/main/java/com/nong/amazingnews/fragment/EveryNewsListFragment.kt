package com.nong.amazingnews.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.nong.amazingnews.EveryNewsViewModel
import com.nong.amazingnews.R
import com.nong.amazingnews.databinding.FragmentEveryNewsListBinding
import com.nong.amazingnews.fragment.adapter.EveryNewsListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EveryNewsListFragment : Fragment() {

    private lateinit var binding: FragmentEveryNewsListBinding
    private lateinit var adapter: EveryNewsListAdapter

    private val viewModel: EveryNewsViewModel by lazy {
        ViewModelProvider(
            owner = this
        )[EveryNewsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEveryNewsListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EveryNewsListAdapter().apply {
            addLoadStateListener {
                when {
                    it.source.refresh is LoadState.Loading -> {
                        binding.shimmerEveryNewsRecyclerView.startShimmer()
                        binding.shimmerEveryNewsRecyclerView.visibility = View.VISIBLE
                    }
                    it.source.refresh is LoadState.NotLoading -> {
                        binding.shimmerEveryNewsRecyclerView.stopShimmer()
                        binding.shimmerEveryNewsRecyclerView.visibility = View.GONE
                    }
                }
            }

            setOnItemClickListener(object : EveryNewsListAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {
                    val fragmentTransaction = parentFragmentManager.beginTransaction()
                    val fragment = EveryNewsDetailFragment().apply {
                        arguments = Bundle().apply {
                            putParcelable("news_data", adapter.snapshot()[position])
                        }
                    }

                    fragmentTransaction.setCustomAnimations(
                        com.google.android.material.R.anim.m3_bottom_sheet_slide_in,
                        com.google.android.material.R.anim.m3_bottom_sheet_slide_in,
                        com.google.android.material.R.anim.m3_bottom_sheet_slide_out,
                        com.google.android.material.R.anim.m3_bottom_sheet_slide_out
                    )

                    fragmentTransaction.add(R.id.every_news_container, fragment, "detailNews")
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.show(fragment)
                    fragmentTransaction.commit()
                }
            })
        }

        binding.everyNewsRecyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.everyNewsFlow.collectLatest { adapter.submitData(it) }
        }
    }
}