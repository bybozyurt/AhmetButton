package com.example.custombutton

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import com.example.custombutton.attribute.RippleAttributes
import com.example.custombutton.attribute.ShadowAttributes
import com.example.custombutton.util.ColorUtil
import com.example.custombutton.util.DimensionUtil


/**
 * Created by Ahmet Bozyurt on 6.12.2021
 */
class AhmetButton : AppCompatButton {

    var ab_bg_color: Int = resources.getColor(R.color.default_background_color)
    var ab_radius: Int = DEFAULT_RADIUS
    var ab_shadow_color: Int = resources.getColor(R.color.default_border_color)
    var ab_shadow_height: Int = 5
    var ab_ripple_color: Int = resources.getColor(R.color.default_pressed_color)

    companion object {
        val DEFAULT_USE_RIPPLE_EFFECT = false
        val DEFAULT_RIPPLE_COLOR = -0x1000000 //0xff000000
        val DEFAULT_RIPPLE_OPACITY = 0.26f
        var DEFAULT_BACKGROUND_COLOR = -0x333334 //0xffcccccc
        val DEFAULT_RADIUS = 12
        val DEFAULT_ELEVATION = 0
        val DEFAULT_BUTTON_ENABLED = true
        val DEFAULT_BUTTON_SHADOW_ENABLED = true
        val DEFAULT_BUTTON_SHADOW_COLOR = 0
        val DEFAULT_BUTTON_SHADOW_HEIGHT = 3

    }

    private var mEnabled: Boolean = true
    private var isShadowColorDefined: Boolean = false
    var Dx: Int = 35
    var Dy: Int = 35
    private var mElevation: Int = DEFAULT_ELEVATION

    private var mTextColorNormal: Int =
        ColorUtil.getTextColorFromBackgroundColor(ab_bg_color)

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

