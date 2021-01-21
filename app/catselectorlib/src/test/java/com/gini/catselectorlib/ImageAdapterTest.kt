package com.gini.catselectorlib

import com.gini.catselectorlib.adapters.ImageAdapter
import com.gini.catselectorlib.models.ImageDto
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE, sdk = [18])
class ImageAdapterTest {


    @Test
    fun test_emptyAdapter() {
        val adapter = ImageAdapter()
        assertEquals(adapter.itemCount, 0)
    }

    @Test
    fun test_size() {
        val adapter = ImageAdapter()
        val items = listOf(
            ImageDto("1", "", 0, 0),
            ImageDto("2", "", 0, 0),
            ImageDto("3", "", 0, 0)
        )
        adapter.updateList(items)
        assertEquals(adapter.itemCount, items.size)
    }

    @Test
    fun test_diffUtil() {
        val list1 = listOf(
            ImageDto("1", "", 0, 0),
            ImageDto("2", "", 0, 0)
        )

        val list2 = listOf(
            ImageDto("1", "", 0, 0),
            ImageDto("3", "", 0, 0),
            ImageDto("4", "", 0, 0)
        )

        val diffUtil = ImageAdapter.ImageDiffCallback(list1, list2)

        assertEquals(diffUtil.oldListSize, list1.size)
        assertEquals(diffUtil.newListSize, list2.size)

        assert(diffUtil.areItemsTheSame(0, 0))
        assert(diffUtil.areContentsTheSame(0, 0))

        assert(!diffUtil.areItemsTheSame(1, 1))
        assert(!diffUtil.areContentsTheSame(1, 1))
    }
}