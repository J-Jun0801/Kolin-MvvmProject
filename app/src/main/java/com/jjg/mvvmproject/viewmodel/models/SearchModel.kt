package com.jjg.mvvmproject.viewmodel.models

enum class RecentViewType { Image, Text }

data class RecentModel(
    val type: RecentViewType,
    val imageUrl: String?,
    val title: String,
    val contents: String,
)