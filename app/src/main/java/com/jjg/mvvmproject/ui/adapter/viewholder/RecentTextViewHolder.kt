package com.jjg.mvvmproject.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jjg.mvvmproject.databinding.ItemNewsBinding
import com.jjg.mvvmproject.databinding.ItemRecentImageBinding
import com.jjg.mvvmproject.databinding.ItemRecentTextBinding
import com.jjg.mvvmproject.extension.textHtml
import com.jjg.mvvmproject.repository.remote.models.ImageDocumentDto
import com.jjg.mvvmproject.repository.remote.models.WebDocumentDto
import com.jjg.mvvmproject.viewmodel.models.RecentModel

class RecentTextViewHolder(private val view: ItemRecentTextBinding) : RecyclerView.ViewHolder(view.root) {

    fun setUp(item: RecentModel, onClick: (RecentModel) -> Unit) {
        view.tvTitle.textHtml(item.title)
        view.tvContent.textHtml(item.contents)

        view.root.setOnClickListener {
            onClick(item)
        }
    }
}