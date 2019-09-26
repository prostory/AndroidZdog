package com.zdog.demo.ui.effects.rain

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import com.zdog.demo.ui.shapes.*
import com.zdog.library.render.Combine
import com.zdog.library.render.Shape
import com.zdog.library.render.line
import com.zdog.library.render.set
import kotlin.random.Random

class Raindrops(count: Int,
    private val width: Pair<Float, Float>,
    private val height: Pair<Float, Float>,
    private val boundX: Pair<Float, Float>,
    private val boundY: Pair<Float, Float>): Combine() {
    private val Pair<Float, Float>.random
        get() = Random.nextFloat() * (second-first) + first


    init {
        let {
            (0 until count).forEach { _ ->
                Drop().set {
                    addTo = it
                }
            }
        }
    }

    fun restart(world: World) {
        children.forEachIndexed { index, drop ->
            if (drop is Drop) {
                drop.animate {
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            drop.resetState()
                            duration(((boundY.second-boundY.first) / drop.speed).toLong()*30)
                        }

                        override fun onAnimationRepeat(animation: Animator?) {
                            drop.resetState()
                            duration(((boundY.second-boundY.first) / drop.speed).toLong()*30)
                        }
                    })

                    onReset { drop.resetState() }

                    update(world) {
                        drop.translate.y += it * drop.speed
                        if (drop.translate.y < boundY.first - drop.h || drop.translate.y > boundY.second + drop.h) {
                            drop.alpha = 0f
                        }
                    }
                }.repeat().start()
            }
        }
    }

    private inner class Drop(
        val w: Float = width.random,
        val h: Float = height.random): Shape() {
        var speed = (2f to 4f).random
        init {
            path(
                line(y=0f),
                line(y=h)
            )
            stroke = w
        }

        fun resetState() {
            translate.x = boundX.random
            translate.y = boundY.first
            speed = (2f to 4f).random
        }
    }
}