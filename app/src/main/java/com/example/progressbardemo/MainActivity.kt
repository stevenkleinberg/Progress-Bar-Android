package com.example.progressbardemo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var downloadButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var restartButton: Button
    private lateinit var lottieView: LottieAnimationView
    private lateinit var textView: TextView
    // small image file of cat
    private val imageUrl = "https://animalgiftclub-static.myshopblocks.com/images/2019/03/contain/2048x2048/ad91f89f14a43481e85fe0809ebd5b5e.jpg"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        downloadButton = findViewById(R.id.downloadButton)
        imageView = findViewById(R.id.imageView)
        restartButton = findViewById(R.id.restartButton)
        lottieView = findViewById(R.id.lottieAnimationView)
        textView = findViewById(R.id.centeredText)


        downloadButton.setOnClickListener {
            // Use lifecycleScope to launch the coroutine
            lifecycleScope.launch {
                downloadImage()
            }
        }

        restartButton.setOnClickListener {
            imageView.visibility = View.GONE
            restartButton.visibility = View.GONE
            downloadButton.visibility = View.VISIBLE

            // Stop the Lottie animation
            lottieView.visibility = View.GONE
            textView.visibility = View.GONE
        }
    }

    private suspend fun downloadImage() {
        // Start the Lottie animation
        textView.visibility = View.VISIBLE
        lottieView.visibility = View.VISIBLE
        downloadButton.visibility = View.GONE

        // Introduce a 2.5-second delay
        delay(2500) // 2.5 seconds in milliseconds

        //grab the image
        val image = loadImageFromUrl(imageUrl)

        withContext(Dispatchers.Main) {
            if (image != null) {
                imageView.visibility = View.VISIBLE
                imageView.setImageBitmap(image)
                restartButton.visibility = View.VISIBLE
                lottieView.visibility= View.GONE
                textView.visibility = View.GONE
            } else {
                downloadButton.visibility = View.VISIBLE
            }


        }
    }

    private suspend fun loadImageFromUrl(url: String): Bitmap? = withContext(Dispatchers.IO) {
        return@withContext try {
            val inputStream = java.net.URL(url).openStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}


