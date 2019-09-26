package com.zdog.library.render

open class Rect : Shape() {
    var width: Float = 1f
    var height: Float = 1f

    override fun setPath() {
        val x = width / 2
        val y = height / 2

        path(
            move(x = -x, y = -y),
            line(x = x, y = -y),
            line(x = x, y = y),
            line(x = -x, y = y)
        )
    }

    override fun copy(): Rect {
        return copy(Rect())
    }

    override fun copy(shape: Anchor): Rect {
        return (super.copy(shape) as Rect).also {
            it.width = width
            it.height = height
        }
    }
}