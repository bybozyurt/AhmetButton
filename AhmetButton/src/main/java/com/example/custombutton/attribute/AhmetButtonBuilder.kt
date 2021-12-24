package com.example.custombutton.attribute

import android.app.Dialog
import android.content.Context
import android.util.AttributeSet
import androidx.annotation.NonNull
import com.example.custombutton.AhmetButton


/**
 * Created by Ahmet Bozyurt on 7.12.2021
 */







//class AhmetButtonBuilder (
//    var ab_bg_color : Int,
//    var ab_radius : Int
//){
//
//    private constructor(builder : Builder) : this(builder.ab_bg_color, builder.ab_radius)
//
//    class Builder {
//        var ab_bg_color: Int = 0
//        var ab_radius: Int = 0
//
//        fun AbBgColor(color: Int) : Builder {
//            this.ab_bg_color = color
//            return this
//        }
//
//        fun AbRadius(ab_radius: Int) = apply { this.ab_radius = ab_radius }
//
//
//        fun build() = AhmetButtonBuilder(this)
//
//
//    }
//}

class AhmetButtonBuilder constructor(
    var ab_bg_color : Int,
    var ab_radius : Int

){
    data class Builder(
        var ab_bg_color: Int = 0,
        var ab_radius: Int = 0,

    )
    {
        //fun AbBgColor(ab_bg_color: Int) = apply { this.ab_bg_color = ab_bg_color },

//        fun AbBgColor(ab_bg_color: Int) : AhmetButton {
//            this.ab_bg_color = ab_bg_color
//            return this
//        }

        fun AbRadius(ab_radius: Int) = apply { this.ab_radius = ab_radius }


        fun build() : AhmetButtonBuilder = AhmetButtonBuilder(ab_bg_color, ab_radius)
    }

}







