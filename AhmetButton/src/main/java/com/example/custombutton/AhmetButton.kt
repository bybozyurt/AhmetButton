package com.example.custombutton

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import com.example.custombutton.attribute.DrawableAttributes
import com.example.custombutton.attribute.RippleAttributes
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
        val DEFAULT_BORDER_RADIUS = 2
        val DEFAULT_COLOR_FACTOR = 0.8f
        val DEFAULT_ELEVATION = 0
        val DEFAULT_BUTTON_ENABLED = true
    }

    private var mTextColorNormal: Int =
        ColorUtil.getTextColorFromBackgroundColor(mDrawableNormal.backgroundColor)
    private var mTextColorPressed: Int =
        ColorUtil.getTextColorFromBackgroundColor(mDrawablePressed.backgroundColor)
    private var mEnabled: Boolean = true
    private var mElevation: Int = DEFAULT_ELEVATION

    private lateinit var mRippleAttributes: RippleAttributes
    private lateinit var mDrawableNormal: DrawableAttributes
    private lateinit var mDrawablePressed: DrawableAttributes


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

        // normal state
        mDrawableNormal = DrawableAttributes()
        mDrawableNormal.backgroundColor = typedArray.getColor(
            R.styleable.AhmetButton_ab_backgroundColorNormal,
            DEFAULT_BACKGROUND_COLOR
        )

        mDrawableNormal.borderRadius = typedArray.getDimension(
            R.styleable.AhmetButton_ab_borderRadiusNormal,
            DEFAULT_BORDER_RADIUS.toFloat()
        ).toInt()
        mTextColorNormal = typedArray.getColor(
            R.styleable.AhmetButton_ab_textColorNormal,
            ColorUtil.getTextColorFromBackgroundColor(mDrawableNormal.backgroundColor)
        )


                    // pressed/focused state
        mDrawablePressed = DrawableAttributes()
        mDrawablePressed.backgroundColor = typedArray.getColor(
            R.styleable.AhmetButton_ab_backgroundColorPressed,
            ColorUtil.darkenLightenColor(mDrawableNormal.backgroundColor, DEFAULT_COLOR_FACTOR)
        )
        mDrawablePressed.borderRadius = typedArray.getDimension(
            R.styleable.AhmetButton_ab_borderRadiusPressed,
            mDrawableNormal.borderRadius.toFloat()
        ).toInt()
        mTextColorPressed = typedArray.getColor(
            R.styleable.AhmetButton_ab_textColorPressed,
            ColorUtil.getTextColorFromBackgroundColor(mDrawablePressed.backgroundColor)
        )


        // general
        mEnabled = typedArray.getBoolean(R.styleable.AhmetButton_ab_enabled, DEFAULT_BUTTON_ENABLED)
        mEnabled = typedArray.getBoolean(R.styleable.AhmetButton_android_enabled, mEnabled)

        mRippleAttributes = RippleAttributes()
        mRippleAttributes.isUseRippleEffect = typedArray.getBoolean(
            R.styleable.AhmetButton_ab_useRippleEffect,
            DEFAULT_USE_RIPPLE_EFFECT
        )
        mRippleAttributes.rippleColor =
            typedArray.getColor(R.styleable.AhmetButton_ab_rippleColor, DEFAULT_RIPPLE_COLOR)
        mRippleAttributes.rippleOpacity =
            typedArray.getFloat(R.styleable.AhmetButton_ab_rippleOpacity, DEFAULT_RIPPLE_OPACITY)

        typedArray.recycle()


    }

    /**
     * Setup button background and text
     */

    private fun setupButton() {

        setButtonTextColor()

        this.isEnabled = mEnabled
        this.setPadding(DimensionUtil.dipToPx(16f), 0, DimensionUtil.dipToPx(16f), 0)


        stateListAnimator = null // remove default elevation effect
        elevation = mElevation.toFloat()


        if (mEnabled && mRippleAttributes.isUseRippleEffect) {
            setButtonBackground(
                DrawableFactory.getRippleDrawable(
                    mDrawableNormal,
                    mRippleAttributes
                )
            )
        } else {
            setButtonBackground(getButtonBackgroundStateList())
        }

    }

    /**
     * Set button text
     */

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

        //mTextColorPressed = mTextColorNormal

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

    fun setTextColorNormal(color: Int) {
        this.mTextColorNormal = color
        setupButton()
    }

    fun setBackgroundColorNormal(color: Int) {
        mDrawableNormal.backgroundColor = color
        setupButton()
    }

    fun setBorderRadiusNormal(radius: Int) {
        if (radius >= 0) {
            mDrawableNormal.borderRadius = DimensionUtil.dipToPx(radius.toFloat())
            setupButton()
        }
    }

    fun setTextColorPressed(color: Int) {
        this.mTextColorPressed = color
        setupButton()
    }

    fun setBackgroundColorPressed(color: Int) {
        mDrawablePressed.backgroundColor = color
        setupButton()
    }

    fun setBorderRadiusPressed(radius: Int) {
        if (radius >= 0) {
            mDrawablePressed.borderRadius = DimensionUtil.dipToPx(radius.toFloat())
            setupButton()
        }
    }

    fun setTextStyle(textStyle: Int) {
        this.setTypeface(this.typeface, textStyle)
    }





}