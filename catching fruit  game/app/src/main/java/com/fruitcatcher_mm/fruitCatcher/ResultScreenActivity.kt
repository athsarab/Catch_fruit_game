package com.fruitcatcher_mm.fruitCatcher

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.fruitcatcher_mm.fruitCatcher.R

class ResultScreenActivity : AppCompatActivity() {

    private lateinit var btnPlayAgain: Button
    private lateinit var tvCurrentScore: TextView
    private lateinit var tvHighScore: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_screen)

        // Initialize UI components
        btnPlayAgain = findViewById(R.id.btnPlayAgain)
        tvCurrentScore = findViewById(R.id.tvCurrentScore)
        tvHighScore = findViewById(R.id.tvHighScore)

        // Retrieve the score from the intent
        val score = intent.getIntExtra("score", 0)
        tvCurrentScore.text = score.toString()

        // Retrieve the high score from SharedPreferences
        val sp = getSharedPreferences("Result", Context.MODE_PRIVATE)
        val highScore = sp.getInt("highScore", 0)

        // Check if the current score is higher than the high score
        if (score > highScore) {
            // Update the high score
            val editor = sp.edit()
            editor.putInt("highScore", score)
            editor.apply()
            tvHighScore.text = score.toString()
        } else {
            // Display the current high score
            tvHighScore.text = highScore.toString()
        }

        // Set click listener for the play again button
        btnPlayAgain.setOnClickListener {
            startActivity(Intent(this@ResultScreenActivity, GameScreen::class.java))

            
        }
    }
}
