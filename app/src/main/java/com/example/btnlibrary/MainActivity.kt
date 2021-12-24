package com.example.btnlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.custombutton.AhmetButton
import com.example.custombutton.AhmetButtonBuilder


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<AhmetButton>(R.id.btnTest)

        AhmetButtonBuilder(this)
            .AbBgColor(resources.getColor(R.color.black))
            .AbShadowColor(resources.getColor(R.color.colorAccent))
            .AbRadius(15)
            .AbRippleColor(resources.getColor(R.color.holo_blue_light))
            .AbTxtColor(resources.getColor(R.color.holo_red_dark))
            .AbShadowHeight(30)
            .build(btn)

    }


}