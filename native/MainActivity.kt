package com.example.lab2_mobile_native

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lab2_mobile_native.databinding.ActivityMainBinding
import android.os.Handler

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.text = "Plan your time"

        animateTextSize(binding.textView, 0f, 35f)

    }

    private fun animateTextSize(textView: TextView, fromSize: Float, toSize: Float) {
        val anim = ObjectAnimator.ofFloat(textView, "textSize", fromSize, toSize)
        anim.duration = 2000
        anim.start()
        Handler().postDelayed({
            FadeOutText(binding.textView)
        },4000)

    }
    private fun FadeOutText(textView: TextView)
    {
        val fadeIn = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f)
        fadeIn.duration = 2000
        fadeIn.start()
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, ListActivities::class.java)
            startActivity(intent)
            finish()
        },2000)

    }
}