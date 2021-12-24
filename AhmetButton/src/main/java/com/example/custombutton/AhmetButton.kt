package com.example.custombutton

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import com.example.custombutton.attribute.AhmetButtonBuilder
import com.example.custombutton.attribute.RippleAttributes
import com.example.custombutton.attribute.ShadowAttributes
import com.example.custombutton.util.ColorUtil
import com.example.custombutton.util.DimensionUtil
import com.example.custombutton.util.DrawableFactory


/**
 * Created by Ahmet Bozyurt on 6.12.2021
 */
open class AhmetButton : BaseButton {

    companion object {
        val DEFAULT_USE_RIPPLE_EFFECT = false
        val DEFAULT_RIPPLE_COLOR = -0x1000000 //0xff000000
        val DEFAULT_RIPPLE_OPACITY = 0.26f
        val DEFAULT_BACKGROUND_COLOR = -0x333334 //0xffcccccc
        val DEFAULT_RADIUS = 12
        val DEFAULT_ELEVATION = 0
        val DEFAULT_BUTTON_ENABLED = true
        val DEFAULT_BUTTON_SHADOW_ENABLED = true
        val DEFAULT_BUTTON_SHADOW_COLOR = 0
        val DEFAULT_BUTTON_SHADOW_HEIGHT = 3
    }


    private var mTextColorNormal: Int =
        ColorUtil.getTextColorFromBackgroundColor(mAhmetButtonBuilder.ab_bg_color)

    private var mEnabled: Boolean = true
    private var isShadowColorDefined: Boolean = false
    private var mElevation: Int = DEFAULT_ELEVATION

    private lateinit var unPressedDrawable: Drawable

    private lateinit var mRippleAttributes: RippleAttributes
    private lateinit var mAhmetButtonBuilder: AhmetButtonBuilder
    private lateinit var mShadowAttributes: ShadowAttributes


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun initButton() {
        initAttributes()
        setupButton()
    }


    private fun initAttributes() {



        val typedArray =
            mContext.obtainStyledAttributes(mAttrs, R.styleable.AhmetButton, mDefStyleAttr!!, 0)


        mAhmetButtonBuilder = AhmetButtonBuilder(0, 0)
        mAhmetButtonBuilder.ab_bg_color = typedArray.getColor(
            R.styleable.AhmetButton_ab_backgroundColorNormal,
            DEFAULT_BACKGROUND_COLOR
        )

        mAhmetButtonBuilder.ab_radius = typedArray.getDimension(
            R.styleable.AhmetButton_ab_radius,
            DEFAULT_RADIUS.toFloat()
        ).toInt()
        mTextColorNormal = typedArray.getColor(
            R.styleable.AhmetButton_ab_textColorNormal,
            ColorUtil.getTextColorFromBackgroundColor(mAhmetButtonBuilder.ab_bg_color)
        )


        mEnabled = typedArray.getBoolean(R.styleable.AhmetButton_ab_enabled, DEFAULT_BUTTON_ENABLED)
        mEnabled = typedArray.getBoolean(R.styleable.AhmetButton_android_enabled, mEnabled)

        isShadowColorDefined =
            typedArray.getBoolean(R.styleable.AhmetButton_ab_shadowColorBrightness, false)

        mRippleAttributes = RippleAttributes()
        mRippleAttributes.isUseRippleEffect = typedArray.getBoolean(
            R.styleable.AhmetButton_ab_useRippleEffect,
            DEFAULT_USE_RIPPLE_EFFECT
        )
        mRippleAttributes.rippleColor =
            typedArray.getColor(R.styleable.AhmetButton_ab_rippleColor, DEFAULT_RIPPLE_COLOR)
        mRippleAttributes.rippleOpacity =
            typedArray.getFloat(R.styleable.AhmetButton_ab_rippleOpacity, DEFAULT_RIPPLE_OPACITY)

        mShadowAttributes = ShadowAttributes()



        mShadowAttributes.ab_shadow_color =
            typedArray.getColor(R.styleable.AhmetButton_ab_shadowColor, DEFAULT_BUTTON_SHADOW_COLOR)


        mShadowAttributes.ab_shadow_height = typedArray.getDimension(
            R.styleable.AhmetButton_ab_shadowHeight, DEFAULT_BUTTON_SHADOW_HEIGHT.toFloat()).toInt()

        mShadowAttributes.ab_shadow_enabled = typedArray.getBoolean(
            R.styleable.AhmetButton_ab_shadowEnabled, DEFAULT_BUTTON_SHADOW_ENABLED)



        typedArray.recycle()


    }


