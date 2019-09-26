package com.zdog.demo.ui.basic

import com.zdog.demo.ui.shapes.World
import com.zdog.demo.ui.shapes.duration
import com.zdog.demo.ui.shapes.repeat
import com.zdog.library.render.*

class Shapes {
    val shapes = Array(15) {
        World()
    }

    init {
        var index = 0
        shape {
            addTo = shapes[index++].world
            path(
                line(x = -40f),
                line(x = 40f)
            )
            stroke = 20f
            color = "#636".toColour()
        }

        shape {
            addTo = shapes[index++].world
            path(
                line(x = -32f, y = -40f),
                line(x = 32f, y = -40f),
                line(x = -32f, y = 40f),
                line(x = 32f, y = 40f)
            )
            closed = false
            stroke = 20f
            color = "#636".toColour()
        }

        shape {
            addTo = shapes[index++].world
            path(
                line(x = -32f, y = -40f, z = 40f),
                line(x = 32f, y = -40f),
                line(x = 32f, y = 40f, z = 40f),
                line(x = 32f, y = 40f, z = -40f)
            )
            closed = false
            stroke = 20f
            color = "#636".toColour()
        }

        shape {
            addTo = shapes[index++].world
            path(
                line(x = -60f, y = -60f),
                arc(
                    vector(x = 20f, y = -60f),
                    vector(x = 20f, y = 20f)
                ),
                arc(
                    vector(x = 20f, y = 60f),
                    vector(x = 60f, y = 60f)
                )
            )
            closed = false
            stroke = 20f
            color = "#636".toColour()
        }

        shape {
            addTo = shapes[index++].world
            path(
                line(x = -60f, y = -60f),
                bezier(
                    vector(x = 20f, y = -60f),
                    vector(x = 20f, y = 60f),
                    vector(x = 60f, y = 60f)
                )
            )
            closed = false
            stroke = 20f
            color = "#636".toColour()
        }

        val triangle = shape {
            addTo = shapes[index++].world
            path(
                line(x = 0f, y = -40f),
                line(x = 40f, y = 40f),
                line(x = -40f, y = 40f)
            )
            stroke = 20f
            color = "#636".toColour()
        }

        triangle.copy {
            addTo = shapes[index++].world
            closed = false
        }

        hemisphere {
            addTo = shapes[index++].world
            diameter = 120f
            stroke = 0f
            color = "#C25".toColour()
            backface = "#EA0".toColour()
        }

        cone {
            addTo = shapes[index++].world
            diameter = 70f
            length = 90f
            stroke = 0f
            color = "#636".toColour()
            backface = "#C25".toColour()
        }

        cylinder {
            addTo = shapes[index++].world
            diameter = 80f
            length = 120f
            stroke = 0f
            color = "#C25".toColour()
            backface = "#E62".toColour()
        }

        cylinder {
            addTo = shapes[index++].world
            diameter = 80f
            length = 120f
            stroke = 0f
            color = "#C25".toColour()
            frontFace = "#EA0".toColour()
            backface = "#636".toColour()
        }

        val box = box {
            addTo = shapes[index++].world
            width = 120f
            height = 100f
            depth = 80f
            stroke = 0f
            color = "#C25".toColour()
            leftFace = "#EA0".toColour()
            rightFace = "#E62".toColour()
            topFace = "#ED0".toColour()
            bottomFace = "#636".toColour()
        }

        box.copy {
            addTo = shapes[index++].world
            leftFace = null
            rightFace = null
            rearFace = "#EA0".toColour()
        }

        val distance = 40f

        shapes[index].world.rotate(x = -(TAU / 16).toFloat())
        val dot = shape {
            addTo = shapes[index++].world
            translate(y = -distance)
            stroke = 80f
            color = "#636".toColour()
        }
        dot.copy {
            translate(x = -distance)
            color = "#EA0".toColour()
        }
        dot.copy {
            translate(z = distance)
            color = "#C25".toColour()
        }
        dot.copy {
            translate(x = distance)
            color = "#E62".toColour()
        }
        dot.copy {
            translate(z = -distance)
            color = "#C25".toColour()
        }
        dot.copy {
            translate(y = distance)
        }

        shapes[index].world.rotate(x = -(TAU / 16).toFloat())
        val dot2 = dot.copy {
            addTo = shapes[index++].world
        }
        dot2.copy {
            translate(x = -distance)
        }
        dot2.copy {
            translate(z = distance)
        }
        dot2.copy {
            translate(x = distance)
        }
        dot2.copy {
            translate(z = -distance)
        }
        dot2.copy {
            translate(y = distance)
        }

        shapes.forEach {
            it.apply {
                play(
                    world.rotateTo(y = TAU.toFloat()).duration(3000).repeat()
                )
            }
        }
    }
}