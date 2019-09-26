package com.zdog.demo.ui.shapes

import com.zdog.library.render.Anchor
import com.zdog.library.render.Combine
import com.zdog.library.render.Shape
import com.zdog.library.render.Vector
import com.zdog.library.render.arc
import com.zdog.library.render.line
import com.zdog.library.render.move
import com.zdog.library.render.shape
import com.zdog.library.render.vector

class Wind : Combine() {
    init {
        let {
            shape {
                path(
                    move(x=-80f),
                    line(x=55f),
                    arc(
                        vector(80f, 0f),
                        vector(80f, 25f)
                    ),
                    arc(
                        vector(80f, 50f),
                        vector(55f, 50f)
                    ),
                    arc(
                        vector(25f, 50f),
                        vector(25f, 25f)
                    )
                )
                addTo = it
                stroke = 8f
                closed = false
            }

            shape {
                path(
                    move(x=-60f, y=8f),
                    line(x=30f, y=8f)
                )
                addTo = it
                stroke = 4f
            }

            shape {
                path(
                    move(x=55f, y=8f),
                    arc(
                        vector(72f, 8f),
                        vector(72f, 25f)
                    ),
                    arc(
                        vector(72f, 42f),
                        vector(55f, 42f)
                    ),
                    arc(
                        vector(38f, 42f),
                        vector(38f, 25f)
                    )
                )
                addTo = it
                stroke = 4f
                closed = false
            }

            shape {
                path(
                    move(x=-70f, y=-10f),
                    line(x=0f, y=-10f),
                    arc(
                        vector(25f, -10f),
                        vector(25f, -35f)
                    ),
                    arc(
                        vector(25f, -60f),
                        vector(0f, -60f)
                    ),
                    arc(
                        vector(-25f, -60f),
                        vector(-25f, -35f)
                    )
                )
                addTo = it
                stroke = 8f
                closed = false
            }

            shape {
                path(
                    move(x=-50f, y=-18f),
                    line(x=-25f, y=-18f)
                )
                addTo = it
                stroke = 4f
            }

            shape {
                path(
                    move(x=0f, y=-18f),
                    arc(
                        vector(17f, -18f),
                        vector(17f, -35f)
                    ),
                    arc(
                        vector(17f, -52f),
                        vector(0f, -52f)
                    ),
                    arc(
                        vector(-17f, -52f),
                        vector(-17f, -35f)
                    )
                )
                addTo = it
                stroke = 4f
                closed = false
            }

            shape {
                path(
                    move(x=20f, y = -8f),
                    line(x=45f, y = -8f)
                )
                addTo = it
                stroke = 4f
            }
        }
    }

    override fun scale(scale: Float): Vector {
        children.forEachIndexed { _, shape ->
            (shape as Shape).stroke *= scale
        }
        return super.scale(scale)
    }

    override fun copy(): Wind {
        return copy(Wind())
    }

    override fun copy(shape: Anchor): Wind {
        return super.copy(shape) as Wind
    }
}