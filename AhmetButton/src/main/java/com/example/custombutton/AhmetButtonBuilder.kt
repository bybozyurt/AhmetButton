package com.example.custombutton

import android.content.Context


/**
 * Created by Ahmet Bozyurt on 7.12.2021
 */




class AhmetButtonBuilder(
    val context: Context
) {
    var ab_bg_color: Int = context.resources.getColor(R.color.default_background_color)
        private set
    var ab_radius: Int = 8
        private set
    var ab_shadow_color: Int = context.resources.getColor(R.color.default_text_color)
        private set
    var ab_shadow_height: Int = 12
        private set
    var ab_rippleColor: Int = context.resources.getColor(R.color.holo_blue_bright)
        private set
    var ab_txt_color : Int = context.resources.getColor(R.color.black)

    fun AbBgColor(ab_bg_color: Int) = apply { this.ab_bg_color = ab_bg_color }

    fun AbRadius(ab_radius: Int) = apply { this.ab_radius = ab_radius }

    fun AbShadowColor(ab_shadow_color: Int) = apply { this.ab_shadow_color = ab_shadow_color }

    fun AbShadowHeight(ab_shadow_height: Int) =
        apply { this.ab_shadow_height = ab_shadow_height }

    fun AbRippleColor(ab_rippleColor: Int) = apply { this.ab_rippleColor = ab_rippleColor }

    fun AbTxtColor(ab_txt_color: Int) = apply { this.ab_txt_color = ab_txt_color }

    fun build(ahmetButton: AhmetButton) = AhmetButton(context, this, ahmetButton)


}




