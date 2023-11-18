package com.jjg.mvvmproject.ui.fragments

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jjg.mvvmproject.R
import com.jjg.mvvmproject.databinding.FragmentNewsBinding
import com.jjg.mvvmproject.ui.adapter.NewsAdapter
import com.jjg.mvvmproject.vm.NewsViewModel
import com.jjg.mvvmproject.vm.RecentViewModel
import kotlin.math.roundToInt

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val gridItemDecoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            val spanCount = 2

            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount

            val horizontalSpacing = 30f
            val verticalSpacing = 30f

            if (column == 0) {
                outRect.right = (horizontalSpacing / 2).roundToInt()
            } else {
                outRect.left = (horizontalSpacing / 2).roundToInt()
            }

            //상하 설정
            if (state.itemCount <= 2) {
                //아이템 갯수가 2개 이하일 경우
            } else if (position == 0 || position == 1) {
                //시작
                outRect.bottom = (verticalSpacing / 2).roundToInt()
            } else if (position == state.itemCount - 1 || position == state.itemCount - 2) {
                //끝
                outRect.top = (verticalSpacing / 2).roundToInt()
            } else {
                outRect.top = (verticalSpacing / 2).roundToInt()
                outRect.bottom = (verticalSpacing / 2).roundToInt()
            }
        }
    }

    private val newsViewModel by lazy {
        ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_main))[NewsViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initialize()
        setUpObserver()
        newsViewModel.reqImageSearch(wordSearch = "한국시리즈 야구 우승")
    }

    private fun initialize() {
        context?.let { _context ->
            binding.rvNews.apply {
                layoutManager = GridLayoutManager(_context, 2)
                adapter = NewsAdapter(list = mutableListOf(), onClick = { _imageDocument ->
                    val recentViewModel = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_main))[RecentViewModel::class.java]
                    recentViewModel.addRecentItem(_imageDocument)

                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(_imageDocument.docUrl)))
                })
                removeItemDecoration(gridItemDecoration)
                addItemDecoration(gridItemDecoration)
            }
        }
    }

    private fun setUpObserver() {
        newsViewModel.imageDocuments.observe(this) { _imageDocuments ->
            (binding.rvNews.adapter as NewsAdapter).apply {
                addItems(_imageDocuments)
            }
        }

        newsViewModel.networkErrorMsg.observe(this) {
            Snackbar.make(_binding!!.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}