package com.example.cvikoapp09imagetoapp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.cvikoapp09imagetoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent())
        { uri ->
            if (uri != null) {
                binding.ivImage.setImageURI(uri)
            }
        }

        binding.btnChangeImage.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.btnRotateLeft.setOnClickListener {
            rotateImage(-90f)
        }

        binding.btnRotateRight.setOnClickListener {
            rotateImage(90f)
        }
    }

    private fun rotateImage(degrees: Float) {
        val drawable = binding.ivImage.drawable ?: return
        val bitmap = getBitmapFromDrawable(drawable) ?: return

        val matrix = Matrix()
        matrix.postRotate(degrees)

        val rotatedBitmap = Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
        )

        binding.ivImage.setImageBitmap(rotatedBitmap)
    }

    private fun getBitmapFromDrawable(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth.coerceAtLeast(1),
            drawable.intrinsicHeight.coerceAtLeast(1),
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }
}