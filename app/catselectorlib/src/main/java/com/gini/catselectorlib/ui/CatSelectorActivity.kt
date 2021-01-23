package com.gini.catselectorlib.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gini.catselectorlib.R
import com.gini.catselectorlib.adapters.ImageAdapter
import com.gini.catselectorlib.api.ApiClient
import com.gini.catselectorlib.databinding.ActivityCatSelectorBinding
import com.gini.catselectorlib.getCatSelectorOptionsExtra
import com.gini.catselectorlib.models.ImageDto
import com.gini.catselectorlib.repositories.image.CatApiImageRepository
import com.gini.catselectorlib.repositories.image.GetAllPublicImagesParams
import com.gini.catselectorlib.utils.IRecyclerAdapterOnItemClickListener
import com.gini.catselectorlib.utils.imageloader.GlideImageDownloader
import com.gini.catselectorlib.viewmodels.CatSelectorViewModel
import com.gini.catselectorlib.viewmodels.CatSelectorViewModelFactory
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import java.io.File


class CatSelectorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val options = intent.getCatSelectorOptionsExtra()
            ?: throw IllegalArgumentException("Cat selector activity needs CatSelectorOptions")

        if(options.uiOptions.theme != 0)
            setTheme(options.uiOptions.theme)

        super.onCreate(savedInstanceState)

        val catApiParams = GetAllPublicImagesParams(
            options.apiOptions.apiKey,
            options.apiOptions.size,
            options.apiOptions.mimeTypes,
            options.apiOptions.order,
            options.apiOptions.categoryIds,
            options.apiOptions.breedId)

        val imageRepository = CatApiImageRepository(ApiClient.client, catApiParams, options.apiOptions.limit)
        val catSelectorViewModelFactory = CatSelectorViewModelFactory(
            imageRepository,
            options.apiOptions.limit,
            GlideImageDownloader(this),
            externalCacheDir!!.absolutePath
        )
        val viewModel = ViewModelProvider(this, catSelectorViewModelFactory)
            .get(CatSelectorViewModel::class.java)
        val binding: ActivityCatSelectorBinding = DataBindingUtil.setContentView(this, R.layout.activity_cat_selector)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val toolbar = binding.toolbarContainer
        setSupportActionBar(toolbar.my_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        options.uiOptions.title?.let {
            toolbar.toolbar_title.text = it
        }
        options.uiOptions.backButtonText?.let {
            toolbar.toolbar_back.text = it
        }
        toolbar.toolbar_back.setOnClickListener {
            onBackPressed()
        }

        val imageAdapter = ImageAdapter()
        imageAdapter.onItemClickListener = object: IRecyclerAdapterOnItemClickListener<ImageDto, ImageAdapter.ViewHolder> {
            override fun onItemClick(
                adapter: RecyclerView.Adapter<ImageAdapter.ViewHolder>,
                viewHolder: ImageAdapter.ViewHolder,
                item: ImageDto,
                position: Int
            ) {
                viewModel.downloadImage(item)
            }
        }
        val recyclerView = binding.recyclerView
        recyclerView.adapter = imageAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    val visibleItemCount = recyclerView.layoutManager!!.childCount
                    val totalItemCount = recyclerView.layoutManager!!.itemCount
                    val pastVisiblesItems = (recyclerView.layoutManager!! as GridLayoutManager)
                        .findFirstVisibleItemPosition()
                    if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                        viewModel.loadNewPage()
                    }
                }
            }
        })

        viewModel.images.observe(this, Observer { images ->
            imageAdapter.updateList(images)
        })

        viewModel.image.observe(this, Observer { image ->
            image?.let {
                setResultAndFinish(image)
            }
        })

        viewModel.error.observe(this, Observer { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setResultAndFinish(file: File) {
        val resultIntent = Intent().apply {
            data = file.toUri()
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}
