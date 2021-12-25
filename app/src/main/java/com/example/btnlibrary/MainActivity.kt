package com.example.btnlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.custombutton.AhmetButton
import com.example.custombutton.AhmetButtonBuilder


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btn : AhmetButton = findViewById(R.id.btnTest)

//        AhmetButtonBuilder(this)
//            .backgroundColor(resources.getColor(R.color.black))
//            .shadowColor(resources.getColor(R.color.colorAccent))
//            .radius(15)
//            .rippleColor(resources.getColor(R.color.holo_blue_light))
//            .textColor(resources.getColor(R.color.holo_red_dark))
//            .shadowHeight(30)
//            .build(btn)




    }


}