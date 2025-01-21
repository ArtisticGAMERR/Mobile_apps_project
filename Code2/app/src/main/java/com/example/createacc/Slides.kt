package com.example.createacc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.ViewFlipper

class Slides : AppCompatActivity() {
    private lateinit var viewFlipper: ViewFlipper
    private lateinit var nextButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slides)
        viewFlipper = findViewById(R.id.viewFlipper)
        nextButton = findViewById(R.id.btnGo)
        val inflater = LayoutInflater.from(this)
        val slide1 = inflater.inflate(R.layout.slide_1, viewFlipper, false)
        val slide2 = inflater.inflate(R.layout.slide_2, viewFlipper, false)
        val slide3 = inflater.inflate(R.layout.slide_3, viewFlipper, false)

        viewFlipper.addView(slide1)
        viewFlipper.addView(slide2)
        viewFlipper.addView(slide3)

        nextButton.setOnClickListener {
            if (viewFlipper.displayedChild == viewFlipper.childCount - 1) {
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                viewFlipper.showNext()
            }
        }
    }
}