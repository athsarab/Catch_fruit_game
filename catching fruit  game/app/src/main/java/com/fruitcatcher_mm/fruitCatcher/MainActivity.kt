package com.fruitcatcher_mm.fruitCatcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.fruitcatcher_mm.fruitCatcher.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the Start button
        btnStart = findViewById(R.id.btnStart)

        // Set click listener for the Start button
        btnStart.setOnClickListener {
            // Start the GameScreen activity
            startActivity(Intent(this@MainActivity, GameScreen::class.java))
        }
    }
}
