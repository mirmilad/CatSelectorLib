package com.gini.catselectorlib.utils.imageloader

import java.io.File

interface IImageDownloader {
    suspend fun download(url: String, path: String) : File
}