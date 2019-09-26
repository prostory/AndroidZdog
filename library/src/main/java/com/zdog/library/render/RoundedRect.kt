package com.zdog.library.render

class RoundedRect : Shape() {
    var width = 1f
    var height = 1f
    var cornerRadius = 0.25f

    override fun setPath() {
        val xA = width / 2
        val yA = height / 2
        val shortSide = Math.min(xA, yA)
        val cornerRadius = Math.min(cornerRadius, shortSide)
        val xB = xA - cornerRadius
        val yB = yA - cornerRadius
        path(
            move(x = xB, y = -yA),
            arc(
                vector(x = xA, y = -yA),
                vector(x = xA, y = -yB))
        ).apply {
            if (yB != 0f) {
                add(line(x = xA, y = yB))
            }
            add(arc(
                vector(x = xA, y = yA),
                vector(x = xB, y = yA)))
            if (xB != 0f) {
                add(line(x = -xB, y = yA))
            }
            add(arc(
                vector(x = -xA, y = yA),
                vector(x = -xA, y = yB)))
            if (yB != 0f) {
                add(line(x = -xA, y = -yB))
            }
            add(arc(
                vector(x = -xA, y = -yA),
                vector(x = -xB, y = -yA)))
            if (xB != 0f) {
                add(line(x = xB, y = -yA))
            }
        }
    }

    override fun copy(): RoundedRect {
        return copy(RoundedRect())
    }

    override fun copy(shape: Anchor): RoundedRect {
        return (super.copy(shape) as RoundedRect).also {
            it.width = width
            it.height = height
            it.cornerRadius = cornerRadius
        }
    }
}