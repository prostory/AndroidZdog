package com.zdog.demo.ui.status

import android.animation.ValueAnimator.INFINITE
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.zdog.demo.ui.shapes.Raindrop
import com.zdog.demo.ui.shapes.Thunder
import kotlin.random.Random

class Thunderstorms : Drop(
    Raindrop(),
    Thunder(),
    Raindrop()
) {
    init {
        drops.translate { x = 0f; }
        drops.children[0].translate { x = -50f }
        drops.children[1].translate { x = -5f; y = -30f }
        drops.children[2].translate { x = 50f }
    }

    override fun onDynamic(inDay: Boolean) {
        super.onDynamic(inDay)
        play {
            repeatCount = INFINITE
            duration = 1500
            interpolator = FastOutSlowInInterpolator()
            startDelay = 1000
            var shakeDegree = 0f
            val degree = 4f
            var oldFactor = 0

            val thunder = drops.children[1]

            val src1 = cloud.translate.copy()
            val src2 = thunder.translate.copy()

            onReset {
                cloud.translate.set(src1)
                thunder.translate.set(src2)
            }

            update {
                if (it <= 0.5f) {
                    cloud.translate.set(src1)
                    thunder.alpha = 1f
                    thunder.translate { x = src2.x; y = src2.y + 40f * it * 2 }
                } else if (it <= 0.9f) {
                    val factor = (it * 20).toInt()
                    if (factor != oldFactor) {
                        if (shakeDegree > 0) {
                            shakeDegree = -degree
                        } else if (shakeDegree < 0) {
                            shakeDegree = degree
                        } else {
                            val isRightDir = Random.nextBoolean()
                            if (isRightDir) {
                                shakeDegree = degree
                            } else {
                                shakeDegree = -degree
                            }
                        }
                        thunder.translate { x += shakeDegree }
                        cloud.translate { x += shakeDegree }
                        oldFactor = factor
                    }
                } else {
                    thunder.alpha = 1 - (it - 0.9f) * 10
                }
            }
        }
    }
}