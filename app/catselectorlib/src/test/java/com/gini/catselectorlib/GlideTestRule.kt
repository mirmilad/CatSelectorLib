package com.gini.catselectorlib


import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.nhaarman.mockitokotlin2.whenever
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.ArgumentMatchers
import org.mockito.MockedStatic
import org.mockito.Mockito

class GlideTestRule(private val requestManager: RequestManager) : TestWatcher() {

    private lateinit var staticMock: MockedStatic<Glide>

    override fun starting(description: Description?) {
        super.starting(description)
        staticMock = Mockito.mockStatic(Glide::class.java).apply {
            whenever(Glide.with(ArgumentMatchers.any(Context::class.java))).thenReturn(requestManager)
        }
    }

    override fun finished(description: Description?) {
        super.finished(description)
        staticMock.close()
    }
}