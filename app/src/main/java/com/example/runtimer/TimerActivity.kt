package com.example.runtimer

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.view.TouchDelegate
import android.view.View
import android.view.accessibility.AccessibilityEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_timer.*
import java.util.*

class TimerActivity : AppCompatActivity() {
    var time = 0
    var started = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        val parent: View = homeButton.parent as View
        val delegateArea = Rect()
        parent.post {
            homeButton.getHitRect(delegateArea)
            delegateArea.left -= 40
            parent.touchDelegate =  TouchDelegate(delegateArea, homeButton)
        }
    }

    fun View.goHome() {
        val intent = Intent(this@TimerActivity, MainActivity::class.java)
        startActivity(intent)
        this@TimerActivity.finish()
    }

    fun View.startTimer() {
        runTimer()
    }

    fun View.reset() {
        time = 0
        started = false
        updateText()
    }

    private fun pause() {
        started = false
        resetButton.isEnabled = true
        start_label.text = resources.getString(R.string.start)
        actionButton.setImageResource(R.drawable.ic_start_button)
    }

    fun updateText() {
        val minutes: Int = time / 360000
        val secs: Int = time % 6000 / 100
        val milli: Int = time % 100
        val timeString: String = java.lang.String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, secs, milli)
        timer_text.text = timeString

        /**this code makes the screen reader to read the time on the app every 15 secs*/
        if (secs % 15 == 0) {
            timer_text.sendAccessibilityEvent(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)
        }
    }

    private fun runTimer() {
        if(started) {
            return pause()
        }
        else {
            start_label.text = resources.getString(R.string.pause)
            actionButton.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
            started = true
            resetButton.isEnabled = false
            val handler = Handler()
            handler.post(object : Runnable {
                override fun run() {
                    updateText()
                    if (started) {
                        time += 1
                        handler.postDelayed(this, 5)
                    }
                }
            })
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}