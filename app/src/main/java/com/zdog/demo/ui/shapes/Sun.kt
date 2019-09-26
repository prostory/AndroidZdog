package com.zdog.demo.ui.shapes

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.animation.OvershootInterpolator
import com.zdog.demo.ui.shapes.Colors.gold1
import com.zdog.demo.ui.shapes.Colors.gold2
import com.zdog.library.render.Anchor
import com.zdog.library.render.Combine
import com.zdog.library.render.TAU
import com.zdog.library.render.combine
import com.zdog.library.render.copy
import com.zdog.library.render.rect
import com.zdog.library.render.shape

class Sun(diameter: Float) : Anchor() {
    private val sunshine: Combine

    init {
        let {
            shape {
                stroke = diameter
                color = gold1.colour
                addTo = it
            }

            sunshine = combine {
                translate { z = -4f }
                color = gold2.colour
                addTo = it
            }.also {
                rect {
                    width = diameter / 3
                    height = diameter / 3
                    stroke = 0f
                    fill = true
                    addTo = it
                }.copy {
                    rotate { z = (TAU /8).toFloat() }
                }
            }
        }
    }

    fun toDynamic(world: World): AnimatorSet.Builder {
        sunshine.scale(1f)
        return world.play(
            sunshine.rotateBy(world, z = TAU.toFloat()) {
                repeatCount = ValueAnimator.INFINITE
                duration = 10000
            }
        ).after(
            sunshine.scaleTo(world, 4f) {
                duration = 800
                interpolator = OvershootInterpolator()
            }
        )
    }

    fun toStatic(world: World) {
        sunshine.scale(4f)
    }
}