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
import com.jjg.mvvmproject.NavMainDirections
import com.jjg.mvvmproject.R
import com.jjg.mvvmproject.databinding.FragmentRecentBinding
import com.jjg.mvvmproject.ui.adapter.RecentAdapter
import com.jjg.mvvmproject.viewmodel.RecentViewModel
import com.jjg.mvvmproject.viewmodel.models.RecentViewType
import kotlin.math.roundToInt

class RecentFragment : Fragment() {

    private var _binding: FragmentRecentBinding? = null
    private val binding get() = _binding!!

    private val recentViewModel by lazy {
        ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_main))[RecentViewModel::class.java]
    }

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
            outRect.top = (verticalSpacing / 2).roundToInt()
            outRect.bottom = (verticalSpacing / 2).roundToInt()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initialize()
        setUpObserver()
    }

    private fun initialize() {

        context?.let { _context ->
            binding.rvRecent.apply {
                layoutManager = GridLayoutManager(_context, 2)
                adapter = RecentAdapter(list = mutableListOf(), onClick = { _recentModel ->
                    if(_recentModel.type == RecentViewType.Text){
                        findNavController().navigate(NavMainDirections.actionToNavDetail(recentModel = _recentModel))
                    }else{
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(_recentModel.docUrl)))
                    }
                })
                removeItemDecoration(gridItemDecoration)
                addItemDecoration(gridItemDecoration)
            }
        }
    }

    private fun setUpObserver() {
        recentViewModel.recentDocuments.observe(this) { _recentDocuments ->
            binding.rvRecent.visibility = if(_recentDocuments.isNotEmpty()) View.VISIBLE else View.GONE
            binding.tvNoRecentViewed.visibility = if(_recentDocuments.isEmpty()) View.VISIBLE else View.GONE

            (binding.rvRecent.adapter as RecentAdapter).addItems(_recentDocuments)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}