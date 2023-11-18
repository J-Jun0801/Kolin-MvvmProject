package com.jjg.mvvmproject.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jjg.mvvmproject.databinding.ItemRecentImageBinding
import com.jjg.mvvmproject.vm.models.RecentModel

class RecentImageViewHolder(private val view: ItemRecentImageBinding) : RecyclerView.ViewHolder(view.root) {

    fun setUp(item: RecentModel, onClick: (RecentModel) -> Unit) {

        Glide.with(view.root)
            .load(item.imageUrl)
            .into(view.ivImage)

        view.tvTitle.text = item.title

        view.root.setOnClickListener {
            onClick(item)
        }

    }
}