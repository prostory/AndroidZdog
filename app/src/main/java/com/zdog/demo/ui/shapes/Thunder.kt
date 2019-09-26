package com.zdog.demo.ui.shapes

import com.zdog.demo.ui.shapes.Colors.gold1
import com.zdog.demo.ui.shapes.Colors.gold2
import com.zdog.library.render.Anchor
import com.zdog.library.render.Group
import com.zdog.library.render.line
import com.zdog.library.render.move
import com.zdog.library.render.shape

class Thunder : Group() {
    init {
        let {
            shape {
                path(
                    move(-20f, 0f),
                    line(-20f, 20f),
                    line(0f, 20f),
                    line(0f, 40f),
                    line(30f, 0f)
                )
                color = gold1.colour
                stroke = 0f
                fill = true
                addTo = it
            }
            shape {
                path(
                    move(-20f, -6f),
                    line(34.5f, -6f),
                    line(25.5f, 6f),
                    line(-20f, 6f)
                )
                color = gold2.colour
                stroke = 0f
                fill = true
                addTo = it
            }
        }
    }

    override fun copy(): Thunder {
        return copy(Thunder())
    }

    override fun copy(shape: Anchor): Thunder {
        return super.copy(shape) as Thunder
    }
}