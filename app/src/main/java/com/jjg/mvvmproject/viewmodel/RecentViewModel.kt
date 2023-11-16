package com.jjg.mvvmproject.viewmodel

import androidx.lifecycle.*
import com.jjg.mvvmproject.repository.remote.models.ImageDocumentDto
import com.jjg.mvvmproject.repository.remote.models.WebDocumentDto
import com.jjg.mvvmproject.viewmodel.models.RecentModel
import com.jjg.mvvmproject.viewmodel.models.RecentViewType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import timber.log.Timber

class RecentViewModel : ViewModel() {

    private val _recentDocuments = MutableLiveData<List<RecentModel>>()
    val recentDocuments: LiveData<List<RecentModel>> get() = _recentDocuments

    fun addRecentItem(imageDocumentDto: ImageDocumentDto) {
        val deepCopyList = (_recentDocuments.value ?: mutableListOf()).toMutableList()

        val recentModel = RecentModel(
            type = RecentViewType.Image,
            imageUrl = imageDocumentDto.imageUrl,
            title = imageDocumentDto.displaySiteName,
            contents = ""
        )

        if (!deepCopyList.contains(recentModel)) {
            deepCopyList.add(recentModel)
            _recentDocuments.postValue(deepCopyList)
        }
    }

    fun addRecentItem(webDocumentDto: WebDocumentDto) {
        val deepCopyList = (_recentDocuments.value ?: mutableListOf()).toMutableList()

        val recentModel = RecentModel(
            type = RecentViewType.Text,
            imageUrl = null,
            title = webDocumentDto.title,
            contents = webDocumentDto.contents
        )

        if (!deepCopyList.contains(recentModel)) {
            deepCopyList.add(recentModel)
            _recentDocuments.postValue(deepCopyList)
        }
    }
}