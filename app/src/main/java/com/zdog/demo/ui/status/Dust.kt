package com.zdog.demo.ui.status

import android.view.animation.LinearInterpolator
import com.zdog.demo.ui.shapes.Colors.dust
import com.zdog.library.render.Combine
import com.zdog.library.render.TAU
import com.zdog.library.render.combine
import com.zdog.library.render.copy
import com.zdog.library.render.line
import com.zdog.library.render.move
import com.zdog.library.render.shape
import com.zdog.library.render.sin

class Dust: Scattered() {
    private val dusts: Combine
    init {
        shape {
            path(move(x=-80f), line(x=80f))
            translate { y = 100f }
            stroke = 16f
            fill = false
            color = "#ddd"
            addTo = illo
        }

        dusts = combine {
            translate { y = 100f; z = -8f }
            addTo = illo
            color = dust.color
        }.also {
            val dots = shape {
                path(
                    move(x=-60f), line(x=-60f),
                    move(x=-30f), line(x=-30f),
                    move(x=0f), line(x=-0f),
                    move(x=30f), line(x=30f),
                    move(x=60f), line(x=60f)
                )
                addTo = it
                stroke = 12f
                fill = false
            }
            dots.copy {}
            dots.copy {}
            dots.copy {}
        }
    }

    override fun onDynamic(inDay: Boolean) {
        super.onDynamic(inDay)
        dusts.children.forEachIndexed {index, dots->
            dots.translate { x = 0f }
            play(float(dots, 10f, -100f, 0f, false, 0f) {
                duration = 4000
                interpolator = LinearInterpolator()
                startDelay = 1000L * index
            })
        }
    }

    override fun onStatic(inDay: Boolean) {
        super.onStatic(inDay)
        dusts.children.forEachIndexed {index, dots->
            with(dots) {
                val factor = index.toFloat() / dusts.children.size
                val angle = (TAU * factor)
                translate { x = angle.sin * 10
                    y = -100f * factor }
                alpha = 1 - factor
            }
        }
    }
}