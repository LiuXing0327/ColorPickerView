package com.liuxing.library

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ComposeShader
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * Author：流星
 * DateTime：2025/7/14 23:52
 * Description：选择颜色视图
 */
class ColorPickerView @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null
) : View(ctx, attrs) {

    private var hue = 0f
    private var sat = 1f
    private var value = 1f

    private val colorSquarePaint = Paint()
    private val hueBarPaint = Paint()
    private val thumbPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 4f * resources.displayMetrics.density
        color = Color.WHITE
    }
    private val indicatorPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        strokeWidth = 2f * resources.displayMetrics.density
    }

    // SV 方块
    private lateinit var squareRect: RectF
    // Hue 滑条
    private lateinit var hueBarRect: RectF

    var onColorChanged: ((Int) -> Unit)? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // 保留 30dp 作为底部的 Hue 滑条
        val barHeight = 30f * resources.displayMetrics.density

        // SV 区域在顶部
        squareRect = RectF(
            paddingLeft.toFloat(),
            paddingTop.toFloat(),
            (w - paddingRight).toFloat(),
            (h - paddingBottom - barHeight - 10f)
        )

        // Hue 滑条在底部
        hueBarRect = RectF(
            paddingLeft.toFloat(),
            squareRect.bottom + 10f,
            (w - paddingRight).toFloat(),
            squareRect.bottom + 10f + barHeight
        )

        buildHueBarShader()
        buildSquareShader()
    }

    /**
     * 构建 SV 方块颜色渐变
     */
    private fun buildSquareShader() {
        val hueColor = Color.HSVToColor(floatArrayOf(hue, 1f, 1f))

        val satShader = LinearGradient(
            squareRect.left, squareRect.top,
            squareRect.right, squareRect.top,
            Color.WHITE, hueColor,
            Shader.TileMode.CLAMP
        )

        val valShader = LinearGradient(
            squareRect.left, squareRect.top,
            squareRect.left, squareRect.bottom,
            Color.WHITE, Color.BLACK,
            Shader.TileMode.CLAMP
        )

        colorSquarePaint.shader = ComposeShader(satShader, valShader, PorterDuff.Mode.MULTIPLY)
    }

    /**
     * 构建 Hue 滑条颜色渐变
     */
    private fun buildHueBarShader() {
        val hueColors = IntArray(7) { i ->
            Color.HSVToColor(floatArrayOf(i * 60f, 1f, 1f))
        }

        hueBarPaint.shader = LinearGradient(
            hueBarRect.left, hueBarRect.top,
            hueBarRect.right, hueBarRect.top,
            hueColors, null,
            Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas) {
        // 绘制 SV 方块
        canvas.drawRect(squareRect, colorSquarePaint)

        // 绘制 Hue 滑条
        canvas.drawRect(hueBarRect, hueBarPaint)

        // 绘制 SV 选中的指示圆点
        val px = squareRect.left + sat * squareRect.width()
        val py = squareRect.top + (1f - value) * squareRect.height()
        canvas.drawCircle(px, py, 14f, thumbPaint)

        // 绘制 Hue 选中的指示线
        val hx = hueBarRect.left + (hue / 360f) * hueBarRect.width()
        canvas.drawLine(hx, hueBarRect.top, hx, hueBarRect.bottom, indicatorPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {

                if (squareRect.contains(x, y)) {
                    sat = ((x - squareRect.left) / squareRect.width()).coerceIn(0f, 1f)
                    value = (1f - (y - squareRect.top) / squareRect.height()).coerceIn(0f, 1f)
                    notifyColorChanged()
                    invalidate()
                    return true
                }

                if (hueBarRect.contains(x, y)) {
                    hue = 360f * ((x - hueBarRect.left) / hueBarRect.width()).coerceIn(0f, 1f)
                    buildSquareShader()
                    notifyColorChanged()
                    invalidate()
                    return true
                }
            }
        }

        return super.onTouchEvent(event)
    }

    /**
     * 通知颜色变化
     */
    private fun notifyColorChanged() {
        val argb = Color.HSVToColor(floatArrayOf(hue, sat, value))
        Log.d("ColorPickerView", "Selected Color: #${Integer.toHexString(argb).uppercase()}")
        onColorChanged?.invoke(argb)
    }
}