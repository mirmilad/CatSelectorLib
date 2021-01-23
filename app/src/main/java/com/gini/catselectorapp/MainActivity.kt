package com.gini.catselectorapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.gini.catselectorlib.CatSelectorIntent
import com.gini.catselectorlib.options.ApiOptions
import com.gini.catselectorlib.options.CatSelectorOptions
import com.gini.catselectorlib.options.UIOptions
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 1001
        const val API_KEY = ""
    }

    private lateinit var imageView: ImageView
    private var lastImage: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.image)
    }

    fun selectImage1(view: View) {
        deleteLastImage()

        val apiOptions = ApiOptions(API_KEY)
        val options = CatSelectorOptions(apiOptions)
        val intent = CatSelectorIntent(this, options)
        startActivityForResult(intent, REQUEST_CODE)
    }

    fun selectImage2(view: View) {
        deleteLastImage()

        val apiOptions = ApiOptions(API_KEY)
        val uiOptions = UIOptions(theme = R.style.CatSelectorCustomTheme)
        val options = CatSelectorOptions(apiOptions, uiOptions)
        val intent = CatSelectorIntent( this, options)
        startActivityForResult(intent, REQUEST_CODE)
    }

    fun selectImage3(view: View) {
        deleteLastImage()

        val apiOptions = ApiOptions(API_KEY)
        val uiOptions = UIOptions(getString(R.string.custom_title), getString(R.string.custom_back), R.style.CatSelectorCustomTheme)
        val options = CatSelectorOptions(apiOptions, uiOptions)
        val intent = CatSelectorIntent( this, options)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                lastImage = data?.data
                lastImage?.let {
                    imageView.setImageURI(lastImage)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        deleteLastImage()
    }

    private fun deleteLastImage() {
        imageView.setImageDrawable(null)
        lastImage?.let {
            File(it.path).delete()
        }
    }
}
