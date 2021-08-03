package ru.dariamikhailukova.task8.internet

import android.animation.ObjectAnimator
import android.animation.ValueAnimator.REVERSE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import ru.dariamikhailukova.task8.R
import ru.dariamikhailukova.task8.databinding.ActivityCustomHtmlBinding

/**
 * Класс для изучения работы анимаций и касаний в Android
 */
class CustomHtml : AppCompatActivity(), View.OnTouchListener {
    private lateinit var binding: ActivityCustomHtmlBinding

    private var initialX: Float = 0F
    private var initialY: Float = 0F
    private var dx: Float = 0F
    private var dy: Float = 0F
    private var isAnimated: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomHtmlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.myToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.customTextView.setOnTouchListener(this)

        binding.button.setOnClickListener {
            rotaryAnimate(binding.customTextView)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    // Функция для анимации переданной вью
    private fun rotaryAnimate(view: View){
        ObjectAnimator.ofFloat(view,
        View.ROTATION_X, 0f, 180f).apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = 1
            repeatMode = REVERSE
            start()
        }
    }

    // Функция, реализующая перемещение вью при касании
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            KeyEvent.ACTION_DOWN -> {
                if (!isAnimated) {
                    initialX = v.x
                    initialY = v.y
                    dx = initialX - event.rawX
                    dy = initialY - event.rawY
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (!isAnimated) {
                    v.x = event.rawX + dx
                    v.y = event.rawY + dy
                }
            }
            KeyEvent.ACTION_UP -> {
                v.performClick()
                v.animate()
                    .x(initialX)
                    .y(initialY)
                    .setDuration(500)
                    .withEndAction{ isAnimated = false }
                    .withStartAction{ isAnimated = true }
                    .start()
            }
        }

        return true
    }
}