        ahmetButton.ab_bg_color = ahmetButtonBuilder.ab_bg_color
        ahmetButton.ab_radius = ahmetButtonBuilder.ab_radius
        ahmetButton.ab_shadow_color = ahmetButtonBuilder.ab_shadow_color
        ahmetButton.ab_shadow_height = ahmetButtonBuilder.ab_shadow_height
        ahmetButton.ab_ripple_color = ahmetButtonBuilder.ab_rippleColor
        ahmetButton.mTextColorNormal = ahmetButtonBuilder.ab_txt_color
        ahmetButton.setupButton()

    }

    private fun initAttributes(mAttrs: AttributeSet?) {

        val typedArray =
            context.obtainStyledAttributes(mAttrs, R.styleable.AhmetButton)

        ab_bg_color = typedArray.getColor(
            R.styleable.AhmetButton_ab_backgroundColorNormal,
            DEFAULT_BACKGROUND_COLOR
        )

        ab_radius = typedArray.getDimension(
            R.styleable.AhmetButton_ab_radius,
            DEFAULT_RADIUS.toFloat()
        ).toInt()
        mTextColorNormal = typedArray.getColor(
            R.styleable.AhmetButton_ab_textColorNormal,
            ColorUtil.getTextColorFromBackgroundColor(ab_bg_color)
        )


        mEnabled = typedArray.getBoolean(R.styleable.AhmetButton_ab_enabled, DEFAULT_BUTTON_ENABLED)
        mEnabled = typedArray.getBoolean(R.styleable.AhmetButton_android_enabled, mEnabled)

        isShadowColorDefined =
            typedArray.getBoolean(R.styleable.AhmetButton_ab_shadowColorBrightness, false)


        mRippleAttributes.isUseRippleEffect = typedArray.getBoolean(
            R.styleable.AhmetButton_ab_useRippleEffect,
            DEFAULT_USE_RIPPLE_EFFECT
        )
        ab_ripple_color =
            typedArray.getColor(R.styleable.AhmetButton_ab_rippleColor, DEFAULT_RIPPLE_COLOR)
        mRippleAttributes.rippleOpacity =
            typedArray.getFloat(R.styleable.AhmetButton_ab_rippleOpacity, DEFAULT_RIPPLE_OPACITY)


        ab_shadow_color =
            typedArray.getColor(R.styleable.AhmetButton_ab_shadowColor, DEFAULT_BUTTON_SHADOW_COLOR)


        ab_shadow_height = typedArray.getDimension(
            R.styleable.AhmetButton_ab_shadowHeight, DEFAULT_BUTTON_SHADOW_HEIGHT.toFloat()
        ).toInt()

        mShadowAttributes.ab_shadow_enabled = typedArray.getBoolean(
            R.styleable.AhmetButton_ab_shadowEnabled, DEFAULT_BUTTON_SHADOW_ENABLED
        )


        typedArray.recycle()
        setupButton()


    }

    fun setButtonBackground(drawable: Drawable) {

        this.background = drawable

    }


    private fun setupButton() {
        setButtonTextColor()

        this.isEnabled = mEnabled
        this.setPadding(DimensionUtil.dipToPx(16f), 0, DimensionUtil.dipToPx(16f), 0)

        stateListAnimator = null
        elevation = mElevation.toFloat()

        val alpha = Color.alpha(ab_bg_color)
        val hsv = FloatArray(3)
        Color.colorToHSV(ab_bg_color, hsv)
        hsv[2] *= 0.8f
        
        if (isShadowColorDefined) {
            ab_shadow_color = Color.HSVToColor(alpha, hsv)
        }

        if (mEnabled && mRippleAttributes.isUseRippleEffect || mShadowAttributes.ab_shadow_enabled) {
            Log.d("TAG", "if enabled")

            mDrawable = createDrawable(
                ab_radius, ab_bg_color,
                ab_shadow_color
            )

            setButtonBackground(
                getRippleDrawable(
                    mRippleAttributes, mDrawable as LayerDrawable
                )
            )


        } else {
            Log.d("TAG", "else enabled")
            mDrawable = createDrawable(
                ab_radius, ab_bg_color,
                Color.TRANSPARENT
            )
            setButtonBackground(getButtonBackgroundStateList())
        }

        this.setPadding(
            0,
            Dx + ab_shadow_height,
            0,
            Dy + ab_shadow_height
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

        val cornerRadius = ab_radius.toFloat()

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

        drawable.setColor(ab_bg_color)


        return drawable

    }


    //layerDrawable: LayerDrawable
    fun getRippleDrawable(

        rippleAttributes: RippleAttributes,
        layerDrawable: LayerDrawable
    ): Drawable {

        val drawableNormal = getBackgroundDrawable()
        var mask = GradientDrawable()

        if (drawableNormal.constantState != null) {
            // Clone the GradientDrawable default and sets the color to white and maintains round corners (if any).
            // This fixes problems with transparent color and rounded corners.
            mask = drawableNormal.constantState!!.newDrawable() as GradientDrawable
            mask.setColor(Color.WHITE)
        }

        return RippleDrawable(
            ColorUtil.getRippleColorFromColor(
                ab_ripple_color,
                rippleAttributes.rippleOpacity
            ), layerDrawable, mask
        )

    }


    fun createDrawable(
        rad: Int,
        topColor: Int,
        bottomColor: Int
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

        layerDrawable.setLayerInset(1, 0, 0, 0, ab_shadow_height)

        return layerDrawable


    }





//
//
//
//
//    fun setBtnShadowColor(color: Int) : AhmetButton{
//        mShadowAttributes.ab_shadow_color = color
//        setupButton()
//        return this
//    }
//
//
//    fun setBtnShadowHeight(height : Int){
//        mShadowAttributes.ab_shadow_height = height
//        setupButton()
//    }
//
//
//    fun setBtnShadowEnabled(enabled : Boolean){
//        mShadowAttributes.ab_shadow_enabled = enabled
//        setupButton()
//    }
//
//
//    fun setBtnRadius(radius: Int) : AhmetButton{
//        if (radius >= 0) {
//            mAhmetButtonBuilder.ab_radius = radius
//            //DrawableAttributesBuilder(ab_radius = DimensionUtil.dipToPx(radius.toFloat())).build()
//            //mDrawableAttributes = DrawableAttributes.Builder(this.context).ab_radius(radius).build()
//            setupButton()
//        }
//        return this
//
//    }
//
//
//    fun setRippleColor(color: Int){
//        mRippleAttributes.rippleColor = color
//        setupButton()
//    }
//
//
//    fun setRippleOpacity(opacity : Int){
//        mRippleAttributes.rippleOpacity = opacity.toFloat()
//        setupButton()
//    }
//
//
//    fun setRippleEffect(enabled: Boolean){
//        mRippleAttributes.isUseRippleEffect = enabled
//        setupButton()
//    }
//
//    fun getBtnRadius() : Int {
//        return mAhmetButtonBuilder.ab_radius
//        //return DrawableAttributesBuilder().getAbRadius().toString()
//    }
//
//    fun getRippleEffectEnabled() : Boolean {
//        return mRippleAttributes.isUseRippleEffect
//    }
//
//    fun getShadowEnabled(): Boolean {
//        return mShadowAttributes.ab_shadow_enabled
//    }
//
//    fun getBtnTextColorNormal(): Int {
//        return mTextColorNormal
//    }
//
//    fun getBtnBackgroundColorNormal(): Int {
//        return mAhmetButtonBuilder.ab_bg_color
//
//        //return DrawableAttributes.Builder().ab_bg_color
//    }
//
//    fun getBtnShadowColor(): Int {
//        return mShadowAttributes.ab_shadow_color
//    }
//
//    fun getBtnShadowHeight(): Int {
//        return mShadowAttributes.ab_shadow_height
//    }


}