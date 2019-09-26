package com.zdog.demo.ui.shapes

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.zdog.library.render.*

open class World: Drawable()  {
    val world = illustration {
        color.alpha = 0
    }
    private val resetCallbacks = mutableListOf<()-> Unit>()

    protected var animator: AnimatorSet? = null

    override fun draw(canvas: Canvas) {
        world.updateRenderGraph(canvas)
    }

    override fun onBoundsChange(bounds: Rect?) {
        bounds?.let {
            changeSize(it.width(), it.height())
        }
    }

    override fun setAlpha(alpha: Int) {
        world.alpha = alpha / 255f
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    private fun changeSize(width: Int, height: Int) {
        world.zoom = Math.min(width, height)/240f
        world.onResize(width.toFloat(), height.toFloat())
    }

    fun addChild(shape: Anchor) {
        world.addChild(shape)
    }

    fun removeChild(shape: Anchor) {
        shape.remove()
    }

    fun rotate(rotate: Boolean) {
        if (rotate) {
            world.animate {
                interpolator = FastOutSlowInInterpolator()
                update {
                    val theta = it * TAU
                    val delta = TAU * -3 / 64
                    world.rotate.y = (Math.sin(theta) * delta).toFloat()
                    world.rotate.x = ((Math.cos(theta) * -0.5 + 0.5) * delta).toFloat()
                }
            }.duration(1500).repeat().start()
        } else {
            world.getAnimator(Anchor.AnimatorType.Custom)?.repeatCount = 0
        }
    }

    fun animator(): AnimatorSet {
        animator = animator?: AnimatorSet()
        return animator!!
    }

    fun play(block: ValueAnimator.()-> Unit) =
        animator().play(block)

    fun play(anim: ValueAnimator) =
        animator().play(anim)

    fun cancel() {
        animator?.cancel()
        resetCallbacks.forEach {
            it.invoke()
            invalidateSelf()
        }
    }

    fun clearAnimator() {
        animator?.pause()
        animator = null
    }

    fun isRunning(): Boolean {
        return animator?.isRunning ?: false
    }

    fun onEnd(block: ()-> Unit) {
        animator?.addListener(object: AnimatorListenerAdapter() {
            override fun onAnimationCancel(animation: Animator?) {
                block()
            }

            override fun onAnimationEnd(animation: Animator?) {
                block()
            }
        })
    }

    fun onReset(block: ()-> Unit) {
        resetCallbacks.add(block)
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

    fun AnimatorSet.play(block: ValueAnimator.()-> Unit): AnimatorSet.Builder =
        this.play(ValueAnimator.ofFloat(0f, 1f).apply(block))

    fun AnimatorSet.Builder.with(block: ValueAnimator.()-> Unit): AnimatorSet.Builder =
        this.with(ValueAnimator.ofFloat(0f, 1f).apply(block))

    fun AnimatorSet.Builder.before(block: ValueAnimator.()-> Unit): AnimatorSet.Builder =
        this.before(ValueAnimator.ofFloat(0f, 1f).apply(block))

    fun AnimatorSet.Builder.after(block: ValueAnimator.()-> Unit): AnimatorSet.Builder =
        this.after(ValueAnimator.ofFloat(0f, 1f).apply(block))

    fun ValueAnimator.update(block: (Float)-> Unit) {
        update(this@World, block)
    }

    fun Anchor.translateTo(x: Float = translate.x, y: Float = translate.y, z: Float = translate.z,
        block: (ValueAnimator.() -> Unit)? = null) =
        translateTo(this@World, x, y, z, block)

    fun Anchor.translateBy(x: Float = 0f, y: Float = 0f, z: Float = 0f,
        block: (ValueAnimator.() -> Unit)? = null) =
        translateBy(this@World, x, y, z, block)

    fun Anchor.translateBy(delta: Vector, block: (ValueAnimator.() -> Unit)? = null) =
        translateBy(this@World, delta, block)

    fun Anchor.rotateTo(x: Float = rotate.x, y: Float = rotate.y, z: Float = rotate.z,
        block: (ValueAnimator.() -> Unit)? = null) =
        rotateTo(this@World, x, y, z, block)

    fun Anchor.rotateBy(x: Float = 0f, y: Float = 0f, z: Float = 0f,
        block: (ValueAnimator.() -> Unit)? = null) =
        rotateBy(this@World, x, y, z, block)

    fun Anchor.rotateBy(delta: Vector, block: (ValueAnimator.() -> Unit)? = null) =
        rotateBy(this@World, delta, block)

    fun Anchor.scaleTo(dest: Float, block: (ValueAnimator.() -> Unit)? = null) =
        scaleTo(this@World, dest, block)

    fun Anchor.alphaTo(dest: Float, block: (ValueAnimator.() -> Unit)? = null) =
        alphaTo(this@World, dest, block)

    fun Anchor.colorTo(dest: Colour, block: (ValueAnimator.() -> Unit)? = null) =
        colorTo(this@World, dest, block)
}