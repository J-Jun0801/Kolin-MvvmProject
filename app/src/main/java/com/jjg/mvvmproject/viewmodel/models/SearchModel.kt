package com.jjg.mvvmproject.viewmodel.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class RecentViewType { Image, Text }

@Parcelize
data class RecentModel(
    val type: RecentViewType,
    val imageUrl: String?,
    val docUrl: String?,
    val title: String,
    val contents: String,
) : Parcelable