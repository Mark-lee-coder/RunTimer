package com.example.runtimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToApp(view: View) {
        // Do something in response to button
        val intent = Intent(this, TimerActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}