package com.jjg.mvvmproject.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jjg.mvvmproject.network.ResponseWrapper
import com.jjg.mvvmproject.repository.SearchRepository
import com.jjg.mvvmproject.repository.remote.models.ImageDocumentDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val searchRepository: SearchRepository = SearchRepository()) : ViewModel() {
    private val _networkErrorMsg = MutableLiveData<String>()
    val networkErrorMsg: LiveData<String> = _networkErrorMsg

    private val _imageDocuments = MutableLiveData<List<ImageDocumentDto>>()
    val imageDocuments: LiveData<List<ImageDocumentDto>> get() = _imageDocuments

    fun reqImageSearch(wordSearch: String?) {

        CoroutineScope(Dispatchers.IO).launch {
            when (val result = searchRepository.getImageSearch(page = 1, size = 20, query = wordSearch, sort = null)) {
                is ResponseWrapper.Success -> {
                    result.body.documents?.let { _documents ->
                        _imageDocuments.postValue(_documents)
                    }
                }
                is ResponseWrapper.StatusError -> {
                    _networkErrorMsg.postValue(result.ErrorMeta.errorType + "\n" + result.ErrorMeta.message)
                }
                is ResponseWrapper.ServerError -> {
                    _networkErrorMsg.postValue(result.e.toString())
                }
            }
        }
    }

}