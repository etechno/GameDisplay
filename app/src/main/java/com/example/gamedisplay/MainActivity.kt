package com.example.gamedisplay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import layout.GameFragment
import java.util.*

class MainActivity : AppCompatActivity(), GameDisplayFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = GameDisplayFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
        }
    }

    override fun onGameSelected(gameTitle: String, gameDescription: String, gameWebUrl: String, gameDisplayUrl: String) {
        val fragment = GameFragment.newInstance(gameTitle,gameDescription,gameWebUrl,gameDisplayUrl)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
    }
}