package com.jjg.mvvmproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jjg.mvvmproject.databinding.ItemNewsBinding
import com.jjg.mvvmproject.databinding.ItemSerarchBinding
import com.jjg.mvvmproject.repository.remote.models.ImageDocumentDto
import com.jjg.mvvmproject.repository.remote.models.WebDocumentDto
import com.jjg.mvvmproject.ui.adapter.viewholder.NewsViewHolder
import com.jjg.mvvmproject.ui.adapter.viewholder.SearchViewHolder
import timber.log.Timber

class SearchAdapter(
    diffCallback: DiffUtil.ItemCallback<WebDocumentDto> = object : DiffUtil.ItemCallback<WebDocumentDto>() {
        override fun areItemsTheSame(oldItem: WebDocumentDto, newItem: WebDocumentDto): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WebDocumentDto, newItem: WebDocumentDto): Boolean {
            return oldItem == newItem
        }
    }
) : PagingDataAdapter<WebDocumentDto, SearchViewHolder>(diffCallback = diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(ItemSerarchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { _webDocumentDto ->
            holder.setUp(_webDocumentDto)
        }
    }
}