    private fun setupButton() {

        setButtonTextColor()

        this.isEnabled = mEnabled
        this.setPadding(DimensionUtil.dipToPx(16f), 0, DimensionUtil.dipToPx(16f), 0)


        stateListAnimator = null
        elevation = mElevation.toFloat()

        val alpha = Color.alpha(mAhmetButtonBuilder.ab_bg_color)
        val hsv = FloatArray(3)
        Color.colorToHSV(mAhmetButtonBuilder.ab_bg_color, hsv)
        hsv[2] *= 0.8f
        
        if (isShadowColorDefined) {
            mShadowAttributes.ab_shadow_color = Color.HSVToColor(alpha, hsv)
        }

        if (mEnabled && mRippleAttributes.isUseRippleEffect || mShadowAttributes.ab_shadow_enabled) {

            unPressedDrawable = DrawableFactory.createDrawable(
                mAhmetButtonBuilder.ab_radius, mAhmetButtonBuilder.ab_bg_color,
                mShadowAttributes.ab_shadow_color, mShadowAttributes
            )

            setButtonBackground(
                DrawableFactory.getRippleDrawable(
                    mAhmetButtonBuilder, mRippleAttributes, unPressedDrawable as LayerDrawable
                )
            )



        } else {
            unPressedDrawable = DrawableFactory.createDrawable(
                mAhmetButtonBuilder.ab_radius, mAhmetButtonBuilder.ab_bg_color,
                Color.TRANSPARENT, mShadowAttributes
            )
            setButtonBackground(getButtonBackgroundStateList())
        }

        this.setPadding(0,35+mShadowAttributes.ab_shadow_height,0,35+mShadowAttributes.ab_shadow_height)



    }



    private fun setButtonTextColor() {

        this.setTextColor(getButtonTextColorStateList())

    }

    private fun getButtonBackgroundStateList(): Drawable {

        val drawableNormal = DrawableFactory.getBackgroundDrawable(mAhmetButtonBuilder)
        val states = StateListDrawable()

        states.addState(
            intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled), drawableNormal
        )
        states.addState(
            intArrayOf(android.R.attr.state_focused, android.R.attr.state_enabled), drawableNormal

        )
        states.addState(intArrayOf(), drawableNormal)

        return states
    }

    private fun getButtonTextColorStateList(): ColorStateList {

        return ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf()
            ),

            intArrayOf(
                mTextColorNormal,
                mTextColorNormal
            )
        )

    }




    fun setBtnTextColorNormal(color: Int) {
        this.mTextColorNormal = color
        setupButton()
    }


    fun setBtnBackgroundColorNormal(color: Int) : AhmetButton{
        mAhmetButtonBuilder.ab_bg_color = color
        //mDrawableAttributes.ab_bg_color = DrawableAttributes.Builder().ab_bg_color(color)
        //DrawableAttributes.Builder().ab_bg_color(color).build()
        //DrawableAttributes.Builder().AbBgColor(color).build()
        setupButton()
        return this

    }



    fun setBtnShadowColor(color: Int) : AhmetButton{
        mShadowAttributes.ab_shadow_color = color
        setupButton()
        return this
    }


    fun setBtnShadowHeight(height : Int){
        mShadowAttributes.ab_shadow_height = height
        setupButton()
    }


    fun setBtnShadowEnabled(enabled : Boolean){
        mShadowAttributes.ab_shadow_enabled = enabled
        setupButton()
    }


    fun setBtnRadius(radius: Int) : AhmetButton{
        if (radius >= 0) {
            mAhmetButtonBuilder.ab_radius = radius
            //DrawableAttributesBuilder(ab_radius = DimensionUtil.dipToPx(radius.toFloat())).build()
            //mDrawableAttributes = DrawableAttributes.Builder(this.context).ab_radius(radius).build()
            setupButton()
        }
        return this

    }


    fun setRippleColor(color: Int){
        mRippleAttributes.rippleColor = color
        setupButton()
    }


    fun setRippleOpacity(opacity : Int){
        mRippleAttributes.rippleOpacity = opacity.toFloat()
        setupButton()
    }


    fun setRippleEffect(enabled: Boolean){
        mRippleAttributes.isUseRippleEffect = enabled
        setupButton()
    }

    fun getBtnRadius() : Int {
        return mAhmetButtonBuilder.ab_radius
        //return DrawableAttributesBuilder().getAbRadius().toString()
    }

    fun getRippleEffectEnabled() : Boolean {
        return mRippleAttributes.isUseRippleEffect
    }

    fun getShadowEnabled(): Boolean {
        return mShadowAttributes.ab_shadow_enabled
    }

    fun getBtnTextColorNormal(): Int {
        return mTextColorNormal
    }

    fun getBtnBackgroundColorNormal(): Int {
        return mAhmetButtonBuilder.ab_bg_color

        //return DrawableAttributes.Builder().ab_bg_color
    }

    fun getBtnShadowColor(): Int {
        return mShadowAttributes.ab_shadow_color
    }

    fun getBtnShadowHeight(): Int {
        return mShadowAttributes.ab_shadow_height
    }


}