package com.gini.catselectorlib

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.FutureTarget
import com.gini.catselectorlib.utils.imageloader.GlideImageDownloader
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.*
import org.junit.Assert.assertArrayEquals
import org.mockito.ArgumentMatchers
import org.mockito.MockedStatic
import org.mockito.Mockito
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.random.Random

class GlideImageDownloaderTest {

    private val testPath = File("tempTestFolder")
    private val downloadedFile = File(testPath,"test.file")
    private val downloadPath = File(testPath, "testdownload").absolutePath
    private var downloadFileContent: ByteArray = byteArrayOf()

    private val context = mock<Context> {
        on {
            applicationContext
        } doReturn this.mock
    }

    private val futureTarget = mock<FutureTarget<File>> {
        on { get() } doReturn downloadedFile
    }

    private val fileRequestBuilder = mock<RequestBuilder<File>> {
        on { load(any<String>()) } doReturn this.mock
        on { submit() } doReturn futureTarget
    }
    private val requestManager = mock<RequestManager> {
        on { asFile() } doReturn fileRequestBuilder
    }

    private val imageDownloader = GlideImageDownloader(context)

    @get:Rule
    val glideRule = GlideTestRule(requestManager)

    @Before
    fun setUp() {
        testPath.deleteRecursively()
        File(downloadPath).mkdirs()
        val fileSize = Random.nextInt(1000, 2000)
        downloadFileContent = Random.nextBytes(fileSize)
        FileOutputStream(downloadedFile).apply {
            write(downloadFileContent)
            close()
        }
    }

    @After
    fun cleanUp() {
        testPath.deleteRecursively()
    }

    companion object {
        private lateinit var dispatchersMock: MockedStatic<Dispatchers>

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            dispatchersMock = Mockito.mockStatic(Dispatchers::class.java).apply {
                whenever(Dispatchers.IO) doReturn TestCoroutineDispatcher()
            }
        }

        @AfterClass
        @JvmStatic
        fun cleanUpClass() {
            dispatchersMock.close()
        }
    }


    @Test
    fun test_downloadAndCopy() {
        val downloadFolder = File(downloadPath)
        val imageName = "xxx.jpg"
        val imageUrl = "http://www.example.com/images/$imageName"
        runBlocking {
            val output = imageDownloader.download(imageUrl, downloadPath)
            val bytes = FileInputStream(output).run {
                val bytes = readBytes()
                close()
                bytes
            }


            assertEquals(imageName, output.name)
            assertEquals(downloadFolder.absolutePath, output.parentFile.absolutePath)

            assertArrayEquals(downloadFileContent, bytes)
        }
    }

    @Test
    fun test_downloadExistingFile() {
        val downloadFolder = File(downloadPath)
        val imageName = "xxx.jpg"
        val imageUrl = "http://www.example.com/images/$imageName"

        File(downloadFolder, imageName).createNewFile()

        runBlocking {
            val output = imageDownloader.download(imageUrl, downloadPath)
            val bytes = FileInputStream(output).run {
                val bytes = readBytes()
                close()
                bytes
            }

            assertEquals(imageName, output.name)
            assertEquals(downloadFolder.absolutePath, output.parentFile.absolutePath)

            assertArrayEquals(downloadFileContent, bytes)
        }
    }

    @Test
    fun test_unrecognizedFileName() {
        val downloadFolder = File(downloadPath)
        val imageUrl = "very strange url without file name :))"

        runBlocking {
            val output = imageDownloader.download(imageUrl, downloadPath)
            val bytes = FileInputStream(output).run {
                val bytes = readBytes()
                close()
                bytes
            }

            assertEquals(downloadedFile.name, output.name)
            assertEquals(downloadFolder.absolutePath, output.parentFile.absolutePath)

            assertArrayEquals(downloadFileContent, bytes)
        }
    }
}