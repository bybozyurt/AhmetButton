package com.example.custombutton.util

import android.content.res.Resources

/**
 * Created by Ahmet Bozyurt on 7.12.2021
 */
object DimensionUtil {

    fun dipToPx(dip: Float): Int {
        return (dip * Resources.getSystem().displayMetrics.density).toInt()
    }

}