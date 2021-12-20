package com.example.custombutton.util

import android.graphics.Color
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.custombutton.attribute.DrawableAttributes
import com.example.custombutton.attribute.RippleAttributes
import com.example.custombutton.attribute.ShadowAttributes

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


    @RequiresApi(Build.VERSION_CODES.N)
    fun getRippleDrawable(drawableAttributes: DrawableAttributes, rippleAttributes: RippleAttributes, layerDrawable: LayerDrawable): Drawable {

        val drawableNormal = getBackgroundDrawable(drawableAttributes)
        var mask = GradientDrawable()

        if (drawableNormal.constantState != null) {
            // Clone the GradientDrawable default and sets the color to white and maintains round corners (if any).
            // This fixes problems with transparent color and rounded corners.
            mask = drawableNormal.constantState!!.newDrawable() as GradientDrawable
            mask.setColor(Color.WHITE)
        }

        return RippleDrawable(ColorUtil.getRippleColorFromColor(rippleAttributes.rippleColor, rippleAttributes.rippleOpacity), layerDrawable, mask)


    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun createDrawable(rad: Int, topColor: Int, bottomColor: Int, mShadowAttributes: ShadowAttributes, drawableAttributes: DrawableAttributes, rippleAttributes: RippleAttributes) : LayerDrawable {

        val drawable = GradientDrawable()
        val radius = rad.toFloat()

        //val backgroundDrawable = getRippleDrawable(drawableAttributes, rippleAttributes)

        drawable.cornerRadii =
            floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)


        //Top
        val topRoundRect = RoundRectShape(drawable.cornerRadii, null, null)
        val topShapeDrawable = ShapeDrawable(topRoundRect)
        topShapeDrawable.paint.color = topColor
        //Bottom
        val roundRectShape = RoundRectShape(drawable.cornerRadii, null, null)
        val bottomShapeDrawable = ShapeDrawable(roundRectShape)
        bottomShapeDrawable.paint.color = bottomColor

        //Create array
        val drawArray = arrayOf<Drawable>(bottomShapeDrawable, topShapeDrawable)
        val layerDrawable = LayerDrawable(drawArray)



        if(mShadowAttributes.ab_shadow_enabled && topColor != Color.TRANSPARENT){
            //unpressed drawable
            Log.d("TAG","buradayım1")
            layerDrawable.setLayerInset(0,0,0,0,0) /*index, left, top, right, bottom*/
        }
        else{
            //pressed drawable
                Log.d("TAG","buradayım")
            layerDrawable.setLayerInset(0,0,mShadowAttributes.ab_shadow_height,0,0)
        }
        layerDrawable.setLayerInset(1,0,0,0,mShadowAttributes.ab_shadow_height)

        return layerDrawable

    }




}