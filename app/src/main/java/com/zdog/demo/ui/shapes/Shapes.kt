package com.zdog.demo.ui.shapes

import com.zdog.library.render.Combine
import com.zdog.library.render.Group
import com.zdog.library.render.combine
import com.zdog.library.render.group
import com.zdog.library.render.line
import com.zdog.library.render.move
import com.zdog.library.render.shape

fun thunder(block: Group.()-> Unit): Group {
    return group {
        block.invoke(this)
    }.also {

    }
}

fun tornado(block: Combine.()-> Unit): Combine {
    return combine(block).also {
        shape {
            path(
                move(x = -4f),        // 0
                line(x = 4f)
            )
            stroke = 8f
            addTo = it
        }
        shape {
            path(
                move(x = -17f, y = -20f), // 1
                line(x = 17f, y = -20f)
            )
            stroke = 8f
            addTo = it
        }
        shape {
            path(
                move(x = -27f, y = -40f), // 11
                line(x = 27f, y = -40f)
            )
            stroke = 8f
            addTo = it
        }
        shape {
            path(
                move(x = -26f, y = -60f),  // 22
                line(x = 26f, y = -60f)
            )
            stroke = 8f
            addTo = it
        }
        shape {
            path(
                move(x = -34f, y = -80f), // 10
                line(x = 34f, y = -80f)
            )
            stroke = 8f
            addTo = it
        }
    }
}

