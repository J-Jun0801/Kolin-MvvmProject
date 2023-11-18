package com.jjg.mvvmproject.ui.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jjg.mvvmproject.NavMainDirections
import com.jjg.mvvmproject.R
import com.jjg.mvvmproject.databinding.FragmentSearchBinding
import com.jjg.mvvmproject.extension.hideKeypad
import com.jjg.mvvmproject.ui.adapter.SearchAdapter
import com.jjg.mvvmproject.vm.SearchViewModel
import com.jjg.mvvmproject.vm.models.RecentModel
import com.jjg.mvvmproject.vm.models.RecentViewType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel by lazy {
        ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_main))[SearchViewModel::class.java]
    }

    private val decoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)

            val verticalSpacing = 30f
            outRect.top = (verticalSpacing / 2).roundToInt()
            outRect.bottom = (verticalSpacing / 2).roundToInt()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initialize()
        setUpObserver()
    }

    private fun initialize() {
        context?.let { _context ->
            binding.rvSearch.apply {
                layoutManager = LinearLayoutManager(_context)
                removeItemDecoration(decoration)
                addItemDecoration(decoration)
                adapter = SearchAdapter(onClick = { _webDocumentDto ->
                    val recentModel = RecentModel(
                        type = RecentViewType.Text,
                        imageUrl = null,
                        docUrl = null,
                        title = _webDocumentDto.title,
                        contents = _webDocumentDto.contents
                    )

                    findNavController().navigate(NavMainDirections.actionToNavDetail(recentModel = recentModel))
                })
            }
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchWord = binding.etSearch.text
                if (searchWord?.isNotEmpty() == true) {
                    searchViewModel.reqWebSearch(searchWord.toString())
                } else {
                    Snackbar.make(_binding!!.root, getString(R.string.guide_empty_word), Snackbar.LENGTH_SHORT).show()
                }

                context?.let { _context -> binding.etSearch.hideKeypad(_context) }
            }
            false
        }
    }

    private fun setUpObserver() {
        searchViewModel.webDocuments.observe(this) { _webDocuments ->
            CoroutineScope(Dispatchers.Main).launch {
                _webDocuments.collectLatest { pagingData ->
                    (binding.rvSearch.adapter as SearchAdapter).submitData(pagingData)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}