package com.example.custombutton

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.example.custombutton.attribute.RippleAttributes
import com.example.custombutton.attribute.ShadowAttributes
import com.example.custombutton.util.ColorUtil
import com.example.custombutton.util.DimensionUtil


/**
 * Created by Ahmet Bozyurt on 6.12.2021
 */
class AhmetButton : AppCompatButton {

    var mBackgroundColor: Int = resources.getColor(R.color.default_background_color)
    var mRadius: Int = DEFAULT_RADIUS
    var mShadowColor: Int = resources.getColor(R.color.default_ripple_color)
    var mShadowHeight: Int = 5
    var mRippleColor: Int = resources.getColor(R.color.color_orange)

    companion object {
        val DEFAULT_USE_RIPPLE_EFFECT = false
        val DEFAULT_RIPPLE_COLOR = -0x1000000
        val DEFAULT_RIPPLE_OPACITY = 0.26f
        var DEFAULT_BACKGROUND_COLOR = -0x237213
        val DEFAULT_RADIUS = 16
        val DEFAULT_ELEVATION = 0
        val DEFAULT_BUTTON_ENABLED = true
        val DEFAULT_BUTTON_SHADOW_ENABLED = true
        val DEFAULT_BUTTON_SHADOW_COLOR = 0
        val DEFAULT_BUTTON_SHADOW_HEIGHT = 16
        val DEFAULT_BUTTON_SHADOW_BRIGHTNESS = true
        val DEFAULT_PADDING_TOP = 35
        val DEFAULT_PADDING_BOTTOM = 35
    }

    private var mEnabled: Boolean = true
    private var ShadowColorBrightness: Boolean = false
    private var mElevation: Int = DEFAULT_ELEVATION

    private var mTextColorNormal: Int =
        ColorUtil.getTextColorFromBackgroundColor(mBackgroundColor)

    private lateinit var mDrawable: Drawable
    private var mRippleAttributes: RippleAttributes = RippleAttributes()
    private var mShadowAttributes: ShadowAttributes = ShadowAttributes()

