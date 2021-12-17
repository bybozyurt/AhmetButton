package com.example.custombutton.util

import android.content.res.Resources

/**
 * Created by Ahmet Bozyurt on 7.12.2021
 */
object DimensionUtil {

    fun pxToDip(px: Float): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    fun dipToPx(dip: Float): Int {
        return (dip * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun pxToSp(px: Float): Int {
        return (px / Resources.getSystem().displayMetrics.scaledDensity).toInt()
    }

    fun spToPx(sp: Float): Int {
        return (sp * Resources.getSystem().displayMetrics.scaledDensity).toInt()
    }

}