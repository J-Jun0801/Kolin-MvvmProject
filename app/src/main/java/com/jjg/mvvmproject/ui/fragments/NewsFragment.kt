package com.jjg.mvvmproject.ui.fragments

import android.graphics.Rect
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
import com.jjg.mvvmproject.viewmodel.NewsViewModel
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
        ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.mobile_navigation))[NewsViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initialize()

        newsViewModel.reqImageSearch(wordSearch = "한국시리즈 야구 우승")
        newsViewModel.imageDocuments.observe(this) {
            val adapter = binding.rvNews.adapter as NewsAdapter
            adapter.addItems(it)
        }

        newsViewModel.networkErrorMsg.observe(this)
        {
            Snackbar.make(_binding!!.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun initialize() {
        context?.let { _context ->
            binding.rvNews.layoutManager = GridLayoutManager(_context, 2)
            binding.rvNews.adapter = NewsAdapter(list = mutableListOf())
            binding.rvNews.removeItemDecoration(gridItemDecoration)
            binding.rvNews.addItemDecoration(gridItemDecoration)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}