    constructor(context: Context) : super(context) {
        initAttributes(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttributes(attrs)
    }

    constructor(
        context: Context,
        ahmetButtonBuilder: AhmetButtonBuilder,
        ahmetButton: AhmetButton
    ) : super(context) {

        ahmetButton.mBackgroundColor = ahmetButtonBuilder.mBackgroundColor
        ahmetButton.mRadius = ahmetButtonBuilder.mRadius
        ahmetButton.mShadowColor = ahmetButtonBuilder.mShadowColor
        ahmetButton.mShadowHeight = ahmetButtonBuilder.mShadowHeight
        ahmetButton.mRippleColor = ahmetButtonBuilder.mRippleColor
        ahmetButton.mTextColorNormal = ahmetButtonBuilder.mTextColor
        ahmetButton.mRippleAttributes.mRippleOpacity = ahmetButtonBuilder.mRippleOpacity
        ahmetButton.setupButton()

    }

    private fun initAttributes(mAttrs: AttributeSet?) {

        val typedArray =
            context.obtainStyledAttributes(mAttrs, R.styleable.AhmetButton)

        mBackgroundColor = typedArray.getColor(
            R.styleable.AhmetButton_ab_backgroundColorNormal,
            DEFAULT_BACKGROUND_COLOR
        )

        mRadius = typedArray.getDimension(
            R.styleable.AhmetButton_ab_radius,
            DEFAULT_RADIUS.toFloat()
        ).toInt()
        mTextColorNormal = typedArray.getColor(
            R.styleable.AhmetButton_ab_textColorNormal,
            ColorUtil.getTextColorFromBackgroundColor(mBackgroundColor)
        )


        mEnabled = typedArray.getBoolean(R.styleable.AhmetButton_ab_enabled, DEFAULT_BUTTON_ENABLED)
        mEnabled = typedArray.getBoolean(R.styleable.AhmetButton_android_enabled, mEnabled)

        ShadowColorBrightness =
            typedArray.getBoolean(
                R.styleable.AhmetButton_ab_shadowColorBrightness,
                DEFAULT_BUTTON_SHADOW_BRIGHTNESS
            )


        mRippleAttributes.mIsRippleEffect = typedArray.getBoolean(
            R.styleable.AhmetButton_ab_useRippleEffect,
            DEFAULT_USE_RIPPLE_EFFECT
        )
        mRippleColor =
            typedArray.getColor(R.styleable.AhmetButton_ab_rippleColor, DEFAULT_RIPPLE_COLOR)
        mRippleAttributes.mRippleOpacity =
            typedArray.getFloat(R.styleable.AhmetButton_ab_rippleOpacity, DEFAULT_RIPPLE_OPACITY)


        mShadowColor =
            typedArray.getColor(R.styleable.AhmetButton_ab_shadowColor, DEFAULT_BUTTON_SHADOW_COLOR)


        mShadowHeight = typedArray.getDimension(
            R.styleable.AhmetButton_ab_shadowHeight, DEFAULT_BUTTON_SHADOW_HEIGHT.toFloat()
        ).toInt()

        mShadowAttributes.mShadowEnabled = typedArray.getBoolean(
            R.styleable.AhmetButton_ab_shadowEnabled, DEFAULT_BUTTON_SHADOW_ENABLED
        )
        typedArray.recycle()
        setupButton()

    }

    private fun setButtonBackground(drawable: Drawable) {
        this.background = drawable
    }


    private fun setupButton() {
        setButtonTextColor()

        this.isEnabled = mEnabled
        this.setPadding(DimensionUtil.dipToPx(16f), 0, DimensionUtil.dipToPx(16f), 0)

        stateListAnimator = null
        elevation = mElevation.toFloat()

        val alpha = Color.alpha(mBackgroundColor)
        val hsv = FloatArray(3)
        Color.colorToHSV(mBackgroundColor, hsv)
        hsv[2] *= 0.8f

        if (ShadowColorBrightness) {
            mShadowColor = Color.HSVToColor(alpha, hsv)
        }

        if (mEnabled && mRippleAttributes.mIsRippleEffect || mShadowAttributes.mShadowEnabled) {

            mDrawable = createDrawable(
                mRadius, mBackgroundColor,
                mShadowColor
            )

            setButtonBackground(
                getRippleDrawable(
                    mRippleAttributes, mDrawable as LayerDrawable
                )
            )


        } else {
            mDrawable = createDrawable(
                mRadius, mBackgroundColor,
                Color.TRANSPARENT
            )
            setButtonBackground(getButtonBackgroundStateList())
        }

        this.setPadding(
            0,
            DEFAULT_PADDING_TOP + mShadowHeight,
            0,
            DEFAULT_PADDING_BOTTOM + mShadowHeight
        )


    }


    private fun setButtonTextColor() {

        this.setTextColor(getButtonTextColorStateList())

    }

    private fun getButtonBackgroundStateList(): Drawable {

        val drawableNormal = getBackgroundDrawable()
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

    fun getBackgroundDrawable(): Drawable {

        val drawable = GradientDrawable()

        val cornerRadius = mRadius.toFloat()

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

        drawable.setColor(mBackgroundColor)


        return drawable

    }

    fun getRippleDrawable(

        rippleAttributes: RippleAttributes,
        layerDrawable: LayerDrawable
    ): Drawable {

        val drawableNormal = getBackgroundDrawable()
        var mask = GradientDrawable()

        if (drawableNormal.constantState != null) {
            mask = drawableNormal.constantState!!.newDrawable() as GradientDrawable
            mask.setColor(Color.WHITE)
        }

        return RippleDrawable(
            ColorUtil.getRippleColorFromColor(
                mRippleColor,
                rippleAttributes.mRippleOpacity
            ), layerDrawable, mask
        )

    }

    fun createDrawable(
        rad: Int,
        //Background
        topColor: Int,
        //Shadow
        bottomColor: Int
    ): Drawable {

        val radius = rad.toFloat()
        val outerRadius = floatArrayOf(
            radius,
            radius,
            radius, radius, radius, radius, radius, radius
        )

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

        layerDrawable.setLayerInset(1, 0, 0, 0, mShadowHeight)

        return layerDrawable

    }


    fun setBtnBackgroundColor(color: Int): AhmetButton {
        mBackgroundColor = color
        setupButton()
        return this
    }


    fun setBtnShadowColor(color: Int): AhmetButton {
        mShadowColor = color
        setupButton()
        return this
    }

    fun setBtnShadowHeight(height: Int) {
        mShadowHeight = height
        setupButton()
    }

    fun setBtnRadius(radius: Int): AhmetButton {
        if (radius >= 0) {
            mRadius = radius
            setupButton()
        }
        return this

    }

    fun setRippleColor(color: Int) {
        mRippleColor = color
        setupButton()
    }

    fun setRippleOpacity(rippleOpacity: Int) {
        mRippleAttributes.mRippleOpacity = rippleOpacity.toFloat()
        setupButton()
    }


}