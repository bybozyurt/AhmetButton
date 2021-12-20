package com.example.btnlibrary

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //Toast.makeText(applicationContext, "he", Toast.LENGTH_SHORT).show()

        btnTest.setBtnBackgroundColorNormal(resources.getColor(R.color.holo_red_dark))

        txt.text = btnTest.getBtnTextColorNormal().toString()








    }
}