package com.zdog.demo.ui.shapes

import com.zdog.library.render.Anchor
import com.zdog.library.render.Combine
import com.zdog.library.render.Shape
import com.zdog.library.render.arc
import com.zdog.library.render.copy
import com.zdog.library.render.cos
import com.zdog.library.render.line
import com.zdog.library.render.move
import com.zdog.library.render.shape
import com.zdog.library.render.sin
import com.zdog.library.render.vector

class Cloud : Combine() {
    init {
        let {
            shape {
                stroke = 80f
                fill = false
                translate { x=40f; y = -35f }
                addTo = it
            }

            shape {
                val degree = Math.asin(15/35.toDouble())
                val sin = degree.sin
                val cos = degree.cos
                path(
                    move(100f, 25f),
                    arc(
                        vector(135f, 25f),
                        vector(135f, -10f)
                    ),
                    arc(
                        vector(135f, -45f),
                        vector(100f, -45f)
                    ),
                    arc(
                        vector(100f-35*cos+20*sin, -45f),
                        vector(100f-35*cos, -25f)
                    ),
                    line(-5f, -25f),
                    arc(
                        vector(-30f, -25f),
                        vector(-30f, 0f)
                    ),
                    arc(
                        vector(-30f, 25f),
                        vector(-5f, 25f)
                    ),
                    line(100f, 25f)
                )
                stroke = 0f
                fill = true
                addTo = it
            }
        }
    }

    override fun copy(): Cloud {
        return copy(Cloud())
    }

    override fun copy(shape: Anchor): Cloud {
        return super.copy(shape) as Cloud
    }

    fun shader(length: Float = 3f, dx: Float = 0f, dy: Float = 0f, block: Combine.()-> Unit): Combine {
        val clone = copy(block)
        clone.children.forEach {
            it.apply {
                if (this is Shape) {
                    stroke += length*2
                    translate { x += dx; y += dy }
                    fill = false
                }
            }
        }
        return clone
    }
}