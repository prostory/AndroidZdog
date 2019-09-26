package com.zdog.demo.ui.shapes

import com.zdog.demo.ui.shapes.Colors.rain
import com.zdog.demo.ui.status.Status
import com.zdog.library.render.Anchor
import com.zdog.library.render.Shape

class Sleet(diameter: Float = 12f): Shape(), Dropable {
    init {
        stroke = diameter
        color = rain.colour
        fill = true

        onCreate()
    }

    override fun drop(target: Status, offset: Float, acceleration: Float) =
        target.float(this, 0f, 90f, acceleration, false) {
            duration = 2000
            startDelay = (offset * duration).toLong()
        }

    override fun copy(): Sleet {
        return copy(Sleet())
    }

    override fun copy(shape: Anchor): Sleet {
        return super.copy(shape) as Sleet
    }
}