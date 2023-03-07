package com.nong.amazingnews.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.nong.amazingnews.R
import com.nong.amazingnews.databinding.FragmentEveryNewsListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EveryNewsListFragment : Fragment() {

    private lateinit var binding: FragmentEveryNewsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEveryNewsListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(3000L)
            binding.shimmerEveryNewsRecyclerView.stopShimmer()
            binding.shimmerEveryNewsRecyclerView.visibility = View.GONE
        }
    }
}