package com.zdog.demo.ui.effects

import com.zdog.library.render.*

fun tree(block: (Shape.()->Unit), width: Float, height: Float, translate: Vector) {
    val treeW = width / 2
    val treeH = height / 2

    val pointA = vector(x = 0f, y = -treeH)
    val pointB = vector(x = treeW, y = treeH)
    val pointC = vector(x = -treeW, y = treeH)

    val treePlane = shape {
        path(
            line(pointA),
            bezier(
                pointA,
                vector(x = treeW, y = treeH*1/3),
                pointB
            ),
            line(pointC),
            bezier(
                vector(x = -treeW, y = treeH*1/3),
                pointA,
                pointA
            )
        )
        fill = true
        this.translate.set(translate)
        block.invoke(this)
    }
    treePlane.copy {
        rotate(y = (TAU /4).toFloat())
    }
}

fun grass(block: Shape.() -> Unit): Shape {
    return shape {
        path(
            line(x = 0f, y = 1f),
            arc(
                vector(x = -1f, y = 1f),
                vector(x = -1f, y = 0f)
            ),
            arc(
                vector(x = -1f, y = -1f),
                vector(x = 0f, y = -1f)
            ),
            arc(
                vector(x = -0.5f, y = -0.7f),
                vector(x = -0.5f, y = 0f)
            ),
            arc(
                vector(x = -0.5f, y = 0.7f),
                vector(x = 0f, y = 1f)
            )
        )
        scale(8f)
        rotate(z = 0.6f)
        stroke = 1f
        fill = true
        closed = false
        block.invoke(this)
    }
}

fun bird(block: Shape.() -> Unit): Shape {
    return shape {
        path(
            line(x = -6f, y = -4f),
            line(x = -4f, y = -4f),
            arc(
                vector(x = 0f, y = -4f),
                vector(x = 0f, y = 0f)
            ),
            arc(
                vector(x = 0f, y = -4f),
                vector(x = 4f, y = -4f)
            ),
            line(x = 6f, y = -4f),
            move(z = -2f, y = 0f),
            line(z = 3f, y = 0f)
        )
        stroke = 3f
        closed = false
        block.invoke(this)
    }
}

fun star(block: Combine.() -> Unit): Combine {
    return combine(block).also {
        shape {
            path(
                line(x = 0f, y = -4f),
                arc(
                    vector(x = 0f, y = 0f),
                    vector(x = 4f, y = 0f)
                ),
                arc(
                    vector(x = 0f, y = 0f),
                    vector(x = 4f, y = 0f)
                ),
                arc(
                    vector(x = 0f, y = 0f),
                    vector(x = -4f, y = 0f)
                ),
                arc(
                    vector(x = 0f, y = 0f),
                    vector(x = 0f, y = -4f)
                )
            )
            addTo = it
            stroke = 2f
            fill = true
        }.copy {
            rotate { y = (TAU /4).toFloat() }
        }
    }
}

fun cloud(block: Combine.() -> Unit): Combine {
    return combine(block).also {
        val dot = shape {
            addTo = it
            translate(x = -36f, y = 18f)
            stroke = 24f
        }
        dot.copy {
            translate(-24f, 24f)
        }
        dot.copy {
            translate(-6f, 26f)
        }
        dot.copy {
            translate(12f, 16f)
        }
        dot.copy {
            translate(28f, 12f)
        }
        dot.copy {
            translate(48f, 20f)
        }

        val bigDot = dot.copy {
            stroke = 48f
            translate(x = -52f, y = 40f)
        }
        bigDot.copy {
            translate(x = 20f, y = 40f)
        }
        bigDot.copy {
            stroke = 40f
            translate(x = 56f, y = 40f)
        }
        bigDot.copy {
            stroke = 40f
            translate(x = -16f, y = 48f)
        }
    }
}

fun cloud1(block: Shape.() -> Unit): Shape {
    return shape {
        path(
            line(x = -20f, y = 0f),
            bezier(
                vector(x = -13f, y = 0f),
                vector(x = -12f, y = -4f),
                vector(x = -10f, y = -4f)
            ),
            bezier(
                vector(x = -8f, y = -4f),
                vector(x = -8f, y = -2f),
                vector(x = -4f, y = -2f)
            ),
            bezier(
                vector(x = 0f, y = -2f),
                vector(x = 1f, y = -6f),
                vector(x = 4f, y = -6f)
            ),
            bezier(
                vector(x = 7f, y = -6f),
                vector(x = 6f, y = 0f),
                vector(x = 20f, y = 0f)
            )
        )
        rotate(y = (-TAU *1/16).toFloat())
        scale {x = (1/Math.cos(TAU *1/16).toFloat())}
        stroke = 4f
        fill = true
        block.invoke(this)
    }
}

fun cloud2(block: Shape.() -> Unit): Shape {
    return shape {
        path(
            line(x = -32f, y = 0f),
            line(x = -28f, y = 0f),
            bezier(
                vector(x = -22f, y = 0f),
                vector(x = -20f, y = -6f),
                vector(x = -16f, y = -6f)
            ),
            bezier(
                vector(x = -12f, y = -6f),
                vector(x = -12f, y = -2f),
                vector(x = -8f, y = -2f)
            ),
            bezier(
                vector(x = -4f, y = -2f),
                vector(x = -4f, y = -6f),
                vector(x = 0f, y = -6f)
            ),
            bezier(
                vector(x = -4f, y = -2f),
                vector(x = -4f, y = -6f),
                vector(x = 0f, y = -6f)
            ),
            bezier(
                vector(x = 4f, y = -6f),
                vector(x = 4f, y = -2f),
                vector(x = 8f, y = -2f)
            ),
            bezier(
                vector(x = 12f, y = -2f),
                vector(x = 12f, y = -6f),
                vector(x = 16f, y = -6f)
            ),
            line(x = 32f, y = 0f)
        )
        rotate { y = (TAU * 1/16).toFloat() }
        scale { x = (1/Math.cos(TAU * -1/16)).toFloat() }
        stroke = 4f
        fill = true
        block.invoke(this)
    }
}