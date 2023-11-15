package com.jjg.mvvmproject.repository.remote.models

import com.google.gson.annotations.SerializedName

data class WebDocumentDto(
    val contents: String,
    val datetime: String,
    val title: String,
    val url: String
)

data class ImageDocumentDto(
    val collection: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("image_url")
    val imageUrl: String,
    val width: Int,
    val height: Int,
    @SerializedName("display_sitename")
    val displaySiteName: String,
    @SerializedName("doc_url")
    val docUrl: String,
    @SerializedName("datetime")
    val dateTime: String
)