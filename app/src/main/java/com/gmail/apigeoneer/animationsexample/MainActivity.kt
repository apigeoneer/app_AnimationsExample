package com.gmail.apigeoneer.animationsexample

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
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
    private lateinit var btnParty: Button
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
        btnParty = findViewById(R.id.partyBtn)
        btnShower = findViewById(R.id.showerBtn)

        btnRotate.setOnClickListener {
            rotater()
        }

        btnTranslate.setOnClickListener {
            translater()
        }

        btnScale.setOnClickListener {
            scaler()
        }

        btnFade.setOnClickListener {
            fader()
        }

        btnBackgroundColor.setOnClickListener {
            colorizer()
        }

        btnParty.setOnClickListener {
            party()
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
        animator.disableDuringAnimation(btnRotate)
        // run the animation
        animator.start()
    }

    private fun translater() {
        /**
         *  200 f -> not the distance to be travelled, it is the end position
         *  START POSITION -> the default position of the star
         *
         *  Problem 1:  the animation is only being set up to run one way; it animates the star 200 pixels to the right… and that’s it.
         *              So if we want it to come back, we’re going to need something extra.
         *  Problem 2:  subsequent animations don’t appear to do anything because the animation is set up to run to a value of 200.
         *              After the animation has run, the value is already at 200, so there’s no place else to go.
         */
        val animator = ObjectAnimator.ofFloat(ivStar, View.TRANSLATION_X, 200f)
        /**
         * Solution to prob.s 1 & 2 :  REPETITION
         */
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableDuringAnimation(btnTranslate)
        animator.start()
    }

    private fun scaler() {
        // The PropertyValuesHolder objects only hold the property & value info of the animation, not the target.
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 2.5f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 2.5f)

        val animator = ObjectAnimator.ofPropertyValuesHolder(ivStar, scaleX, scaleY)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableDuringAnimation(btnScale)
        animator.start()
    }

    private fun fader() {
        val animator = ObjectAnimator.ofFloat(ivStar, View.ALPHA, 0f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableDuringAnimation(btnFade)
        animator.start()
    }

    private fun colorizer() {
        // THe ofArgb() smooths the transition between the start & final values
        // ivStar.parent -> background
        val animator = ObjectAnimator.ofInt(ivStar.parent, "backgroundColor", Color.BLACK, Color.RED)
        animator.setDuration(500)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableDuringAnimation(btnBackgroundColor)
        animator.start()
    }

    private fun party() {
        // ivStar.parent -> background
        val animator = ObjectAnimator.ofInt(ivStar.parent, "backgroundColor", Color.BLACK, Color.RED)
        animator.setDuration(600)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    private fun shower() {
        TODO("Not yet implemented")
    }

    // Extension fun
    private fun ObjectAnimator.disableDuringAnimation(view: View) {
        addListener(object: AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                btnRotate.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                btnRotate.isEnabled = true
            }
        })
    }
}
