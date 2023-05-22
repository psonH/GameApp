package com.example.gameapp

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // main activity layout has the "welcome message" and the OK button to go to the menu screen
        setContentView(R.layout.activity_main)

        playBackgroundMusic()

        // ok button takes the user to the menu screen
        val okBtn: Button = findViewById(R.id.ok_btn)
        okBtn.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    // starts the background music
    private fun playBackgroundMusic() {
        val intent = intent
        // condition added to avoid the background music from playing again if one instance of music is already playing
        val backFromMenu = intent.getBooleanExtra("onBackFromMenu", false)
        mediaPlayer = MediaPlayer.create(this, R.raw.sample_audio)
        if(!backFromMenu) {
            mediaPlayer.isLooping = true
            mediaPlayer.start()
        }

    }

    // the background music must stop playing when the user exits the app using the back button
    override fun onPause() {
        super.onPause()
        if(this.isFinishing){
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}