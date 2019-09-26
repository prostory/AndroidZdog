package com.zdog.library.render

class Polygon: Shape() {
    var sides = 3
    var radius = 0.5f

    override fun setPath() {
        path.clear()
        (0 until sides).forEach {
            val theta = it / sides.toFloat() * TAU - TAU/4
            val x = Math.cos(theta) * radius
            val y = Math.sin(theta) * radius
            path.add(line(x = x.toFloat(), y = y.toFloat()))
        }
    }

    override fun copy(): Polygon {
        return copy(Polygon())
    }

    override fun copy(shape: Anchor): Polygon {
        return (super.copy(shape) as Polygon).also {
            it.sides = sides
            it.radius = radius
        }
    }
}