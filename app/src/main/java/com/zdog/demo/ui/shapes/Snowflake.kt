package com.zdog.demo.ui.shapes

import com.zdog.demo.ui.shapes.Colors.white
import com.zdog.demo.ui.status.Status
import com.zdog.library.render.Anchor
import com.zdog.library.render.Shape
import com.zdog.library.render.TAU
import com.zdog.library.render.cos
import com.zdog.library.render.line
import com.zdog.library.render.move
import com.zdog.library.render.sin

class Snowflake(private val diameter: Float = 8f) : Shape(),
    Dropable {
    init {
        val sin = (TAU /6).sin
        val cos = (TAU /6).cos
        path(
            move(0f, diameter),
            line(0f, -diameter),
            move(sin * -diameter, cos * -diameter),
            line(sin * diameter, cos * diameter),
            move(sin * diameter, cos * -diameter),
            line(sin * -diameter, cos * diameter)
        )

        stroke = 5f
        color = white.colour
        fill = false

        onCreate()
    }

    override fun drop(target: Status, offset: Float, acceleration: Float) =
        target.float(this, 20f, 100f, acceleration) {
            startDelay = (offset * duration).toLong()
        }

    override fun copy(): Snowflake {
        return copy(Snowflake(diameter))
    }

    override fun copy(shape: Anchor): Snowflake {
        return super.copy(shape) as Snowflake
    }
}