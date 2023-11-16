package com.jjg.mvvmproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.jjg.mvvmproject.databinding.ItemSerarchBinding
import com.jjg.mvvmproject.repository.remote.models.WebDocumentDto
import com.jjg.mvvmproject.ui.adapter.viewholder.SearchViewHolder

class SearchAdapter(
    private val onClick : (WebDocumentDto)->Unit,
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
            holder.setUp(_webDocumentDto, onClick)
        }
    }
}
