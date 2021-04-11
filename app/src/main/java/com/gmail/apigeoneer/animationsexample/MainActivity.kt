package com.gmail.apigeoneer.animationsexample

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var ivStar: ImageView
    private lateinit var btnRotate: Button
    private lateinit var btnTranslate: Button
    private lateinit var btnScale: Button
    private lateinit var btnFade: Button
    private lateinit var btnBackgroundColor: Button
    private lateinit var btnShower: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivStar = findViewById(R.id.starImageView)
        btnRotate = findViewById(R.id.rotateBtn)
        btnTranslate = findViewById(R.id.translateBtn)
        btnScale = findViewById(R.id.scaleBtn)
        btnFade = findViewById(R.id.fadeBtn)
        btnBackgroundColor = findViewById(R.id.backgroundBtn)
        btnShower = findViewById(R.id.showerBtn)

        btnRotate.setOnClickListener {
            rotater()
        }

        btnTranslate.setOnClickListener {
            translater()
        }

        btnScale.setOnClickListener {
            sacler()
        }

        btnFade.setOnClickListener {
            fader()
        }

        btnBackgroundColor.setOnClickListener {
            colorizer()
        }

        btnShower.setOnClickListener {
            shower()
        }

    }

    private fun rotater() {
        val animator = ObjectAnimator.ofFloat(ivStar, View.ROTATION, -360f, 0f)
        // Change the animation duration to 1000 ms (since 3000 ms i.e. the default feels too short here)
        animator.duration = 900
        // to avoid jank
        animator.addListener(object: AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                btnRotate.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                btnRotate.isEnabled = true
            }
        })
        // run the animation
        animator.start()
    }

    private fun translater() {
        TODO("Not yet implemented")
    }

    private fun sacler() {
        TODO("Not yet implemented")
    }

    private fun fader() {
        TODO("Not yet implemented")
    }

    private fun colorizer() {
        TODO("Not yet implemented")
    }

    private fun shower() {
        TODO("Not yet implemented")
    }
}