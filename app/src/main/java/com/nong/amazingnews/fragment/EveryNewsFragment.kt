package com.nong.amazingnews.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nong.amazingnews.R
import com.nong.amazingnews.databinding.FragmentEveryNewsBinding

enum class EveryNewsFragmentInfo(val tag: String) {
    EVERY_NEWS_LIST("everyNewsList")
}

class EveryNewsFragment : Fragment() {

    private lateinit var binding: FragmentEveryNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEveryNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentTransaction = childFragmentManager.beginTransaction()
        val everyNewsFragment = EveryNewsListFragment()

        fragmentTransaction.add(R.id.every_news_container, everyNewsFragment, EveryNewsFragmentInfo.EVERY_NEWS_LIST.tag)
        fragmentTransaction.show(everyNewsFragment)
        fragmentTransaction.commit()
    }
}