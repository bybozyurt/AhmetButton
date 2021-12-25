package com.example.custombutton

import android.content.Context


/**
 * Created by Ahmet Bozyurt on 7.12.2021
 */




class AhmetButtonBuilder(
    val context: Context
) {
    var mBackgroundColor: Int = context.resources.getColor(R.color.default_background_color)
        private set
    var mRadius: Int = 8
        private set
    var mShadowColor: Int = context.resources.getColor(R.color.default_text_color)
        private set
    var mShadowHeight: Int = 12
        private set
    var mRippleColor: Int = context.resources.getColor(R.color.holo_blue_bright)
        private set
    var mTextColor : Int = context.resources.getColor(R.color.black)


    fun backgroundColor(backgroundColor: Int) = apply { this.mBackgroundColor = backgroundColor }

    fun radius(radius: Int) = apply { this.mRadius = radius }

    fun shadowColor(shadowColor: Int) = apply { this.mShadowColor = shadowColor }

    fun shadowHeight(shadowHeight: Int) = apply { this.mShadowHeight = shadowHeight }

    fun rippleColor(rippleColor: Int) = apply { this.mRippleColor = rippleColor }

    fun textColor(textColor: Int) = apply { this.mTextColor = textColor }

    fun build(ahmetButton: AhmetButton) = AhmetButton(context, this, ahmetButton)


}




