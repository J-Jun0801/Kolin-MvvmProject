package com.jjg.mvvmproject.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jjg.mvvmproject.R
import com.jjg.mvvmproject.databinding.FragmentHomeBinding
import com.jjg.mvvmproject.ui.adapter.NewsAdapter
import com.jjg.mvvmproject.viewmodel.HomeViewModel
import timber.log.Timber
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
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


    private val homeViewModel by lazy {
        ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.mobile_navigation))[HomeViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initialize()

        homeViewModel.reqImageSearch(wordSearch = "한국시리즈 야구 우승")
        homeViewModel.imageDocuments.observe(this) {
            val adapter = binding.rvData.adapter as NewsAdapter
            adapter.addItems(it)
        }


        homeViewModel.networkErrorMsg.observe(this)
        {
            Snackbar.make(_binding!!.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun initialize() {
        context?.let { _context ->
            binding.rvData.layoutManager = GridLayoutManager(_context, 2)
            binding.rvData.adapter = NewsAdapter(list = mutableListOf())
            binding.rvData.removeItemDecoration(gridItemDecoration)
            binding.rvData.addItemDecoration(gridItemDecoration)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}