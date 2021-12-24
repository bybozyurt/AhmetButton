package com.example.custombutton.util

import android.graphics.Color
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import com.example.custombutton.attribute.AhmetButtonBuilder
import com.example.custombutton.attribute.RippleAttributes
import com.example.custombutton.attribute.ShadowAttributes


/**
 * Created by Ahmet Bozyurt on 7.12.2021
 */
object DrawableFactory {
    fun getBackgroundDrawable(ahmetButtonBuilder: AhmetButtonBuilder): Drawable {

        val drawable = GradientDrawable()

        val cornerRadius = ahmetButtonBuilder.ab_radius.toFloat()

        drawable.cornerRadii = floatArrayOf(
            cornerRadius,
            cornerRadius,
            cornerRadius,
            cornerRadius,
            cornerRadius,
            cornerRadius,
            cornerRadius,
            cornerRadius
        )

        drawable.setColor(ahmetButtonBuilder.ab_bg_color)


        return drawable

    }


    //layerDrawable: LayerDrawable
    fun getRippleDrawable(
        ahmetButtonBuilder: AhmetButtonBuilder,
        rippleAttributes: RippleAttributes,
        layerDrawable: LayerDrawable
    ): Drawable {

        val drawableNormal = getBackgroundDrawable(ahmetButtonBuilder)
        var mask = GradientDrawable()

        if (drawableNormal.constantState != null) {
            // Clone the GradientDrawable default and sets the color to white and maintains round corners (if any).
            // This fixes problems with transparent color and rounded corners.
            mask = drawableNormal.constantState!!.newDrawable() as GradientDrawable
            mask.setColor(Color.WHITE)
        }

        return RippleDrawable(
            ColorUtil.getRippleColorFromColor(
                rippleAttributes.rippleColor,
                rippleAttributes.rippleOpacity
            ), layerDrawable, mask
        )


    }


    fun createDrawable(
        rad: Int,
        topColor: Int,
        bottomColor: Int,
        mShadowAttributes: ShadowAttributes
    ): Drawable {

        val radius = rad.toFloat()

        val outerRadius = floatArrayOf(
            radius,
            radius,
            radius, radius, radius, radius, radius, radius
        )

        //val backgroundDrawable = getRippleDrawable(drawableAttributes, rippleAttributes)

        //Top
        val topRoundRect = RoundRectShape(outerRadius, null, null)
        val topShapeDrawable = ShapeDrawable(topRoundRect)
        topShapeDrawable.paint.color = topColor

        //Bottom
        val roundRectShape = RoundRectShape(outerRadius, null, null)
        val bottomShapeDrawable = ShapeDrawable(roundRectShape)
        bottomShapeDrawable.paint.color = bottomColor

        //Create array
        val drawArray = arrayOf<Drawable>(bottomShapeDrawable, topShapeDrawable)
        val layerDrawable = LayerDrawable(drawArray)

        layerDrawable.setLayerInset(1, 0, 0, 0, mShadowAttributes.ab_shadow_height)

        return layerDrawable


    }



}