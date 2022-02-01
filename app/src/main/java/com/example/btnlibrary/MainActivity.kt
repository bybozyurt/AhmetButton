package com.example.btnlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.custombutton.AhmetButton
import com.example.custombutton.AhmetButtonBuilder


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //val btn : AhmetButton = findViewById(R.id.btnTest)

//        AhmetButtonBuilder(this)
//            .backgroundColor(resources.getColor(R.color.black))
//            .shadowColor(resources.getColor(R.color.colorAccent))
//            .radius(15)
//            .rippleColor(resources.getColor(R.color.holo_blue_light))
//            .textColor(resources.getColor(R.color.holo_red_dark))
//            .shadowHeight(30)
//            .build(btn)

        val btn : AhmetButton = findViewById(R.id.btn)

//        btn.setBtnBackgroundColor(ContextCompat.getColor(applicationContext,R.color.color_orange))
//        btn.setBtnRadius(45)
//        btn.setBtnShadowColor(ContextCompat.getColor(applicationContext, R.color.holo_red_dark))
//        btn.setBtnShadowHeight(18)
//        btn.setRippleColor(ContextCompat.getColor(applicationContext, R.color.holo_purple))
//        btn.setRippleOpacity(150)
//        btn.text = "Ahmet"
//        btn.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))

        AhmetButtonBuilder(this)
            .backgroundColor(ContextCompat.getColor(applicationContext,R.color.color_orange))
            .shadowColor(ContextCompat.getColor(applicationContext, R.color.holo_red_dark))
            .rippleColor(ContextCompat.getColor(applicationContext, R.color.holo_blue_dark))
            .rippleOpacity(15f)
            .radius(45)
            .shadowHeight(18)
            .textColor(ContextCompat.getColor(applicationContext, R.color.black))
            .build(btn)




    }


}