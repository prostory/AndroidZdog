package com.zdog.demo.ui.shapes

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import com.zdog.library.render.Anchor
import com.zdog.library.render.Colour
import com.zdog.library.render.Vector
import com.zdog.library.render.vector

fun ValueAnimator.update(world: World, block: (Float)-> Unit) {
    addUpdateListener {
        block.invoke(it.animatedFraction)
        world.invalidateSelf()
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

fun Anchor.translateTo(world: World, x: Float = translate.x, y: Float = translate.y, z: Float = translate.z,
    block: (ValueAnimator.() -> Unit)? = null) =
    translateTo(world, vector(x, y, z), block)

fun Anchor.translateTo(world: World, vector: Vector,
                       block: (ValueAnimator.() -> Unit)? = null) =
    translateBy(world, vector.subtract(this.translate), block)

fun Anchor.translateBy(world: World, x: Float = 0f, y: Float = 0f, z: Float = 0f,
    block: (ValueAnimator.() -> Unit)? = null) =
    translateBy(world, vector(x, y, z), block)

fun Anchor.translateBy(world: World, delta: Vector, block: (ValueAnimator.() -> Unit)? = null): ValueAnimator {
    val src = translate.copy()

    world.onReset {
        translate.set(src)
    }

    return ValueAnimator.ofFloat(0f, 1f).also {
        it.interpolator = LinearInterpolator()
        it.duration = 300

        block?.invoke(it)

        it.update(world) {
            translate.set(delta).multiply(it).add(src)
        }

        addAnimator(Anchor.AnimatorType.Translate, it)
    }
}

fun Anchor.rotateTo(world: World, x: Float = rotate.x, y: Float = rotate.y, z: Float = rotate.z,
    block: (ValueAnimator.() -> Unit)? = null) =
    rotateBy(world, vector(x, y, z).subtract(this.rotate), block)

fun Anchor.rotateBy(world: World, x: Float = 0f, y: Float = 0f, z: Float = 0f,
    block: (ValueAnimator.() -> Unit)? = null) =
    rotateBy(world, vector(x, y, z), block)

fun Anchor.rotateBy(world: World, delta: Vector, block: (ValueAnimator.() -> Unit)? = null): ValueAnimator {
    val src = rotate.copy()

    world.onReset {
        rotate.set(src)
    }

    return ValueAnimator.ofFloat(0f, 1f).also {
        it.interpolator = LinearInterpolator()
        it.duration = 300

        block?.invoke(it)

        it.update(world) {
            rotate.set(delta).multiply(it).add(src)
        }

        addAnimator(Anchor.AnimatorType.Rotate, it)
    }
}

fun Anchor.scaleTo(world: World, dest: Float, block: (ValueAnimator.() -> Unit)? = null): ValueAnimator {
    val src = scale.copy()
    val delta = vector(dest).subtract(src)

    world.onReset {
        scale.set(src)
    }

    return ValueAnimator.ofFloat(0f, 1f).also {
        it.interpolator = LinearInterpolator()
        it.duration = 300

        block?.invoke(it)

        it.update(world) {
            scale.set(delta).multiply(it).add(src)
        }

        addAnimator(Anchor.AnimatorType.Scale, it)
    }
}

fun Anchor.alphaTo(world: World, dest: Float, block: (ValueAnimator.() -> Unit)? = null): ValueAnimator {
    val src = alpha
    val delta = dest - src

    world.onReset {
        alpha = src
    }

    return ValueAnimator.ofFloat(0f, 1f).also {
        it.interpolator = LinearInterpolator()
        it.duration = 300

        block?.invoke(it)

        it.update(world) {
            alpha = src + delta * it
        }

        addAnimator(Anchor.AnimatorType.Alpha, it)
    }
}

fun Anchor.colorTo(world: World, dest: Colour, block: (ValueAnimator.() -> Unit)? = null): ValueAnimator {
    val c = arrayOf(
            color.alpha, color.red, color.green, color.blue
    )
    val delta = arrayOf(
            dest.alpha - c[0],
            dest.red - c[1],
            dest.green - c[2],
            dest.blue - c[3]
    )

    world.onReset {
        color.set(c[0], c[1], c[2], c[3])
    }

    fun compute(delta: Int, from: Int, factor: Float) =
            (delta * factor + from).toInt()

    return ValueAnimator.ofFloat(0f, 1f).also {
        it.interpolator = LinearInterpolator()
        it.duration = 300

        block?.invoke(it)

        it.update(world) {
            color.set(
                    compute(delta[0], c[0], it),
                    compute(delta[1], c[1], it),
                    compute(delta[2], c[2], it),
                    compute(delta[3], c[3], it))
        }

        addAnimator(Anchor.AnimatorType.Color, it)
    }
}