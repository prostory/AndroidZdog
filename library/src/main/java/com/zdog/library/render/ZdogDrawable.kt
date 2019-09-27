package com.zdog.library.render

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable

open class ZdogDrawable : Drawable() {
    val illo = illustration()
    protected var animator: AnimatorSet? = null

    override fun draw(canvas: Canvas) {
        illo.updateRenderGraph(canvas)
    }

    override fun onBoundsChange(bounds: Rect?) {
        bounds?.let {
            changeSize(it.width(), it.height())
        }
    }

    override fun setAlpha(alpha: Int) {
        illo.alpha = alpha / 255f
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    private fun changeSize(width: Int, height: Int) {
        illo.zoom = Math.min(width, height) / 240f
        illo.onResize(width.toFloat(), height.toFloat())
    }

    fun addChild(shape: Anchor) {
        illo.addChild(shape)
    }

    fun removeChild(shape: Anchor) {
        shape.remove()
    }

    fun animator(): AnimatorSet {
        animator = animator ?: AnimatorSet()
        return animator!!
    }

    fun play(block: ValueAnimator.() -> Unit) =
        animator().play(block)

    fun play(anim: ValueAnimator) =
        animator().play(anim)

    fun cancel() {
        animator?.pause()
        animator?.cancel()
    }

    fun clearAnimator() {
        cancel()
        animator = null
    }

    fun isRunning(): Boolean {
        return animator?.isRunning ?: false
    }

    fun onEnd(block: () -> Unit) {
        animator?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                block()
            }
        })
    }

    fun onReset(block: () -> Unit) {
        animator?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                block()
            }

            override fun onAnimationCancel(animation: Animator?) {
                block()
            }
        })
    }

    fun start() {
        if (isRunning()) {
            return
        }
        animator?.start()
    }

    fun start(delay: Long = 0) {
        if (isRunning()) {
            return
        }
        animator?.apply {
            startDelay = delay
            start()
        }
    }

    fun resume() {
        animator?.apply {
            if (isPaused) {
                resume()
            }
        }
    }

    fun pause() {
        if (isRunning()) {
            animator?.pause()
        }
    }

    fun AnimatorSet.play(block: ValueAnimator.() -> Unit): AnimatorSet.Builder =
        this.play(ValueAnimator.ofFloat(0f, 1f).apply(block))

    fun AnimatorSet.Builder.with(block: ValueAnimator.() -> Unit): AnimatorSet.Builder =
        this.with(ValueAnimator.ofFloat(0f, 1f).apply(block))

    fun AnimatorSet.Builder.before(block: ValueAnimator.() -> Unit): AnimatorSet.Builder =
        this.before(ValueAnimator.ofFloat(0f, 1f).apply(block))

    fun AnimatorSet.Builder.after(block: ValueAnimator.() -> Unit): AnimatorSet.Builder =
        this.after(ValueAnimator.ofFloat(0f, 1f).apply(block))

    fun ValueAnimator.update(block: (Float) -> Unit) {
        update(this@ZdogDrawable, block)
    }

    fun Anchor.translateTo(
        x: Float = translate.x, y: Float = translate.y, z: Float = translate.z,
        block: (ValueAnimator.() -> Unit)? = null
    ) =
        translateTo(this@ZdogDrawable, x, y, z) {
            val _x = translate.x
            val _y = translate.y
            val _z = translate.z
            onReset {
                translate(_x, _y, _z)
            }
            block?.invoke(this)
        }

    fun Anchor.translateBy(
        x: Float = 0f, y: Float = 0f, z: Float = 0f,
        block: (ValueAnimator.() -> Unit)? = null
    ) =
        translateBy(this@ZdogDrawable, x, y, z) {
            val _x = translate.x
            val _y = translate.y
            val _z = translate.z
            onReset {
                translate(_x, _y, _z)
            }
            block?.invoke(this)
        }

    fun Anchor.translateBy(delta: Vector, block: (ValueAnimator.() -> Unit)? = null) =
        translateBy(this@ZdogDrawable, delta) {
            val _x = translate.x
            val _y = translate.y
            val _z = translate.z
            onReset {
                translate(_x, _y, _z)
            }
            block?.invoke(this)
        }

    fun Anchor.rotateTo(
        x: Float = rotate.x, y: Float = rotate.y, z: Float = rotate.z,
        block: (ValueAnimator.() -> Unit)? = null
    ) =
        rotateTo(this@ZdogDrawable, x, y, z) {
            val _x = rotate.x
            val _y = rotate.y
            val _z = rotate.z
            onReset {
                rotate(_x, _y, _z)
            }
            block?.invoke(this)
        }

    fun Anchor.rotateBy(
        x: Float = 0f, y: Float = 0f, z: Float = 0f,
        block: (ValueAnimator.() -> Unit)? = null
    ) =
        rotateBy(this@ZdogDrawable, x, y, z) {
            val _x = rotate.x
            val _y = rotate.y
            val _z = rotate.z
            onReset {
                rotate(_x, _y, _z)
            }
            block?.invoke(this)
        }

    fun Anchor.rotateBy(delta: Vector, block: (ValueAnimator.() -> Unit)? = null) =
        rotateBy(this@ZdogDrawable, delta) {
            val _x = rotate.x
            val _y = rotate.y
            val _z = rotate.z
            onReset {
                rotate(_x, _y, _z)
            }
            block?.invoke(this)
        }

    fun Anchor.scaleTo(dest: Float, block: (ValueAnimator.() -> Unit)? = null) =
        scaleTo(this@ZdogDrawable, dest) {
            val _x = scale.x
            val _y = scale.y
            val _z = scale.z
            onReset {
                scale { x = _x;y = _y;z = _z }
            }
            block?.invoke(this)
        }

    fun Anchor.alphaTo(dest: Float, block: (ValueAnimator.() -> Unit)? = null) =
        alphaTo(this@ZdogDrawable, dest) {
            val _alpha = alpha
            onReset {
                alpha = _alpha
            }
            block?.invoke(this)
        }

    fun Anchor.colorTo(dest: Colour, block: (ValueAnimator.() -> Unit)? = null) =
        colorTo(this@ZdogDrawable, dest)  {
            val _color = color.get()
            onReset {
                color.set(_color)
            }
            block?.invoke(this)
        }
}