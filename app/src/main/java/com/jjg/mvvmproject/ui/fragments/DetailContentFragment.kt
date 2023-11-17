package com.jjg.mvvmproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jjg.mvvmproject.R
import com.jjg.mvvmproject.databinding.FragmentDetailContentBinding
import com.jjg.mvvmproject.viewmodel.DetailContentViewModel
import com.jjg.mvvmproject.viewmodel.RecentViewModel
import com.jjg.mvvmproject.viewmodel.models.RecentModel

class DetailContentFragment : Fragment() {

    private var _binding: FragmentDetailContentBinding? = null
    private val binding get() = _binding!!

    private val args: DetailContentFragmentArgs by navArgs()

    private val detailContentViewModel by lazy {
        ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_detail))[DetailContentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val recentModel = args.recentModel

        val recentViewModel = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_main))[RecentViewModel::class.java]
        recentViewModel.addRecentItem(recentModel = recentModel)

        initialize(recentModel)
    }

    private fun initialize(recentModel: RecentModel) {
        binding.toolbar.title = recentModel.title
        binding.tvContent.text = recentModel.contents
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}