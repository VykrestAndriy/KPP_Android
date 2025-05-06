package com.example.lb_android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var commenceActivityButton: Button
    private lateinit var numericalToolButton: Button
    private lateinit var formButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        commenceActivityButton = findViewById(R.id.commenceActivityButton)
        numericalToolButton = findViewById(R.id.numericalToolButton)
        formButton = findViewById(R.id.formButton)

        commenceActivityButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        numericalToolButton.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }

        formButton.setOnClickListener {
            val intent = Intent(this, FormaActivity::class.java)
            startActivity(intent)
        }
    }
}