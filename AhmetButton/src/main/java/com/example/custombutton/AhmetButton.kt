package com.example.custombutton

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.custombutton.attribute.DrawableAttributes
import com.example.custombutton.attribute.RippleAttributes
import com.example.custombutton.attribute.ShadowAttributes
import com.example.custombutton.util.ColorUtil
import com.example.custombutton.util.DimensionUtil
import com.example.custombutton.util.DrawableFactory

/**
 * Created by Ahmet Bozyurt on 6.12.2021
 */
class AhmetButton : BaseButton {
    companion object {
        val DEFAULT_USE_RIPPLE_EFFECT = false
        val DEFAULT_RIPPLE_COLOR = -0x1000000 //0xff000000
        val DEFAULT_RIPPLE_OPACITY = 0.26f
        val DEFAULT_BACKGROUND_COLOR = -0x333334 //0xffcccccc
        val DEFAULT_RADIUS = 2
        val DEFAULT_COLOR_FACTOR = 0.8f
        val DEFAULT_ELEVATION = 0
        val DEFAULT_BUTTON_ENABLED = true
        val DEFAULT_BUTTON_SHADOW_ENABLED = true
        val DEFAULT_BUTTON_SHADOW_COLOR = 0
        val DEFAULT_BUTTON_SHADOW_HEIGHT = 3
    }

    private var mTextColorNormal: Int =
        ColorUtil.getTextColorFromBackgroundColor(mDrawableNormal.ab_bg_color)
    private var mTextColorPressed: Int =
        ColorUtil.getTextColorFromBackgroundColor(mDrawablePressed.ab_bg_color)

    private var mEnabled: Boolean = true
    private var isShadowColorDefined : Boolean = false
    private var mElevation: Int = DEFAULT_ELEVATION

    private lateinit var unPressedDrawable: Drawable

    private lateinit var mRippleAttributes: RippleAttributes
    private lateinit var mDrawableNormal: DrawableAttributes
    private lateinit var mDrawablePressed: DrawableAttributes
    private lateinit var mShadowAttributes : ShadowAttributes



    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initButton() {
        initAttributes()
        setupButton()
    }


    private fun initAttributes() {


        val typedArray =
            mContext.obtainStyledAttributes(mAttrs, R.styleable.AhmetButton, mDefStyleAttr!!, 0)

        // normal state
        mDrawableNormal = DrawableAttributes()
        mDrawableNormal.ab_bg_color = typedArray.getColor(
            R.styleable.AhmetButton_ab_backgroundColorNormal,
            DEFAULT_BACKGROUND_COLOR
        )

        mDrawableNormal.ab_radius = typedArray.getDimension(
            R.styleable.AhmetButton_ab_radius,
            DEFAULT_RADIUS.toFloat()
        ).toInt()
        mTextColorNormal = typedArray.getColor(
            R.styleable.AhmetButton_ab_textColorNormal,
            ColorUtil.getTextColorFromBackgroundColor(mDrawableNormal.ab_bg_color)
        )


                    // pressed/focused state
        mDrawablePressed = DrawableAttributes()
        mDrawablePressed.ab_bg_color = typedArray.getColor(
            R.styleable.AhmetButton_ab_backgroundColorPressed,
            ColorUtil.darkenLightenColor(mDrawableNormal.ab_bg_color, DEFAULT_COLOR_FACTOR)
        )
        mDrawablePressed.ab_radius = typedArray.getDimension(
            R.styleable.AhmetButton_ab_borderRadiusPressed,
            mDrawableNormal.ab_radius.toFloat()
        ).toInt()
        mTextColorPressed = typedArray.getColor(
            R.styleable.AhmetButton_ab_textColorPressed,
            ColorUtil.getTextColorFromBackgroundColor(mDrawablePressed.ab_bg_color)
        )
        // general
        mEnabled = typedArray.getBoolean(R.styleable.AhmetButton_ab_enabled, DEFAULT_BUTTON_ENABLED)
        mEnabled = typedArray.getBoolean(R.styleable.AhmetButton_android_enabled, mEnabled)

        isShadowColorDefined = typedArray.getBoolean(R.styleable.AhmetButton_ab_shadowColorDefined,false)

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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setupButton() {

        setButtonTextColor()

        this.isEnabled = mEnabled
        this.setPadding(DimensionUtil.dipToPx(16f), 0, DimensionUtil.dipToPx(16f), 0)


        stateListAnimator = null // remove default elevation effect
        elevation = mElevation.toFloat()

        val alpha = Color.alpha(mDrawableNormal.ab_bg_color)
        val hsv = FloatArray(3)
        Color.colorToHSV(mDrawableNormal.ab_bg_color, hsv)
        hsv[2] *= 0.8f
        
        if (isShadowColorDefined) {
            mShadowAttributes.ab_shadow_color = Color.HSVToColor(alpha, hsv)
        }

        if (mEnabled && mRippleAttributes.isUseRippleEffect || mShadowAttributes.ab_shadow_enabled) {
            Log.d("TAG","selam")

            unPressedDrawable = DrawableFactory.createDrawable(
                mDrawableNormal.ab_radius, mDrawableNormal.ab_bg_color,
                mShadowAttributes.ab_shadow_color, mShadowAttributes,
                mDrawableNormal, mRippleAttributes)

            setButtonBackground(
                DrawableFactory.getRippleDrawable(
                mDrawableNormal, mRippleAttributes, unPressedDrawable as LayerDrawable
            )
            )

        } else {
            unPressedDrawable = DrawableFactory.createDrawable(
                mDrawableNormal.ab_radius, mDrawableNormal.ab_bg_color,
                Color.TRANSPARENT, mShadowAttributes, mDrawableNormal, mRippleAttributes)
            setButtonBackground(getButtonBackgroundStateList())
        }


    }



    private fun setButtonTextColor() {

        this.setTextColor(getButtonTextColorStateList())

    }

    private fun getButtonBackgroundStateList(): Drawable {

        val drawableNormal = DrawableFactory.getBackgroundDrawable(mDrawableNormal)
        val drawablePressed = DrawableFactory.getBackgroundDrawable(mDrawablePressed)

        val states = StateListDrawable()

        states.addState(
            intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled),drawablePressed

        )
        states.addState(
            intArrayOf(android.R.attr.state_focused, android.R.attr.state_enabled),
            drawablePressed
        )
        states.addState(intArrayOf(), drawableNormal)

        return states
    }

