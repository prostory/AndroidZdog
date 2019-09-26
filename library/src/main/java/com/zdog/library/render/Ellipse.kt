package com.zdog.library.render

open class Ellipse: Shape() {
    var diameter = 1f
    var width = 0f
    var height = 0f
    var quarters = 4

    init {
        closed = true
    }

    override fun setPath() {
        val x = if (width != 0f) width/2 else diameter/2
        val y = if (height != 0f) height/2 else diameter/2
        path(
            move(x = 0f, y = -y),
            arc(
                vector(x = x, y = -y),
                vector(x = x, y = 0f)
            )
        ).apply {
            if (quarters > 1) {
                add(arc(
                    vector(x =  x, y = y),
                    vector(x = 0f, y = y)
                ))
            }
            if (quarters > 2) {
                add(arc(
                    vector(x = -x, y = y),
                    vector(x = -x, y = 0f)
                ))
            }
            if (quarters > 3) {
                add(arc(
                    vector(x = -x, y = -y),
                    vector(x = 0f, y = -y)
                ))
            }
        }
    }

    override fun copy(): Ellipse {
        return copy(Ellipse())
    }

    override fun copy(shape: Anchor): Ellipse {
        return (super.copy(shape) as Ellipse).also {
            it.diameter = diameter
            it.width = width
            it.height = height
            it.quarters = quarters
        }
    }
}