package com.zdog.demo.ui.effects.sunny


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import com.zdog.demo.ui.shapes.*
import com.zdog.library.render.*
import kotlin.random.Random

class RandomMover(
    private val shape: Anchor,
    private val bound: Vector,
    private val speed: Float = 0.1f,
    private val minTick: Int = 30,
    private val maxTick: Int = 50
) {
    private val velocity = vector()
    private var boundX: Pair<Float, Float>? = null
    private var boundY: Pair<Float, Float>? = null
    private var boundZ: Pair<Float, Float>? = null

    init {
        if (bound.x > 0) {
            boundX = Pair(shape.translate.x - bound.x, shape.translate.x + bound.x)
        } else if (bound.x < 0) {
            boundX = Pair(bound.x, -bound.x)
        }
        if (bound.y > 0) {
            boundY = Pair(shape.translate.y - bound.y, shape.translate.y + bound.y)
        } else if (bound.y < 0) {
            boundY = Pair(bound.y, -bound.y)
        }
        if (bound.z > 0) {
            boundZ = Pair(shape.translate.z - bound.z, shape.translate.z + bound.z)
        } else if (bound.z < 0) {
            boundZ = Pair(bound.z, -bound.z)
        }
    }

    fun animate(world: World) = shape.animate {
        val src = shape.translate.copy()

        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                reset()
            }

            override fun onAnimationRepeat(animation: Animator?) {
                reset()
            }

            fun reset() {
                boundX?.let {
                    when {
                        shape.translate.x <= it.first -> velocity.x = speed
                        shape.translate.x >= it.second -> velocity.x = -speed
                        else -> velocity.x = randomSpeed()
                    }
                }

                boundY?.let {
                    when {
                        shape.translate.y <= it.first -> velocity.y = speed
                        shape.translate.y >= it.second -> velocity.y = -speed
                        else -> velocity.y = randomSpeed()
                    }
                }

                boundZ?.let {
                    when {
                        shape.translate.z <= it.first -> velocity.z = speed
                        shape.translate.z >= it.second -> velocity.z = -speed
                        else -> velocity.z = randomSpeed()
                    }
                }
                duration(Random.nextInt(minTick, maxTick) * 20L)
            }
        })

        onReset { shape.translate.set(src) }

        update(world) {
            shape.translate.add(velocity)
        }
    }.toReverse()

    private fun randomSpeed(): Float {
        return if (Random.nextBoolean()) speed else -speed
    }
}