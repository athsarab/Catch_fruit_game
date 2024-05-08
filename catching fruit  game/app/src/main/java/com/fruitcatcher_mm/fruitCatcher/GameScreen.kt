package com.fruitcatcher_mm.fruitCatcher

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.fruitcatcher_mm.fruitCatcher.R
import kotlin.concurrent.schedule
import java.util.*
import kotlin.math.floor

class GameScreen : AppCompatActivity() {

    private lateinit var gameLayout: ConstraintLayout
    private lateinit var heroImageView: ImageView
    private lateinit var gameEndImageView: ImageView
    private lateinit var apricot50PImageView: ImageView
    private lateinit var heart20PImageView: ImageView
    private lateinit var startGameTextView: TextView
    private lateinit var scoreTextView: TextView

    // Positions
    private var heroX = 0.0f
    private var heroY = 0.0f
    private var gameEndY = 0.0f
    private var gameEndX = 0.0f
    private var apricotX = 0.0f
    private var apricotY = 0.0f
    private var heartX = 0.0f
    private var heartY = 0.0f

    // Dimensions
    private var screenWidth = 0
    private var screenHeight = 0
    private var heroWidth = 0
    private var heroHeight = 0

    // Controls
    private var touchControl = false
    private var startControl = false

    private val timer = Timer()
    private var score = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        gameLayout = findViewById(R.id.clGameScreen)
        heroImageView = findViewById(R.id.ivHero)
        gameEndImageView = findViewById(R.id.ivGameOver)
        heart20PImageView = findViewById(R.id.ivLife20P)
        apricot50PImageView = findViewById(R.id.ivApricot50P)
        startGameTextView = findViewById(R.id.tvStartGame)
        scoreTextView = findViewById(R.id.tvScore)

        gameEndImageView.x = -800f
        gameEndImageView.y = -800f
        heart20PImageView.x = -800f
        heart20PImageView.y = -800f
        apricot50PImageView.x = -800f
        apricot50PImageView.y = -800f

        gameLayout.run {
            setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                    if (startControl) {
                        if (event?.action == MotionEvent.ACTION_DOWN) {
                            Log.e("motionevent", "touched the screen")
                            touchControl = true
                        }
                        if (event?.action == MotionEvent.ACTION_UP) {
                            Log.e("motionevent", "released the screen")
                            touchControl = false
                        }
                    } else {
                        startControl = true

                        startGameTextView.visibility = View.INVISIBLE

                        heroX = heroImageView.x
                        heroY = heroImageView.y

                        heroWidth = heroImageView.width
                        heroHeight = heroImageView.height
                        screenWidth = gameLayout.width
                        screenHeight = gameLayout.height

                        timer.schedule(0, 20) {
                            Handler(Looper.getMainLooper()).post {
                                heroMovement()
                                objectMovement()
                                collisionCheck()
                            }
                        }

                    }

                    return true
                }
            })
        }
    }

    fun heroMovement() {
        val heroSpeed = screenHeight / 60.0f

        if (touchControl) {
            heroY -= heroSpeed
        } else {
            heroY += heroSpeed
        }
        if (heroY <= 0.0f) {
            heroY = 0.0f
        }
        if (heroY >= screenHeight - heroHeight) {
            heroY = (screenHeight - heroHeight).toFloat()
        }
        heroImageView.y = heroY
    }

    fun objectMovement() {
        gameEndX -= screenWidth / 44.0f
        apricotX -= screenWidth / 36.0f
        heartX -= screenWidth / 54.0f
        if (gameEndX < 0.0f) {
            gameEndX = screenWidth + 20.0f
            gameEndY = floor(Math.random() * screenHeight).toFloat()
        }
        gameEndImageView.x = gameEndX
        gameEndImageView.y = gameEndY
        if (apricotX < 0.0f) {
            apricotX = screenWidth + 20.0f
            apricotY = floor(Math.random() * screenHeight).toFloat()
        }
        apricot50PImageView.x = apricotX
        apricot50PImageView.y = apricotY
        if (heartX < 0.0f) {
            heartX = screenWidth + 20.0f
            heartY = floor(Math.random() * screenHeight).toFloat()
        }
        heart20PImageView.x = heartX
        heart20PImageView.y = heartY
    }

    fun collisionCheck() {
        val heartCenterX = heartX + heart20PImageView.width / 2.0f
        val heartCenterY = heartY + heart20PImageView.height / 2.0f

        if (0.0f <= heartCenterX && heartCenterX <= heroWidth && heroY <= heartCenterY
            && heartCenterY <= heroY + heroHeight
        ) {
            score += 20
            heartX = -80.0f
        }
        val apricotCenterX = apricotX + apricot50PImageView.width / 2.0f
        val apricotCenterY = apricotY + apricot50PImageView.height / 2.0f

        if (0.0f <= apricotCenterX && apricotCenterX <= heroWidth && heroY <= apricotCenterY
            && apricotCenterY <= heroY + heroHeight
        ) {
            score += 50
            apricotX = -80.0f
        }
        val gameEndCenterX = gameEndX + gameEndImageView.width / 2.0f
        val gameEndCenterY = gameEndY + gameEndImageView.height / 2.0f

        if (0.0f <= gameEndCenterX && gameEndCenterX <= heroWidth && heroY <= gameEndCenterY
            && gameEndCenterY <= heroY + heroHeight
        ) {
            gameEndX = -80.0f
            timer.cancel()

            val intent = Intent(this@GameScreen, ResultScreenActivity::class.java)
            intent.putExtra("score", score)
            startActivity(intent)
            finish()
        }
        scoreTextView.text = score.toString()
    }
}
