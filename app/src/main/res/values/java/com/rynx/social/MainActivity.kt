package com.rynx.social

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val text = TextView(this)

        text.text = "Rynx Social 🚀"
        text.textSize = 28f
        text.setTextColor(Color.BLACK)
        text.setPadding(40, 40, 40, 40)

        setContentView(text)
    }
}
