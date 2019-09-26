package com.zdog.demo.ui.shapes

import com.zdog.demo.ui.shapes.Colors.rain
import com.zdog.demo.ui.status.Status
import com.zdog.library.render.Anchor
import com.zdog.library.render.Shape
import com.zdog.library.render.arc
import com.zdog.library.render.line
import com.zdog.library.render.move
import com.zdog.library.render.vector

class Raindrop(private val diameter: Float = 8f): Shape(),
    Dropable {
    init {
        path(
            move(-diameter, 0f),
            line(0f, -diameter*2),
            line(diameter, 0f),
            arc(
                vector(diameter, diameter),
                vector(0f, diameter)
            ),
            arc(
                vector(-diameter, diameter),
                vector(-diameter, 0f)
            )
        )

        stroke = 0f
        fill = true
        color = rain.colour

        onCreate()
    }

    override fun drop(target: Status, offset: Float, acceleration: Float) =
        target.drop(this, 90f, acceleration) {
            startDelay = (offset * duration).toLong()
        }

    override fun copy(): Raindrop {
        return copy(Raindrop(diameter))
    }

    override fun copy(shape: Anchor): Raindrop {
        return (super.copy(shape) as Raindrop)
    }
}