package com.zdog.library.render

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.view.animation.LinearInterpolator

fun ValueAnimator.update(d: Drawable, block: (Float)-> Unit) {
    addUpdateListener {
        block.invoke(it.animatedFraction)
        d.invalidateSelf()
    }
}

fun ValueAnimator.duration(t: Long): ValueAnimator = apply {
    duration = t
}

fun ValueAnimator.delay(t: Long): ValueAnimator = apply {
    startDelay = t
}

fun ValueAnimator.repeat(): ValueAnimator = apply {
    repeatCount = ValueAnimator.INFINITE
    repeatMode = ValueAnimator.RESTART
}

fun ValueAnimator.toReverse(): ValueAnimator = apply {
    repeatCount = ValueAnimator.INFINITE
    repeatMode = ValueAnimator.REVERSE
}

fun ValueAnimator.interpolator(interpolator: TimeInterpolator) = apply {
    this.interpolator = interpolator
}

fun ValueAnimator.onEnd(block: () -> Unit) = apply {
    addListener(object: AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            block()
        }
    })
}

fun ValueAnimator.onCancel(block: () -> Unit) = apply {
    addListener(object: AnimatorListenerAdapter() {
        override fun onAnimationCancel(animation: Animator?) {
            block()
        }
    })
}

fun ValueAnimator.onReset(block: () -> Unit) = apply {
    addListener(object: AnimatorListenerAdapter() {
        override fun onAnimationCancel(animation: Animator?) {
            block()
        }

        override fun onAnimationEnd(animation: Animator?) {
            block()
        }
    })
}

fun Anchor.animate(block: (ValueAnimator.() -> Unit)? = null): ValueAnimator {
    return ValueAnimator.ofFloat(0f, 1f).also {
        it.interpolator = LinearInterpolator()
        it.duration = 300

        block?.invoke(it)

        addAnimator(Anchor.AnimatorType.Custom, it)
    }
}

fun Anchor.translateTo(d: Drawable, x: Float = translate.x, y: Float = translate.y, z: Float = translate.z,
    block: (ValueAnimator.() -> Unit)? = null) =
    translateTo(d, vector(x, y, z), block)

fun Anchor.translateTo(d: Drawable, vector: Vector,
                       block: (ValueAnimator.() -> Unit)? = null) =
    translateBy(d, vector.subtract(this.translate), block)

fun Anchor.translateBy(d: Drawable, x: Float = 0f, y: Float = 0f, z: Float = 0f,
    block: (ValueAnimator.() -> Unit)? = null) =
    translateBy(d, vector(x, y, z), block)

fun Anchor.translateBy(d: Drawable, delta: Vector, block: (ValueAnimator.() -> Unit)? = null): ValueAnimator {
    val x = translate.x
    val y = translate.y
    val z = translate.z

    return ValueAnimator.ofFloat(0f, 1f).also {
        it.interpolator = LinearInterpolator()
        it.duration = 300

        block?.invoke(it)

        it.update(d) {
            translate.set(delta).multiply(it).add(x, y, z)
        }

        addAnimator(Anchor.AnimatorType.Translate, it)
    }
}

fun Anchor.rotateTo(d: Drawable, x: Float = rotate.x, y: Float = rotate.y, z: Float = rotate.z,
    block: (ValueAnimator.() -> Unit)? = null) =
    rotateBy(d, vector(x, y, z).subtract(this.rotate), block)

fun Anchor.rotateBy(d: Drawable, x: Float = 0f, y: Float = 0f, z: Float = 0f,
    block: (ValueAnimator.() -> Unit)? = null) =
    rotateBy(d, vector(x, y, z), block)

fun Anchor.rotateBy(d: Drawable, delta: Vector, block: (ValueAnimator.() -> Unit)? = null): ValueAnimator {
    val x = rotate.x
    val y = rotate.y
    val z = rotate.z

    return ValueAnimator.ofFloat(0f, 1f).also {
        it.interpolator = LinearInterpolator()
        it.duration = 300

        block?.invoke(it)

        it.update(d) {
            rotate.set(delta).multiply(it).add(x, y, z)
        }

        addAnimator(Anchor.AnimatorType.Rotate, it)
    }
}

fun Anchor.scaleTo(d: Drawable, dest: Float, block: (ValueAnimator.() -> Unit)? = null): ValueAnimator {
    val x = scale.x
    val y = scale.y
    val z = scale.z

    val delta = vector(dest).subtract(scale)

    return ValueAnimator.ofFloat(0f, 1f).also {
        it.interpolator = LinearInterpolator()
        it.duration = 300

        block?.invoke(it)

        it.update(d) {
            scale.set(delta).multiply(it).add(x, y, z)
        }

        addAnimator(Anchor.AnimatorType.Scale, it)
    }
}

fun Anchor.alphaTo(d: Drawable, dest: Float, block: (ValueAnimator.() -> Unit)? = null): ValueAnimator {
    val alpha = alpha
    val delta = dest - alpha

    return ValueAnimator.ofFloat(0f, 1f).also {
        it.interpolator = LinearInterpolator()
        it.duration = 300

        block?.invoke(it)

        it.update(d) {
            this.alpha = alpha + delta * it
        }

        addAnimator(Anchor.AnimatorType.Alpha, it)
    }
}

fun Anchor.colorTo(d: Drawable, dest: Colour, block: (ValueAnimator.() -> Unit)? = null): ValueAnimator {
    val c = arrayOf(
            color.alpha, color.red, color.green, color.blue
    )
    val delta = arrayOf(
            dest.alpha - c[0],
            dest.red - c[1],
            dest.green - c[2],
            dest.blue - c[3]
    )

    fun compute(delta: Int, from: Int, factor: Float) =
            (delta * factor + from).toInt()

    return ValueAnimator.ofFloat(0f, 1f).also {
        it.interpolator = LinearInterpolator()
        it.duration = 300

        block?.invoke(it)

        it.update(d) {
            color.set(
                    compute(delta[0], c[0], it),
                    compute(delta[1], c[1], it),
                    compute(delta[2], c[2], it),
                    compute(delta[3], c[3], it))
        }

        addAnimator(Anchor.AnimatorType.Color, it)
    }
}