package com.jjg.mvvmproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjg.mvvmproject.databinding.FragmentHomeBinding
import com.jjg.mvvmproject.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    private fun initialize() {
        context?.let { _context ->
            binding.rvData.layoutManager = LinearLayoutManager(_context)
//            binding.rvData.adapter =
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}