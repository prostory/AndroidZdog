package com.zdog.demo.ui.extended

import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.animation.OvershootInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.zdog.demo.ui.shapes.Colors
import com.zdog.library.render.*

class Extended {
    val shapes = mutableListOf<ZdogDrawable>()

    init {
        shapes.add(ZdogDrawable().apply {
            illo.alpha(0f)
            val line = shape {
                addTo = illo
                path(
                    move(x = -32f, y = -40f),
                    line(x = 32f, y = -40f),
                    line(x = -32f, y = 40f),
                    line(x = 32f, y = 40f)
                )
                closed = false
                stroke = 20f
                color = "#636"
                updateSegment(0f, 0f)
            }

            play(line.animate {
                onReset {
                    line.updateSegment(0f, 1f)
                }

                update {
                    line.updateSegment(0f, it)
                }
            }.duration(1500).interpolator(FastOutSlowInInterpolator()).toReverse())
        })

        shapes.add(ZdogDrawable().apply {
            illo.alpha(0f)
            shape {
                addTo = illo
                path(
                    move(-90f),
                    line(-90f)
                )
                color = "#FD4"
                stroke = 16f
            }

            shape {
                addTo = illo
                path(
                    move(90f),
                    line(90f)
                )
                color = "#FD4"
                stroke = 16f
            }

            shape {
                addTo = illo
                path(
                    move(-90f, 0f),
                    arc(
                        vector(-90f, -90f),
                        vector(0f, -90f)
                    ),
                    arc(
                        vector(90f, -90f),
                        vector(90f, 0f)
                    )
                )
                translate { z = -8f }
                color = "#636"
                effect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
                stroke = 4f
                closed = false
            }

            illo.rotate { z = -(TAU / 8).toFloat() }
            play(
                illo.rotateBy(z = (TAU / 4).toFloat()).duration(1500)
                    .interpolator(OvershootInterpolator()).toReverse()
            )
        })

        shapes.add(ZdogDrawable().apply {
            illo.alpha(0f)

            shape {
                addTo = illo
                path(
                    move(-90f, 0f),
                    arc(
                        vector(-90f, 90f),
                        vector(0f, 90f)
                    ),
                    arc(
                        vector(90f, 90f),
                        vector(90f, 0f)
                    )
                )
                shader = LinearGradient(
                    0f, 90f, 0f, 0f,
                    "#636".color, Color.TRANSPARENT, Shader.TileMode.CLAMP
                )
                fill = true
                stroke = 0f
                closed = false
            }

            illo.rotate { z = (TAU / 8).toFloat() }
            play(
                illo.rotateBy(z = -(TAU / 4).toFloat()).duration(1500)
                    .interpolator(OvershootInterpolator()).toReverse()
            )
        })

        shapes.add(ZdogDrawable().apply {
            illo.alpha(0f)

            shape {
                addTo = illo
                path(
                    move(-90f, 0f),
                    arc(
                        vector(-90f, 90f),
                        vector(0f, 90f)
                    ),
                    arc(
                        vector(90f, 90f),
                        vector(90f, 0f)
                    )
                )
                color = "#fff"
                layer = ShaderLayer(
                    16f, 0f, 0f,
                    Colors.shader.colour
                )
                stroke = 8f
                closed = false
            }

            illo.rotate { z = (TAU / 8).toFloat() }
            play(
                illo.rotateBy(z = -(TAU / 4).toFloat()).duration(1500)
                    .interpolator(OvershootInterpolator()).toReverse()
            )
        })

        shapes.add(ZdogDrawable().apply {
            illo.alpha(0f)
            val dotted = shape {
                addTo = illo
                path(
                    move(-90f, 0f),
                    arc(
                        vector(-90f, -90f),
                        vector(0f, -90f)
                    ),
                    arc(
                        vector(90f, -90f),
                        vector(90f, 0f)
                    )
                )
                translate { z = -8f }
                color = "#636"
                effect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
                stroke = 4f
                closed = false
            }

            val keyframes =
                PathKeyframes(illo.renderToPath(dotted))
            val xFrames = keyframes.createXFloatKeyframes()
            val yFrames = keyframes.createYFloatKeyframes()

            val dot = shape {
                addTo = illo
                color = "#FD4"
                stroke = 16f

                translate {
                    x = xFrames.getFloatValue(0f)
                    y = yFrames.getFloatValue(0f)
                }
            }

            play(
                dot.animate {
                    onReset {
                        dot.translate {
                            x = xFrames.getFloatValue(0f)
                            y = yFrames.getFloatValue(0f)
                        }
                    }

                    update {
                        dot.translate {
                            x = xFrames.getFloatValue(it)
                            y = yFrames.getFloatValue(it)
                        }
                    }
                }.duration(1500).interpolator(FastOutSlowInInterpolator())
                    .toReverse()
            )
        })

        shapes.add(ZdogDrawable().apply {
            illo.alpha(0f)
            val arrow = shape {
                addTo = illo
                path(
                    move(-80f, 40f),
                    line(0f, -40f),
                    line(80f, 40f)
                )
                color = "#fff"
                stroke = 10f
                layer = ShaderLayer(
                    16f, 0f, 0f,
                    Colors.shader.colour
                )
                closed = false
            }

            fun updatePath(shape: Shape, top: Float) {
                shape.apply {
                    path[0].point().y = -top
                    path[1].point().y = top
                    path[2].point().y = -top
                }
            }

            play(arrow.animate {
                onReset {
                    updatePath(arrow, 40f)
                }

                update {
                    updatePath(arrow, -40f + it * 80f)
                }
            }.duration(1500).toReverse())
        })
    }
}