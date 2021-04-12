package com.gmail.apigeoneer.animationsexample

import android.animation.*
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

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
        val animator = ObjectAnimator.ofArgb(ivStar.parent, "backgroundColor", Color.BLACK, Color.RED)
        animator.setDuration(400)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableDuringAnimation(btnBackgroundColor)
        animator.start()
    }

    private fun party() {
        // ivStar.parent -> background
        val animator = ObjectAnimator.ofInt(ivStar.parent, "backgroundColor", Color.BLACK, Color.RED)
        animator.setDuration(400)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableDuringAnimation(btnParty)
        animator.start()
    }

    private fun shower() {
        // Hold the required states
        val container = ivStar.parent as ViewGroup
        val containerW = container.width
        val containerH = container.height
        var starW = ivStar.width.toFloat()
        var starH = ivStar.height.toFloat()

        // Create a new view to hold the star graphic
        val newStar = AppCompatImageView(this)
        // Create the star
        newStar.setImageResource(R.drawable.ic_baseline_star_24)
        newStar.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                                FrameLayout.LayoutParams.WRAP_CONTENT)
        // Add the star to the background
        container.addView(newStar)

        // Set the size of the star (.5x <= random size <= 1.5x of default size)
        newStar.scaleX = Math.random().toFloat() * 1.5f + .5f
        newStar.scaleY = newStar.scaleX
        // Use the above scale factor to change the cached width/height values
        starW *= newStar.scaleX                    // ?
        starH *= newStar.scaleY

        // Position the star
        // horizontally
        newStar.translationX = Math.random().toFloat() * containerW - starW / 2
        /**
         * vertically
         * Step 1: Create 2 animators: mover & rotator, along w/ their interpolators
         */
        val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y, -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1.2f)          // vertical fall is accelerated
        val rotator = ObjectAnimator.ofFloat(newStar, View.ROTATION, (Math.random() * 1080).toFloat())         // 0 deg - 3*360 deg : 0 <= #rotations <= 3
        rotator.interpolator = LinearInterpolator()                      // rotation is linear

        // Combine the 2 animators to run in parallel using AnimatorSet
        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        set.duration = (Math.random() * 1500 + 500).toLong()

        // Ren=move the star at the end of teh animation.
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                container.removeView(newStar)
            }
        })
        set.start()
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
