package com.example.gameapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class MenuActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // the "go back" on the menu screen will take the user back to the "welcome message" screen
        val goBackBtn: Button = findViewById(R.id.go_back)
        goBackBtn.setOnClickListener {
            // using intent to switch between the activities
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("onBackFromMenu",true)
            // to clear the activity stack
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        // the "start game" button will start the "quiz app" game and launch the QuizPlayActivity
        val startGameBtn: Button = findViewById(R.id.start_game)
        startGameBtn.setOnClickListener {
            // using intent to switch between the activities
            val intent = Intent(this, QuizPlayActivity::class.java)
            startActivity(intent)
        }

        // the "show score" button will show the score screen of the last game
        val showScore: Button = findViewById(R.id.show_score)
        showScore.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }
    }
}