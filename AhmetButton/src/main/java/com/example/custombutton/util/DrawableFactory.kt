package com.example.custombutton.util

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import com.example.custombutton.attribute.DrawableAttributes
import com.example.custombutton.attribute.RippleAttributes

/**
 * Created by Ahmet Bozyurt on 7.12.2021
 */
object DrawableFactory {
    fun getBackgroundDrawable(drawableAttributes: DrawableAttributes): Drawable {

        val drawable = GradientDrawable()

        val cornerRadius = drawableAttributes.ab_radius.toFloat()

        drawable.cornerRadii = floatArrayOf(cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius)

//        drawable.setStroke(drawableAttributes.borderThickness, drawableAttributes.borderColor)
//        drawable.setColor(ColorUtil.useOpacity(drawableAttributes.backgroundColor, drawableAttributes.backgroundOpacity))
        drawable.setColor(drawableAttributes.ab_bg_color)


        return drawable

    }


    fun getRippleDrawable(drawableAttributes: DrawableAttributes, rippleAttributes: RippleAttributes): Drawable {

        val drawableNormal = getBackgroundDrawable(drawableAttributes)
        var mask = GradientDrawable()

        if (drawableNormal.constantState != null) {
            // Clone the GradientDrawable default and sets the color to white and maintains round corners (if any).
            // This fixes problems with transparent color and rounded corners.
            mask = drawableNormal.constantState!!.newDrawable() as GradientDrawable
            mask.setColor(Color.WHITE)
        }

        return RippleDrawable(ColorUtil.getRippleColorFromColor(rippleAttributes.rippleColor, rippleAttributes.rippleOpacity), drawableNormal, mask)

    }




}