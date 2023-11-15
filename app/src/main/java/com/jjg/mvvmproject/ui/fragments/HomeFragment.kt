package com.jjg.mvvmproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jjg.mvvmproject.databinding.FragmentHomeBinding
import com.jjg.mvvmproject.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        homeViewModel.reqGetSummary(wordSearch = "ajfakdsjkfjaklfjkaljfkajsdk;fjaklsfja;f")
        homeViewModel.networkErrorMsg.observe(this){

            Snackbar.make(_binding!!.root, it, Snackbar.LENGTH_SHORT).show()
        }
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initialize()


    }

    private fun initialize() {
        context?.let { _context ->
            binding.rvData.layoutManager = GridLayoutManager(_context,2)
//            binding.rvData.adapter =
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}