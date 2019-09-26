package com.zdog.library.render

import android.graphics.Color

const val TAU = Math.PI * 2
const val arcHandleLength = 9/16f

val Float.angle
    get() = ((this / Math.PI) * 180).toFloat()

val Double.sin
    get() = Math.sin(this).toFloat()

val Double.cos
    get() = Math.cos(this).toFloat()

val Double.tan
    get() = Math.tan(this).toFloat()

fun lerp(a: Float, b: Float, alpha: Float): Float {
    return (b - a) * alpha + a
}

fun modulo(num: Float, div: Float): Float {
    return ((num % div) + div) % div
}

fun magnitudeSqrt(sum: Float): Float {
    if (Math.abs(sum-1) < 0.00000001) {
        return 1f
    }
    return Math.sqrt(sum.toDouble()).toFloat()
}

private val powerMultipliers = arrayOf(
    {a: Float-> a * a},
    {a: Float-> a * a * a},
    {a: Float-> a * a * a * a},
    {a: Float-> a * a * a * a * a}
)

fun powerMultipliers(power: Int) = powerMultipliers[power-2]

fun easeInOut(alpha: Float, power: Int = 2): Float {
    if (power == 1) return alpha
    val alpha = Math.max(0f, Math.min(1f, alpha))
    val isFirstHalf = alpha < 0.5f
    var slope = if (isFirstHalf) alpha else 1 - alpha
    slope /= 0.5f
    val powerMultiplier = if (power < 2 || power > 5)
        powerMultipliers(2)
    else
        powerMultipliers(power)
    var curve = powerMultiplier(slope)
    curve /= 2f
    return if (isFirstHalf) curve else 1 - curve
}

val String.color: Int
    get() {
        if (this[0] != '#') {
            throw IllegalArgumentException("Can't parse $this to color")
        }
        when(length) {
            4 -> {
                val r = this[1]
                val g = this[2]
                val b = this[3]
                return Color.parseColor("#FF$r$r$g$g$b$b")
            }
            5 -> {
                val a = this[1]
                val r = this[2]
                val g = this[3]
                val b = this[4]
                return Color.parseColor("#$a$a$r$r$g$g$b$b")
            }
            7 -> {
                return Color.parseColor("#FF${substring(1)}")
            }
            9 -> {
                return Color.parseColor(this)
            }
            else->
                throw IllegalArgumentException("Can't parse $this to color")
        }
    }

fun Float.bound(min: Float, max: Float): Float =
        Math.min(max, Math.max(min, this))

fun Int.bound(min: Int, max: Int): Int =
    Math.min(max, Math.max(min, this))

fun colorPart(c: Int, shift: Int) =
    (c and (0xFF shl shift)) shr shift

fun colorTo(from: Int, to: Int, factor: Float): Int {
    val factor = Math.min(1f, Math.max(factor, 0f))
    val a1 = (from and (0xFF shl 24)) shr 24
    val r1 = (from and (0xFF shl 16)) shr 16
    val g1 = (from and (0xFF shl 8)) shr 8
    val b1 = (from and 0xFF)
    val a2 = (to and (0xFF shl 24)) shr 24
    val r2 = (to and (0xFF shl 16)) shr 16
    val g2 = (to and (0xFF shl 8)) shr 8
    val b2 = (to and 0xFF)
    val a = (if (a1 > a2) a1-(a1-a2)*factor else (a2-a1)*factor+a1).toInt()
    val r = (if (r1 > r2) r1-(r1-r2)*factor else (r2-r1)*factor+r1).toInt()
    val g = (if (g1 > g2) g1-(g1-g2)*factor else (g2-g1)*factor+g1).toInt()
    val b = (if (b1 > b2) b1-(b1-b2)*factor else (b2-b1)*factor+b1).toInt()

    return (a shl 24) + (r shl 16) + (g shl 8) + b
}

fun Int.toColor(): String {
    return String.format("#%X", this)
}

fun String.toColour(): Colour {
    return Colour(this)
}