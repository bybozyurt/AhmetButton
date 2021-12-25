package com.example.custombutton.util

import android.content.res.ColorStateList
import android.graphics.Color

/**
 * Created by Ahmet Bozyurt on 7.12.2021
 */
object ColorUtil {

    fun getRippleColorFromColor(color: Int, opacity: Float): ColorStateList {

        return ColorStateList.valueOf(useOpacity(color, opacity))

    }

    fun useOpacity(color: Int, opacity: Float): Int {
        return Color.argb(Math.min(Math.round(opacity * 255), 255),
            Math.min(Math.round(Color.red(color).toFloat()), 255),
            Math.min(Math.round(Color.green(color).toFloat()), 255),
            Math.min(Math.round(Color.blue(color).toFloat()), 255))

    }

    fun isDarkColor(color: Int): Boolean {
        
        val r = Color.red(color) / 255f
        val g = Color.green(color) / 255f
        val b = Color.blue(color) / 255f

        val luma = r * 0.299f + g * 0.587f + b * 0.114f

        return luma < 0.5
    }

    fun getTextColorFromBackgroundColor(color: Int): Int {

        return if (isDarkColor(color)) Color.WHITE else Color.BLACK

    }
}