package com.gini.catselectorlib.utils.imageloader

import android.content.Context
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel

class GlideImageDownloader(private val context: Context) : IImageDownloader  {
    override suspend fun download(url: String, path: String): File = withContext(Dispatchers.IO) {
        val downloadedFile =
            Glide.with(context)
            .asFile()
            .load(url)
            .submit()
            .get()

        var targetName = ""
        val index = url.lastIndexOf('/') + 1
        if (index > 0) {
            targetName = url.substring(index)
        }
        if(targetName == "")
            targetName = downloadedFile.name

        val resultFile = File(path, targetName)
        resultFile.parentFile.mkdirs()
        resultFile.createNewFile()

        val inStream = FileInputStream(downloadedFile)
        val outStream = FileOutputStream(resultFile)
        val inChannel: FileChannel = inStream.channel
        val outChannel: FileChannel = outStream.channel
        inChannel.transferTo(0, inChannel.size(), outChannel)
        inStream.close()
        outStream.close()

        resultFile
    }



}