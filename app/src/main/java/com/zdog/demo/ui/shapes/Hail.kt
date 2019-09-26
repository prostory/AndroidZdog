package com.zdog.demo.ui.shapes

import com.zdog.demo.ui.shapes.Colors.white
import com.zdog.demo.ui.status.Status
import com.zdog.library.render.Anchor
import com.zdog.library.render.Rect
import com.zdog.library.render.TAU

class Hail(diameter: Float = 12f): Rect(), Dropable {
    init {
        width = diameter
        height = diameter
        color = white.colour
        stroke = 0f
        fill = true

        onCreate()
    }

    override fun drop(target: Status, offset: Float, acceleration: Float) =
        target.float(this, 0f, 90f, acceleration) {
            duration = 2000
            startDelay = (offset * duration).toLong()
        }

    override fun toStatic() {
        rotate { z = (TAU/8).toFloat() }
    }

    override fun copy(): Hail {
        return copy(Hail())
    }

    override fun copy(shape: Anchor): Hail {
        return super.copy(shape) as Hail
    }
}