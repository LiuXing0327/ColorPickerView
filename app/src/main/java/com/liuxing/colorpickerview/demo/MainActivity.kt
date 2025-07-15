package com.liuxing.colorpickerview.demo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.liuxing.colorpickerview.R
import com.liuxing.library.ColorPickerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val colorPickerView =
            findViewById<ColorPickerView>(R.id.color_picker_view)
        val main = findViewById<ConstraintLayout>(R.id.main)

        colorPickerView.onColorChanged =
            { color -> main.setBackgroundColor(color) }
    }
}