package com.zdog.demo.ui.shapes

import com.zdog.demo.ui.shapes.Colors.gold1
import com.zdog.demo.ui.shapes.Colors.gold2
import com.zdog.library.render.Anchor
import com.zdog.library.render.Group
import com.zdog.library.render.TAU
import com.zdog.library.render.arc
import com.zdog.library.render.cos
import com.zdog.library.render.line
import com.zdog.library.render.move
import com.zdog.library.render.shape
import com.zdog.library.render.sin
import com.zdog.library.render.tan
import com.zdog.library.render.vector

class Question : Group() {
    var radius = 15f

    init {
        val degree = TAU / 12
        val sin = degree.sin
        val cos = degree.cos
        val control = (1/cos-1)/degree.tan

        let {
            shape {
                path(
                    move(-radius*cos, radius*sin),
                    arc(
                        vector(-radius, radius*control),
                        vector(-radius, 0f)
                    ),
                    arc(
                        vector(-radius, -radius),
                        vector(0f, -radius)
                    ),
                    arc(
                        vector(radius, -radius),
                        vector(radius, 0f)
                    ),
                    arc(
                        vector(radius, radius*control),
                        vector(radius*cos, radius*sin)
                    ),
                    arc(
                        vector(0f, radius+5),
                        vector(0f, radius*2f)
                    )
                )
                stroke = 8f
                color = gold1.colour
                closed = false
                addTo = it
            }

            shape {
                path(
                    move(y=radius*2+14f),
                    line(y=radius*2+14f)
                )
                stroke = 12f
                color = gold2.colour
                addTo = it
            }
        }
    }

    override fun copy(): Question {
        return copy(Question())
    }

    override fun copy(shape: Anchor): Question {
        return (super.copy(shape) as Question).also {
            it.radius = radius
        }
    }
}