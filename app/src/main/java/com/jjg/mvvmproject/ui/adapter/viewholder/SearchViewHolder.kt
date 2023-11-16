package com.jjg.mvvmproject.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.jjg.mvvmproject.databinding.ItemSerarchBinding
import com.jjg.mvvmproject.extension.textHtml
import com.jjg.mvvmproject.repository.remote.models.WebDocumentDto

class SearchViewHolder(private val view: ItemSerarchBinding) : RecyclerView.ViewHolder(view.root) {

    fun setUp(item: WebDocumentDto, onClick: (WebDocumentDto) -> Unit) {
        view.tvTitle.textHtml(item.title)
        view.tvContent.textHtml(item.contents)

        view.root.setOnClickListener {
            onClick(item)
        }
    }
}