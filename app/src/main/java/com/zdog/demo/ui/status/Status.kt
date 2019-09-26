package com.zdog.demo.ui.status

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.animation.LinearInterpolator
import com.zdog.library.render.Anchor
import com.zdog.library.render.TAU
import com.zdog.library.render.sin
import com.zdog.demo.ui.shapes.World

open class Status: World() {
    protected open fun switchDay(inDay: Boolean) {}
    protected open fun onStatic(inDay: Boolean) {}
    protected open fun onDynamic(inDay: Boolean) {}

    fun toDynamicNight() = apply {
        clearAnimator()
        switchDay(false)
        onDynamic(false)
    }

    fun toDynamicDay() = apply {
        clearAnimator()
        switchDay(true)
        onDynamic(true)
    }

    fun toStaticNight() = apply {
        clearAnimator()
        switchDay(false)
        onStatic(false)
    }

    fun toStaticDay() = apply {
        clearAnimator()
        switchDay(true)
        onStatic(true)
    }

    fun drawTo(bitmap: Bitmap): Bitmap {
        bitmap.eraseColor(Color.TRANSPARENT)
        setBounds(0, 0, bitmap.width, bitmap.height)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }

    fun drop(shape: Anchor, height: Float, acceleration: Float = 0f,
        toAlpha: Float = 0f, block: (ValueAnimator.() -> Unit)? = null): ValueAnimator {
        return with(shape) {
             ValueAnimator.ofFloat(0f, 1f).apply {
                repeatCount = ValueAnimator.INFINITE
                duration = 1500
                interpolator = LinearInterpolator()

                block?.invoke(this)

                val src = translate.copy()
                val fromAlpha = alpha
                onReset {
                    alpha = fromAlpha
                    translate.set(src)
                }

                update {
                    if (it <= 2/3f) {
                        val t = it * 3/2f
                        val speed = acceleration * t
                        rotate { z = -(speed / height * TAU/8).toFloat() }
                        alpha = fromAlpha
                        translate { x = src.x + speed * t
                            y = src.y + height * t }
                    } else {
                        alpha = fromAlpha - (it-2/3f)*3f + toAlpha
                    }
                }
            }
        }
    }

    fun float(shape: Anchor, width: Float, height: Float, acceleration: Float = 0f,
        rotate: Boolean = true, toAlpha: Float = 1f, block: (ValueAnimator.() -> Unit)? = null): ValueAnimator {
        return with(shape) {
            ValueAnimator.ofFloat(0f, 1f).apply {
                repeatCount = ValueAnimator.INFINITE
                duration = 3000

                block?.invoke(this)

                val src = translate.copy()
                val fromAlpha = alpha
                onReset {
                    alpha = fromAlpha
                    translate.set(src)
                }

                update {
                    val angle = (TAU * it)
                    if (rotate) {
                        rotate { z = angle.toFloat() }
                    }
                    val speed = if (acceleration != 0f)
                        acceleration * it
                    else
                        angle.sin * width
                    translate { x = src.x  + speed * it
                        y = src.y + height * it }
                    alpha = fromAlpha - it + toAlpha
                }
            }
        }
    }
}