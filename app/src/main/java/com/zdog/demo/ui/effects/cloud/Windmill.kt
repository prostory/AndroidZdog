package com.zdog.demo.ui.effects.cloud

import android.animation.ValueAnimator
import com.zdog.demo.ui.shapes.*
import com.zdog.library.render.*

class Windmill : Group() {
    private val body: Shape
    private val dot: Shape
    private val fan: Combine
    private var bodyScale = 1f

    init {
        let {
            body = shape {
                path(
                    line(x = 0f, y = 0f),
                    line(x = -3f, y = 50f),
                    line(x = 3f, y = 50f)
                )
                addTo = it
                stroke = 3f
                fill = true
            }

            fan = combine {
                addTo = it
            }.also {
                shape {
                    path(
                        line(x = -2f, y = 0f),
                        line(x = 0f, y = 35f),
                        line(x = 2f, y = 0f)
                    )
                    addTo = it
                    stroke = 0f
                    fill = true
                }.copy {
                    rotate {
                        z = (TAU * 1 / 3).toFloat()
                    }
                }.copy {
                    rotate {
                        z = (TAU * 2 / 3).toFloat()
                    }
                }
            }

            dot = shape {
                addTo = it
                stroke = 4f
                fill = true
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        body.stroke = 3 * bodyScale
        dot.stroke = 4 * bodyScale
    }

    override fun scale(scale: Float): Vector {
        bodyScale = scale
        body.stroke = 3 * scale
        dot.stroke = 4 * scale
        return super.scale(scale)
    }

    fun changeColor(world: World, fan: Colour, body: Colour, duration: Long = 0, delay: Long = 0) {
        if (duration > 0) {
            this.fan.colorTo(world, fan).delay(delay).duration(duration).start()
            this.body.colorTo(world, body).delay(delay).duration(duration).start()
            this.dot.colorTo(world, body).delay(delay).duration(duration).start()
        } else {
            if (delay > 0) {
                this.fan.colorTo(world, fan).delay(delay).start()
                this.body.colorTo(world, body).delay(delay).start()
                this.dot.colorTo(world, body).delay(delay).start()
            } else {
                this.fan.color = fan
                this.body.color = body
                this.dot.color = body
            }
        }
    }

    fun animate(world: World, duration: Long, delay: Long = 0): ValueAnimator {
        fan.rotate.z = 0f
        return fan.rotateTo(world, z = TAU.toFloat()).delay(delay)
            .duration(duration).repeat()
    }

    override fun copy(): Windmill {
        return copy(Windmill())
    }

    override fun copy(shape: Anchor): Windmill {
        return (super.copy(shape) as Windmill).also {
            it.bodyScale = bodyScale
        }
    }
}