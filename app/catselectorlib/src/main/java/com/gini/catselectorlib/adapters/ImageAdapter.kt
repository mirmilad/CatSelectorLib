package com.gini.catselectorlib.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gini.catselectorlib.databinding.ImageAdapterItemBinding
import com.gini.catselectorlib.models.ImageDto
import com.gini.catselectorlib.utils.IRecyclerAdapterOnItemClickListener

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private val items : MutableList<ImageDto> = arrayListOf()
    var onItemClickListener: IRecyclerAdapterOnItemClickListener<ImageDto, ImageAdapter.ViewHolder>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        val binding = ImageAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding).apply {
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    val position = this.adapterPosition
                    it.onItemClick(
                        this@ImageAdapter,
                        this,
                        items[adapterPosition],
                        adapterPosition
                    )
                }
            }
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        val image = items.get(position)
        holder.bind(image)
    }

    class ViewHolder(private val viewBinding: ImageAdapterItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(image: ImageDto) {
            viewBinding.model = image
        }
    }

    fun updateList(newList: List<ImageDto>) {
        val diffResult = DiffUtil.calculateDiff(ImageDiffCallback(items, newList))
        items.clear()
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class ImageDiffCallback(private val oldList: List<ImageDto>,
                            private val newList: List<ImageDto>) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = newList[newItemPosition].equals(oldList[oldItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = newList[newItemPosition].equals(oldList[oldItemPosition])
    }
}