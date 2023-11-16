package com.jjg.mvvmproject.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jjg.mvvmproject.databinding.ItemNewsBinding
import com.jjg.mvvmproject.databinding.ItemSerarchBinding
import com.jjg.mvvmproject.extension.textHtml
import com.jjg.mvvmproject.repository.remote.models.ImageDocumentDto
import com.jjg.mvvmproject.repository.remote.models.WebDocumentDto

class SearchViewHolder(private val view: ItemSerarchBinding) : RecyclerView.ViewHolder(view.root) {

    fun setUp(item: WebDocumentDto) {
        view.tvTitle.textHtml(item.title)
        view.tvContent.textHtml(item.contents)
    }
}