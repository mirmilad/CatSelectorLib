package com.gini.catselectorlib.utils

import androidx.recyclerview.widget.RecyclerView
import com.gini.catselectorlib.adapters.ImageAdapter

interface IRecyclerAdapterOnItemClickListener<T, VH : RecyclerView.ViewHolder> {
    fun onItemClick(adapter: RecyclerView.Adapter<VH>,
                    viewHolder: VH,
                    item: T,
                    position: Int
                    )
}