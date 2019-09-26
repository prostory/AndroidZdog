package com.zdog.demo.ui.shapes

import com.zdog.library.render.Anchor
import com.zdog.library.render.Shape
import com.zdog.library.render.TAU
import com.zdog.library.render.arc
import com.zdog.library.render.cos
import com.zdog.library.render.move
import com.zdog.library.render.sin
import com.zdog.library.render.tan
import com.zdog.library.render.vector

class Moon : Shape() {
    var radius = 1f

    init {
        stroke = 0f
        fill = true
    }

    override fun setPath() {
        val degree = TAU / 12
        val sin = degree.sin
        val cos = degree.cos
        val control = (1/sin-1)*degree.tan
        path(
            move(radius*sin, -radius*cos),
            arc(
                vector(radius, -radius*control),
                vector(radius, 0f)
            ),
            arc(
                vector(radius, radius),
                vector(0f, radius)
            ),
            arc(
                vector(-radius*control, radius),
                vector(-radius*cos, radius*sin)
            ),
            arc(
                vector(radius*sin, radius*sin),
                vector(radius*sin, -radius*cos)
            )
        )
    }

    override fun copy(): Moon {
        return copy(Moon())
    }

    override fun copy(shape: Anchor): Moon {
        return (super.copy(shape) as Moon).also {
            it.radius = radius
        }
    }
}