    private fun getButtonTextColorStateList(): ColorStateList {

        return ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_focused, android.R.attr.state_pressed),
                intArrayOf()
            ),

            intArrayOf(
                mTextColorPressed,
                mTextColorNormal,
                mTextColorPressed,
                mTextColorNormal
            )

        )
    }



    @RequiresApi(Build.VERSION_CODES.N)
    fun setBtnTextColorNormal(color: Int) {
        this.mTextColorNormal = color
        setupButton()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setBtnBackgroundColorNormal(color: Int) {
        mDrawableNormal.ab_bg_color = color
        setupButton()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun setBtnShadowColor(color: Int){
        mShadowAttributes.ab_shadow_color = color
        setupButton()
    }



    @RequiresApi(Build.VERSION_CODES.N)
    fun setBtnShadowHeight(height : Int){
        mShadowAttributes.ab_shadow_height = height
        setupButton()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setBtnShadowEnabled(enabled : Boolean){
        mShadowAttributes.ab_shadow_enabled = enabled
        setupButton()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setBtnRadius(radius: Int) {
        if (radius >= 0) {
            mDrawableNormal.ab_radius = DimensionUtil.dipToPx(radius.toFloat())
            setupButton()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setBtnRadiusPressed(radius: Int) {
        if (radius >= 0) {
            mDrawablePressed.ab_radius = DimensionUtil.dipToPx(radius.toFloat())
            setupButton()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setBtnTextColorPressed(color: Int) {
        this.mTextColorPressed = color
        setupButton()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setBtnBackgroundColorPressed(color: Int) {
        mDrawablePressed.ab_bg_color = color
        setupButton()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setRippleColor(color: Int){
        mRippleAttributes.rippleColor = color
        setupButton()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setRippleOpacity(opacity : Int){
        mRippleAttributes.rippleOpacity = opacity.toFloat()
        setupButton()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setRippleEffect(enabled: Boolean){
        mRippleAttributes.isUseRippleEffect = enabled
        setupButton()
    }

    fun getBtnRadius() : Int{
        return mDrawableNormal.ab_radius
    }

    fun getRippleEffectEnabled() : Boolean {
        return mRippleAttributes.isUseRippleEffect
    }

    fun getShadowEnabled() : Boolean {
        return mShadowAttributes.ab_shadow_enabled
    }

    fun getBtnTextColorNormal() : Int {
        return mTextColorNormal
    }

    fun getBtnBackgroundColorNormal() : Int{
        return mDrawableNormal.ab_bg_color
    }

    fun getBtnShadowColor() : Int{
        return mShadowAttributes.ab_shadow_color
    }

    fun getBtnShadowHeight() : Int {
        return mShadowAttributes.ab_shadow_height
    }









}