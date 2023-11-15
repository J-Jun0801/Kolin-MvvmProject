package com.jjg.mvvmproject.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jjg.mvvmproject.databinding.ItemNewsBinding
import com.jjg.mvvmproject.repository.remote.models.ImageDocumentDto

class NewsViewHolder(private val view: ItemNewsBinding) : RecyclerView.ViewHolder(view.root) {

    fun setUp(item: ImageDocumentDto) {

        Glide.with(view.root)
            .load(item.imageUrl)
            .into(view.ivImage)

        view.tvTitle.text = item.displaySiteName
    }
}