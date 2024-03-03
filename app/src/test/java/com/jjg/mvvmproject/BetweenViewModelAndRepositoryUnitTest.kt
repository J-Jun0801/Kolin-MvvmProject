package com.jjg.mvvmproject

import android.media.Image
import android.net.Network
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jjg.mvvmproject.network.Meta
import com.jjg.mvvmproject.network.NetworkResponse
import com.jjg.mvvmproject.network.ResponseWrapper
import com.jjg.mvvmproject.repository.SearchRepository
import com.jjg.mvvmproject.repository.remote.models.ImageDocumentDto
import com.jjg.mvvmproject.vm.NewsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response
import timber.log.Timber
import java.time.Duration

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class BetweenViewModelAndRepositoryUnitTest {
    // ViewModel에서 LiveData를 사용하면 Android의 Main 스레드에서 동작해야 하므로 InstantTaskExecutorRule을 추가
//    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: NewsViewModel
    private lateinit var repository: SearchRepository


    @Before
    fun setup() {
        println(">>> setup start ")
//         테스트용 디스패처 설정 (코루틴 테스트용)
        Dispatchers.setMain(Dispatchers.Unconfined)

        repository = mockk()
        viewModel = NewsViewModel()
        println(">>> setup end ")
    }

    @Test
    fun `test fetchData success`() = runBlockingTest{
        // Mock 객체의 동작 정의
        println(">>> 1 ")
        val mockedTest = ResponseWrapper.Success(
            NetworkResponse<List<ImageDocumentDto>>(
                documents = emptyList(), meta = Meta(
                    isEnd = false,
                    pageableCount = 0,
                    totalCount = 0
                )
            )
        )
        println(">>> 2 ")
        coEvery {
            repository.getImageSearch(page = 1, size = 20, query = "한국시리즈", sort = null)
        } returns mockedTest


        // LiveData를 관찰할 Observer 생성
        println(">>> 3 ")
        val observer = mockk<Observer<List<ImageDocumentDto>>>(relaxed = true)

        // LiveData를 Observer에 연결
        println(">>> 4 ")
        viewModel.imageDocuments.observeForever(observer)

        // 특정 동작 실행
        println(">>> 5 ")
        viewModel.reqImageSearch("한국시리즈")

        // LiveData의 변경을 검증
        println(">>> 6 ")
        verify { observer.onChanged(mockedTest.body.documents!!) }
    }
}