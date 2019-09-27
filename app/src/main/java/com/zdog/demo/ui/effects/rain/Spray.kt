package com.zdog.demo.ui.effects.rain

import com.zdog.demo.ui.shapes.*
import com.zdog.library.render.*

class Spray : Combine() {
    init {
        let {
            val dot = ellipse {
                diameter = 24f
                addTo = it
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
            dot.scale(0.5f)
            dot.translate { x = 0f;y = 0f }
            dot.animate {
                update(world) { t ->
                    if (t <= 0.5f) {
                        dot.scale(0.5f + t)
                        dot.translate(-t * 20, -t * 10)
                    } else {
                        dot.scale(1.5f - t)
                        dot.translate(-t * 60 + 20, t * 10 - 10)
                    }
                }
            }.delay(index * 800L).duration(4000).repeat().start()
        }
    }
}