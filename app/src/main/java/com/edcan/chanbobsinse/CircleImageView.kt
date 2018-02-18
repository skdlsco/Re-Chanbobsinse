package com.edcan.chanbobsinse

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by eka on 2018. 2. 16..
 */
class CircleImageView : ImageView {

    private val mDrawableRect = RectF()
    private val mBorderRect = RectF()

    private val mShaderMatrix = Matrix()
    private val mBitmapPaint = Paint()
    private val mBorderPaint = Paint()

    private var mBorderColor = Color.BLACK
    private var mBorderWidth = 0

    private var mBitmap: Bitmap? = null
    private var mBitmapShader: BitmapShader? = null
    private var mBitmapWidth: Int = 0
    private var mBitmapHeight: Int = 0

    private var mDrawableRadius: Float = 0.toFloat()
    private var mBorderRadius: Float = 0.toFloat()

    private var mReady: Boolean = false
    private var mSetupPending: Boolean = false

    val imageScaleType: ImageView.ScaleType
        get() = SCALE_TYPE

    var borderColor: Int
        get() = mBorderColor
        set(borderColor) {
            if (borderColor == mBorderColor) {
                return
            }

            mBorderColor = borderColor
            mBorderPaint.color = mBorderColor
            invalidate()
        }

    var borderWidth: Int
        get() = mBorderWidth
        set(borderWidth) {
            if (borderWidth == mBorderWidth) {
                return
            }

            mBorderWidth = borderWidth
            setup()
        }

    constructor(context: Context) : super(context) {

        init()
    }

    @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(context, attrs, defStyle) {

        context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0).let {
            mBorderWidth = it.getDimensionPixelSize(R.styleable.CircleImageView_circleBorderWidth, 0)
            mBorderColor = it.getColor(R.styleable.CircleImageView_circleBorderColor, Color.BLACK)
            it.recycle()
        }

        init()
    }

    private fun init() {
        super.setScaleType(SCALE_TYPE)
        mReady = true

        if (mSetupPending) {
            setup()
            mSetupPending = false
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (drawable == null) {
            return
        }

        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), mDrawableRadius, mBitmapPaint)
        if (mBorderWidth != 0) {
            canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), mBorderRadius, mBorderPaint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup()
    }

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        mBitmap = bm
        setup()
    }

    override fun setImageDrawable(drawable: Drawable) {
        super.setImageDrawable(drawable)
        mBitmap = getBitmapFromDrawable(drawable)
        setup()
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        mBitmap = getBitmapFromDrawable(drawable)
        setup()
    }

    override fun setImageURI(uri: Uri) {
        super.setImageURI(uri)
        mBitmap = getBitmapFromDrawable(drawable)
        setup()
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap: Bitmap = if (drawable is ColorDrawable) {
            Bitmap.createBitmap(COLOR_DRAWABLE_DIMENSION, COLOR_DRAWABLE_DIMENSION, BITMAP_CONFIG)
        } else {
            Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, BITMAP_CONFIG)
        }

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private fun setup() {
        if (!mReady) {
            mSetupPending = true
            return
        }

        if (mBitmap == null) {
            return
        }

        mBitmapShader = BitmapShader(mBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        mBitmapPaint.run {
            isAntiAlias = true
            shader = mBitmapShader
        }

        mBorderPaint.run {
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = mBorderColor
            strokeMiter = mBorderWidth.toFloat()
        }

        mBitmapHeight = mBitmap!!.height
        mBitmapWidth = mBitmap!!.width

        mBorderRect.set(0f, 0f, width.toFloat(), height.toFloat())
        mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2, (mBorderRect.width() - mBorderWidth) / 2)

        mDrawableRect.set(mBorderWidth.toFloat(), mBorderWidth.toFloat(), mBorderRect.width() - mBorderWidth, mBorderRect.height() - mBorderWidth)
        mDrawableRadius = Math.min(mDrawableRect.height() / 2, mDrawableRect.width() / 2)

        updateShaderMatrix()
        invalidate()
    }

    private fun updateShaderMatrix() {
        val scale: Float
        var dx = 0f
        var dy = 0f

        mShaderMatrix.set(null)

        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / mBitmapHeight.toFloat()
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f
        } else {
            scale = mDrawableRect.width() / mBitmapWidth.toFloat()
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f
        }

        mShaderMatrix.setScale(scale, scale)
        mShaderMatrix.postTranslate(((dx + 0.5f).toInt() + mBorderWidth).toFloat(), ((dy + 0.5f).toInt() + mBorderWidth).toFloat())

        mBitmapShader!!.setLocalMatrix(mShaderMatrix)
    }

    companion object {
        private var SCALE_TYPE = ImageView.ScaleType.CENTER_CROP
        private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888
        private const val COLOR_DRAWABLE_DIMENSION = 2
    }

}