package com.zdog.demo.ui.effects.rain

import com.zdog.demo.ui.shapes.World
import com.zdog.library.render.colorTo
import com.zdog.library.render.delay
import com.zdog.library.render.duration
import com.zdog.library.render.*

class Ship : Group() {
    private var bodyScale: Float = 1f
    private val day = arrayOf(
        "#e5e9ec".toColour(),
        "#dadada".toColour(),
        "#f5f5f5".toColour(),
        "#808080".toColour(),
        "#f5f5f5".toColour(),
        "#dadada".toColour(),
        "#72a4c4".toColour(),
        "#a5d4e4".toColour(),
        "#e3001b".toColour(),
        "#ffffff".toColour(),
        "#808080".toColour(),
        "#ffffff".toColour(),
        "#8a9295".toColour(),
        "#adb1b3".toColour()
    )
    private val night = arrayOf(
        "#423356".toColour(),
        "#423356".toColour(),
        "#423456".toColour(),
        "#3F3053".toColour(),
        "#423456".toColour(),
        "#423456".toColour(),
        "#FA3".toColour(),
        "#FA6".toColour(),
        "#422C50".toColour(),
        "#433457".toColour(),
        "#3F3053".toColour(),
        "#433457".toColour(),
        "#3F3053".toColour(),
        "#403154".toColour()
    )

    init {
        let {
            shape {
                path(
                    move(0f, 0f),
                    line(115f, 0f),
                    line(120f, -5f),
                    line(0f, -5f)
                )
                addTo = it
                stroke = 0f
                fill = true
            }
            shape {
                path(
                    move(0f, -5f),
                    line(15f, -30f),
                    line(105f, -30f),
                    line(120f, -5f),

                    move(15f, -30f),
                    line(19.8f, -38f),
                    line(45.8f, -38f),
                    line(50.6f, -30f),

                    move(65f, -30f),
                    line(74.6f, -46f),
                    line(96.4f, -46f),
                    line(101.2f,-38f),
                    line(79.8f, -38f),
                    line(75f, -30f)
                )
                addTo = it
                stroke = 0f
                fill = true
            }
            shape {
                path(
                    move(24f, -38f),
                    line(24f, -48f),
                    line(39f, -48f),
                    line(39f, -38f)
                )
                addTo = it
                stroke = 0f
                fill = true
            }
            shape {
                path(
                    move(24f, -42f),
                    line(24f, -46f),
                    line(39f, -46f),
                    line(39f, -42f)
                )
                addTo = it
                stroke = 0f
                fill = true
            }
            shape {
                path(
                    move(77f, -47f),
                    line(93f, -47f),

                    move(87f, -48f),
                    line(87f, -50f)
                )
                addTo = it
                stroke = 2f
                fill = true
            }
            shape {
                path(
                    move(0f, -8.8f),
                    line(0f, -9.2f),
                    line(-15f, -9.2f),
                    line(-15f, -8.8f),

                    move(0f, 0f),
                    arc(
                        vector(9f, -9f),
                        vector(0f, -18f)
                    )
                )
                translate { x = 78f; y=-48f }
                rotate { z = (TAU/6).toFloat() }
                addTo = it
                stroke = 0f
                fill = true
            }
            shape {
                path(
                    move(19f, -30f),
                    line(20.2f, -32f),
                    line(45.4f,-32f),
                    line(46.6f,-30f),

                    move(15.4f, -24f),
                    line(16.6f, -26f),
                    line(103.4f, -26f),
                    line(104.6f, -24f),

                    move(12.4f, -19f),
                    line(13.6f, -21f),
                    line(106.4f, -21f),
                    line(107.6f, -19f)
                )
                addTo = it
                stroke = 0f
                fill = true
            }
            shape {
                path(
                    line(75f, -30f),
                    line(79.8f, -38f),
                    line(99.2f,-38f),
                    line(104f,-30f)
                )
                addTo = it
                stroke = 0f
                fill = true
            }
            shape {
                path(
                    line(0f, 0f),
                    line(115f, 0f),
                    line(114f, 1f),
                    line(0f, 1f)
                )
                addTo = it
                stroke = 0f
                fill = true
            }
            shape {
                path(
                    line(1f, 1f),
                    line(114f, 1f),
                    line(106f, 7f),
                    line(7f, 7f)
                )
                addTo = it
                stroke = 0f
                fill = true
            }
            shape {
                path(
                    move(7f, 7f),
                    line(106f, 7f),
                    line(97f, 14f),
                    line(16f, 14f)
                )
                addTo = it
                stroke = 0f
                fill = true
            }
            shape {
                path(
                    move(0f, -5f),
                    line(0f, -15f),
                    line(30f, -15f),
                    line(40f, -5f),

                    move(70f, -5f),
                    line(80f, -15f),
                    line(130f, -15f),
                    line(120f, -5f)
                )
                addTo = it
                stroke = 0f
                fill = true
            }
            val dot = shape {
                path(line(4f, -10f))
                addTo = combine { addTo = it }
                stroke = 4f
                fill = true
            }
            dot.copy {
                path(line(12f, -10f))
            }
            dot.copy {
                path(line(20f, -10f))
            }
            dot.copy {
                path(line(28f, -10f))
            }


            val dot2 = dot.copy {
                addTo = combine { addTo = it }
                path(line(44f, -10f))
            }
            dot2.copy {
                path(line(52f, -10f))
            }
            dot2.copy {
                path(line(60f, -10f))
            }
            dot2.copy {
                path(line(68f, -10f))
            }

            dot2.copy {
                path(line(84f, -10f))
            }
            dot2.copy {
                path(line(92f, -10f))
            }
            dot2.copy {
                path(line(100f, -10f))
            }
            dot2.copy {
                path(line(108f, -10f))
            }
            dot2.copy {
                path(line(116f, -10f))
            }
        }
    }

    fun switchDay(world: World, inDay: Boolean, duration: Long = 0, delay: Long = 0) {
        val theme = if (inDay) day else night
        children.forEachIndexed { index, shape ->
            if (index < theme.size) {
                if (duration > 0 || delay > 0) {
                    if (shape is Shape || shape is Combine) {
                        shape.colorTo(world, theme[index])
                            .delay(delay).duration(duration).start()
                    }
                } else {
                    if (shape is Shape || shape is Combine) {
                        shape.color = theme[index]
                    }
                }
            }
        }
    }

    override fun copy(): Ship {
        return copy(Ship())
    }

    override fun copy(shape: Anchor): Ship {
        return (super.copy(shape) as Ship).also {
            it.bodyScale = bodyScale
        }
    }
}