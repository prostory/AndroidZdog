package com.zdog.demo.ui.effects.rain

import com.zdog.demo.ui.shapes.*
import com.zdog.library.render.*

class Smoke : Combine() {
    init {
        let {
            val dot = ellipse {
                addTo = it
                diameter = 18f
                stroke = 0f
                fill = true
            }

            dot.copy {}
            dot.copy {}
            dot.copy {}
            dot.copy {}
        }
    }

    fun run(world: World) {
        children.forEachIndexed { index, dot ->
            dot.alpha = 0f
            dot.animate {
                update(world) { t ->
                    dot.scale(1.0f - t)
                    dot.translate(-t * 30, -magnitudeSqrt(t * 5))
                    dot.alpha = 1.0f - t
                }
            }.delay(index * 1000L).duration(4000).repeat().start()
        }
    }
}