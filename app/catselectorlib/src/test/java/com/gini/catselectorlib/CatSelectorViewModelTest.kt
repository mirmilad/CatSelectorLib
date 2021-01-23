package com.gini.catselectorlib

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gini.catselectorlib.models.ImageDto
import com.gini.catselectorlib.repositories.Resource
import com.gini.catselectorlib.repositories.image.IImageRepository
import com.gini.catselectorlib.utils.imageloader.IImageDownloader
import com.gini.catselectorlib.viewmodels.CatSelectorViewModel
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.io.File
import java.lang.RuntimeException

class CatSelectorViewModelTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val loadingObserver = mock<Observer<Boolean>>()
    private val errorObserver = mock<Observer<String>>()
    private val imagesObserver = mock<Observer<List<ImageDto>>>()
    private val imageObserver = mock<Observer<File>>()
    private val downloadingObserver = mock<Observer<Boolean>>()
    private val imageRepository = mock<IImageRepository>()
    private val imageDownloader = mock<IImageDownloader>()
    private val imageDownloadPath = "xxx"
    private val pageSize = 3

    private lateinit var fakeLifeCycleOwner: FakeLifeCycleOwner
    private lateinit var catSelectorViewModel: CatSelectorViewModel

    @Before
    fun setUp() {
        fakeLifeCycleOwner = FakeLifeCycleOwner()

        catSelectorViewModel = CatSelectorViewModel(imageRepository, pageSize, imageDownloader, imageDownloadPath)
        catSelectorViewModel.loading.observe(fakeLifeCycleOwner, loadingObserver)
        catSelectorViewModel.error.observe(fakeLifeCycleOwner, errorObserver)
        catSelectorViewModel.images.observe(fakeLifeCycleOwner, imagesObserver)
        catSelectorViewModel.image.observe(fakeLifeCycleOwner, imageObserver)
        catSelectorViewModel.downloading.observe(fakeLifeCycleOwner, downloadingObserver)

        fakeLifeCycleOwner.onCreate()
    }

    @After
    fun tearDown() {
        fakeLifeCycleOwner.onDestroy()
    }

    @Test
    fun test_lifecycleNotResumed() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(imageRepository.getImages(any())).thenReturn(Resource.success(emptyList<ImageDto>()))

            verify(imageRepository, never()).getImages(any())
        }
    }

    @Test
    fun test_addObserverAfterLifecycleResume() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(imageRepository.getImages(any())).thenReturn(Resource.success(emptyList<ImageDto>()))
            catSelectorViewModel.images.removeObserver(imagesObserver)
            fakeLifeCycleOwner.onResume()
            catSelectorViewModel.images.observe(fakeLifeCycleOwner, imagesObserver)

            verify(imageRepository, times(1)).getImages(any())
        }
    }

    @Test
    fun test_noObserver() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(imageRepository.getImages(any())).thenReturn(Resource.success(emptyList<ImageDto>()))
            catSelectorViewModel.images.removeObserver(imagesObserver)
            fakeLifeCycleOwner.onResume()

            verify(imageRepository, never()).getImages(any())
        }
    }

    @Test
    fun test_loadingSuccess() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(imageRepository.getImages(any())).thenReturn(Resource.success(emptyList<ImageDto>()))

            fakeLifeCycleOwner.onResume()

            inOrder(loadingObserver).apply {
                verify(loadingObserver).onChanged(false)
                verify(loadingObserver).onChanged(true)
                verify(loadingObserver).onChanged(false)
            }
        }
    }

    @Test
    fun test_loadingError() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(imageRepository.getImages(any())).thenReturn(Resource.error("Error", 500))

            fakeLifeCycleOwner.onResume()

            inOrder(loadingObserver).apply {
                verify(loadingObserver).onChanged(false)
                verify(loadingObserver).onChanged(true)
                verify(loadingObserver).onChanged(false)
            }
        }
    }

    @Test
    fun test_fetchData() {
        val images = (0..10).map { ImageDto(it.toString(), "", 0, 0) }
        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(imageRepository.getImages(any())).thenReturn(Resource.success(images))

            fakeLifeCycleOwner.onResume()
            verify(imagesObserver).onChanged(images)
            verify(errorObserver, never()).onChanged(any())
        }
    }

    @Test
    fun test_fetchError() {
        val error = "xxx"
        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(imageRepository.getImages(any())).thenReturn(Resource.error(error, 0))

            fakeLifeCycleOwner.onResume()
            verify(errorObserver).onChanged(error)
            verify(imagesObserver, never()).onChanged(any())
        }
    }

    @Test
    fun test_fetchPages() {
        val pages = arrayOf(
            (0 * pageSize until 1 * pageSize).map { ImageDto(it.toString(), "", 0, 0) },
            (1 * pageSize until 2 * pageSize).map { ImageDto(it.toString(), "", 0, 0) },
            (2 * pageSize until 3 * pageSize - 1).map { ImageDto(it.toString(), "", 0, 0) }
        )
        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(imageRepository.getImages(any()))
                .thenAnswer { Resource.success(pages[it.getArgument(0)]) }

            fakeLifeCycleOwner.onResume()

            verify(imagesObserver).onChanged(pages[0])

            catSelectorViewModel.loadNewPage()
            verify(imagesObserver).onChanged(pages[0] + pages[1])

            catSelectorViewModel.loadNewPage()
            verify(imagesObserver).onChanged(pages[0] + pages[1] + pages[2])

            verify(imageRepository, times(3)).getImages(any())
        }
    }

    @Test
    fun test_fetchLastPageEmpty() {
        val pages = arrayOf(
            (0 * pageSize until 1 * pageSize).map { ImageDto(it.toString(), "", 0, 0) },
            (1 * pageSize until 2 * pageSize).map { ImageDto(it.toString(), "", 0, 0) },
            (2 * pageSize until 3 * pageSize).map { ImageDto(it.toString(), "", 0, 0) },
            emptyList()
        )
        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(imageRepository.getImages(any()))
                .thenAnswer { Resource.success(pages[it.getArgument(0)]) }

            fakeLifeCycleOwner.onResume()

            catSelectorViewModel.loadNewPage()      //loading second page
            catSelectorViewModel.loadNewPage()      //loading third page

            catSelectorViewModel.loadNewPage()     //loading an empty page

            verify(imageRepository, times(4)).getImages(any())

            inOrder(imagesObserver).apply {
                verify(imagesObserver).onChanged(pages[0])
                verify(imagesObserver).onChanged(pages[0] + pages[1])
                verify(imagesObserver).onChanged(pages[0] + pages[1] + pages[2])
                verify(imagesObserver, never()).onChanged(any())
            }
        }
    }

    @Test
    fun test_fetchLastPageOnce1() {
        val pages = arrayOf(
            (0 * pageSize until 1 * pageSize).map { ImageDto(it.toString(), "", 0, 0) },
            (1 * pageSize until 2 * pageSize).map { ImageDto(it.toString(), "", 0, 0) },
            (2 * pageSize until 3 * pageSize - 1).map { ImageDto(it.toString(), "", 0, 0) }
        )
        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(imageRepository.getImages(any()))
                .thenAnswer {
                    val index: Int = it.getArgument(0)
                    Resource.success(if (index >= pages.size) emptyList<ImageDto>() else pages[index])
                }

            fakeLifeCycleOwner.onResume()

            catSelectorViewModel.loadNewPage()  //should loading second page
            catSelectorViewModel.loadNewPage()  //should loading third page

            catSelectorViewModel.loadNewPage()  //nothing to load, shouldn't call repository
            catSelectorViewModel.loadNewPage() //nothing to load, shouldn't call repository

            verify(imageRepository, times(3)).getImages(any())
        }
    }

    @Test
    fun test_fetchLastPageOnce2() {
        val pages = arrayOf(
            (0 * pageSize until 1 * pageSize).map { ImageDto(it.toString(), "", 0, 0) },
            (1 * pageSize until 2 * pageSize).map { ImageDto(it.toString(), "", 0, 0) },
            (2 * pageSize until 3 * pageSize).map { ImageDto(it.toString(), "", 0, 0) }
        )
        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(imageRepository.getImages(any()))
                .thenAnswer {
                    val index: Int = it.getArgument(0)
                    Resource.success(if (index >= pages.size) emptyList<ImageDto>() else pages[index])
                }

            fakeLifeCycleOwner.onResume()

            catSelectorViewModel.loadNewPage()  //should loading second page
            catSelectorViewModel.loadNewPage()  //should loading third page
            catSelectorViewModel.loadNewPage()  //should loading an empty page

            catSelectorViewModel.loadNewPage() //nothing to load, shouldn't call repository
            catSelectorViewModel.loadNewPage() //nothing to load, shouldn't call repository

            verify(imageRepository, times(4)).getImages(any())
        }
    }

    @Test
    fun test_fetchRetry() {
        val pages = arrayOf(
            (0 * pageSize until 1 * pageSize).map { ImageDto(it.toString(), "", 0, 0) },
            (1 * pageSize until 2 * pageSize).map { ImageDto(it.toString(), "", 0, 0) },
            (2 * pageSize until 3 * pageSize - 1).map { ImageDto(it.toString(), "", 0, 0) }
        )
        var error: String? = null
        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(imageRepository.getImages(any()))
                .thenAnswer {
                    if (error != null)
                        Resource.error(error!!, 0)
                    else {
                        val index: Int = it.getArgument(0)
                        Resource.success(if (index >= pages.size) emptyList<ImageDto>() else pages[index])
                    }
                }

            fakeLifeCycleOwner.onResume()

            error = "err in page 2"
            catSelectorViewModel.loadNewPage()  //should return error when loading second page
            error = null
            catSelectorViewModel.loadNewPage()  //should loading second page
            catSelectorViewModel.loadNewPage()  //should loading third page

            inOrder(imageRepository).apply {
                verify(imageRepository).getImages(0)
                verify(imageRepository, times(2)).getImages(1)
                verify(imageRepository).getImages(2)
            }

            inOrder(imagesObserver).apply {
                verify(imagesObserver).onChanged(pages[0])
                verify(imagesObserver).onChanged(pages[0] + pages[1])
                verify(imagesObserver).onChanged(pages[0] + pages[1] + pages[2])
            }
        }
    }

    @Test
    fun test_invokeImageDownloader() {
        val image = ImageDto("id", "fake image url", 0, 0)
        coroutineTestRule.testDispatcher.runBlockingTest {
            fakeLifeCycleOwner.onResume()
            catSelectorViewModel.downloadImage(image)
            verify(imageDownloader, times(1)).download(image.url, imageDownloadPath)
        }
    }

    @Test
    fun test_downloadingImage() {
        val image = ImageDto("id", "fake image url", 0, 0)
        coroutineTestRule.testDispatcher.runBlockingTest {
            val imageFile = File("")
            whenever(imageDownloader.download(any(), any())).thenReturn(imageFile)
            fakeLifeCycleOwner.onResume()
            catSelectorViewModel.downloadImage(image)

            inOrder(downloadingObserver).apply {
                verify(downloadingObserver).onChanged(true)
                verify(downloadingObserver).onChanged(false)
            }

            verify(imageObserver).onChanged(imageFile)
            verify(errorObserver, never()).onChanged(any())
        }
    }

    @Test
    fun test_downloadingFailed() {
        val image = ImageDto("id", "fake image url", 0, 0)
        val exception = RuntimeException("Can not download image")
        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(imageDownloader.download(any(), any())).thenThrow(exception)
            fakeLifeCycleOwner.onResume()
            catSelectorViewModel.downloadImage(image)

            inOrder(downloadingObserver).apply {
                verify(downloadingObserver).onChanged(true)
                verify(downloadingObserver).onChanged(false)
            }

            verify(imageObserver, never()).onChanged(any())
            verify(errorObserver).onChanged(exception.message)
        }
    }
}