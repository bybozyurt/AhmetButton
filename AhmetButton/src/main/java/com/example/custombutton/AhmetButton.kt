package com.example.custombutton

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
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
    private var mElevation: Int = DEFAULT_ELEVATION
    //var isShadowColorDefined = false

    private lateinit var layerDrawable: LayerDrawable
    private lateinit var pressedDrawable: Drawable
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

        //isShadowColorDefined = true

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

        if (mShadowAttributes.ab_shadow_enabled){
            pressedDrawable = createDrawable(
                mDrawableNormal.ab_radius, Color.TRANSPARENT, mDrawableNormal.ab_bg_color)
            unPressedDrawable = createDrawable(
                mDrawableNormal.ab_radius, mDrawableNormal.ab_bg_color, mShadowAttributes.ab_shadow_color)

        }
        else{
            mShadowAttributes.ab_shadow_height = 0
            pressedDrawable = createDrawable(
                mDrawableNormal.ab_radius, mShadowAttributes.ab_shadow_color, Color.TRANSPARENT)
            unPressedDrawable = createDrawable(
                mDrawableNormal.ab_radius, mDrawableNormal.ab_bg_color, Color.TRANSPARENT)

        }

        setButtonBackground(unPressedDrawable)

    }

    private fun createDrawable(rad: Int, topColor: Int, bottomColor: Int) : LayerDrawable{

        val radius = rad.toFloat()

//        drawable.cornerRadii =
//            floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)

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

        if(mShadowAttributes.ab_shadow_enabled && topColor != Color.TRANSPARENT){
            //unpressed drawable
            layerDrawable.setLayerInset(0,0,0,0,0) /*index, left, top, right, bottom*/
        }
        else{
            //pressed drawable
            layerDrawable.setLayerInset(0,0,mShadowAttributes.ab_shadow_height,0,0)
        }
        layerDrawable.setLayerInset(1,0,0,0,mShadowAttributes.ab_shadow_height)

        return layerDrawable


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



    fun setBtnTextColorNormal(color: Int) {
        this.mTextColorNormal = color
        setupButton()
    }

    fun setBtnBackgroundColorNormal(color: Int) {
        mDrawableNormal.ab_bg_color = color
        setupButton()
    }

    fun setBtnRadius(radius: Int) {
        if (radius >= 0) {
            mDrawableNormal.ab_radius = DimensionUtil.dipToPx(radius.toFloat())
            setupButton()
        }
    }

    fun setBtnTextColorPressed(color: Int) {
        this.mTextColorPressed = color
        setupButton()
    }

    fun setBtnBackgroundColorPressed(color: Int) {
        mDrawablePressed.ab_bg_color = color
        setupButton()
    }

    fun setBtnRadiusPressed(radius: Int) {
        if (radius >= 0) {
            mDrawablePressed.ab_radius = DimensionUtil.dipToPx(radius.toFloat())
            setupButton()
        }
    }



}