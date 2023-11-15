package com.jjg.mvvmproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jjg.mvvmproject.databinding.ItemNewsBinding
import com.jjg.mvvmproject.repository.remote.models.ImageDocumentDto
import com.jjg.mvvmproject.ui.adapter.viewholder.NewsViewHolder
import timber.log.Timber

class NewsAdapter(private val list: MutableList<ImageDocumentDto>) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        Timber.e("CustomerReviewAdapter onBindViewHolder position: $position")
        val item = list[position]
        holder.setUp(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addItems(list: List<ImageDocumentDto>) {

        val startIndex = this.list.size

        this.list.addAll(list)

        notifyItemRangeChanged(startIndex, this.list.size)
    }

}