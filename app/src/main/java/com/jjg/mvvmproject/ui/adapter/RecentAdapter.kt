package com.jjg.mvvmproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jjg.mvvmproject.databinding.ItemNewsBinding
import com.jjg.mvvmproject.databinding.ItemRecentImageBinding
import com.jjg.mvvmproject.databinding.ItemRecentTextBinding
import com.jjg.mvvmproject.repository.remote.models.ImageDocumentDto
import com.jjg.mvvmproject.ui.adapter.viewholder.NewsViewHolder
import com.jjg.mvvmproject.ui.adapter.viewholder.RecentImageViewHolder
import com.jjg.mvvmproject.ui.adapter.viewholder.RecentTextViewHolder
import com.jjg.mvvmproject.viewmodel.models.RecentModel
import com.jjg.mvvmproject.viewmodel.models.RecentViewType

class RecentAdapter(private val list: MutableList<RecentModel>, private val onClick: (RecentModel) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewTypeImage = 0
    private val viewTypeText = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            viewTypeImage -> {
                RecentImageViewHolder(ItemRecentImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else -> {
                RecentTextViewHolder(ItemRecentTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]

        when (holder) {
            is RecentImageViewHolder -> {
                holder.setUp(item = item, onClick = onClick)
            }
            is RecentTextViewHolder -> {
                holder.setUp(item = item, onClick = onClick)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].type == RecentViewType.Image) viewTypeImage else viewTypeText
    }

    fun addItems(list: List<RecentModel>) {
        val startIndex = this.list.size
        this.list.addAll(list)
        notifyItemRangeChanged(startIndex, this.list.size)
    }

}