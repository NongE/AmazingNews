package com.nong.amazingnews.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.LoadState.*
import com.nong.amazingnews.R
import com.nong.amazingnews.SearchNewsViewModel
import com.nong.amazingnews.databinding.FragmentSearchNewsBinding
import com.nong.amazingnews.fragment.adapter.SearchNewsListAdapter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {

    private lateinit var binding: FragmentSearchNewsBinding

    private lateinit var adapter: SearchNewsListAdapter
    private val viewModel: SearchNewsViewModel by lazy {
        ViewModelProvider(owner = this)[SearchNewsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchNewsListAdapter().apply {
            setOnItemClickListener(object : SearchNewsListAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {
                    Toast.makeText(v.context, "$position is clicked!", Toast.LENGTH_SHORT).show()
                }
            })

            addLoadStateListener {
                when {
                    it.source.refresh is Loading -> {
                        binding.shimmerActivityMain.startShimmer()
                        binding.shimmerActivityMain.visibility = View.VISIBLE
                    }
                    it.source.refresh is NotLoading -> {
                        binding.shimmerActivityMain.stopShimmer()
                        binding.shimmerActivityMain.visibility = View.GONE
                    }
                }
            }
        }

        binding.searchRecyclerView.adapter = adapter

        binding.editText
            .textChange()
            .onEach {
                if(it?.length != 0){ viewModel.query(it.toString()) }
            }
            .debounce(1000)
            .launchIn(lifecycleScope)

        lifecycleScope.launch {
            viewModel.flow.collectLatest { adapter.submitData(it) }
        }

        viewModel.searchExceptionLiveData.observe(viewLifecycleOwner) {
            it.getContent()?.let { exception ->
                Toast.makeText(context, "E: ${exception.message}", Toast.LENGTH_SHORT).show()
                binding.shimmerActivityMain.stopShimmer()
                binding.shimmerActivityMain.visibility = View.GONE
            }
        }
    }

    private fun EditText.textChange(): Flow<CharSequence?> {
        return callbackFlow<CharSequence?> {
            val textListener = object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) = Unit
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    trySend(p0)
                }
            }
            addTextChangedListener(textListener)
            awaitClose { removeTextChangedListener(textListener) }
        }.onStart { emit(text) }
    }
}