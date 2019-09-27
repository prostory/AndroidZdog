package com.zdog.demo.ui.effects

import android.animation.ValueAnimator
import com.zdog.demo.ui.shapes.*
import com.zdog.library.render.*

class Sky(world: World) {
    val background = shape {
        addTo = world.illo
        translate(z = layerSpace * -2)
        visible = false
    }
    val bgGroup1: Combine
    val bgGroup2: Combine
    val bgGroup3: Combine
    val sun: Shape
    val sunshine: Combine
    val cloud: Combine
    private val animators = mutableListOf<ValueAnimator>()

    init {
        bgGroup1 = combine {
            addTo = background
            translate(z = -24f)
        }

        val bgStripe = rect {
            width = 180f
            height = 44f
            addTo = bgGroup1
            translate(y = -40f)
            stroke = 12f
            fill = true
        }

        val bgCircle = ellipse {
            diameter = 96f
            addTo = bgGroup1
            translate(y = -16f)
            stroke = 24f
            fill = true
        }

        bgGroup2 = combine {
            addTo = background
        }

        bgStripe.copy {
            addTo = bgGroup2
            translate(y = -8f)
        }

        bgCircle.copy {
            addTo = bgGroup2
            diameter = 64f
            translate(y = -16f)
        }

        bgGroup3 = combine {
            addTo = background
            translate(z = 24f)
        }

        bgStripe.copy {
            addTo = bgGroup3
            height = 60f
            translate(y = 32f)
        }

        bgCircle.copy {
            addTo = bgGroup3
            width = 32f
            height = 32f
            translate(y = -16f)
        }

        sunshine = combine {
            translate(y = -16f, z = 47f)
            color = Colors.gold2.colour
        }.also {
            (0..11).forEach { index ->
                shape {
                    translate(
                        (16 * Math.cos(index * TAU / 12).toFloat()),
                        (16 * Math.sin(index * TAU / 12).toFloat())
                    )
                    stroke = 5f
                    addTo = it
                }
            }
        }

        sun = shape {
            translate(y = -16f, z = 48f)
            addTo = background
            stroke = 24f
        }

        cloud = cloud {
            addTo = world.illo
            translate(z = layerSpace * -1)
        }
    }

    fun onAttachTo(world: World, theme: SkyTheme) {
        bgGroup1.color = theme.background1
        bgGroup2.color = theme.background2
        bgGroup3.color = theme.background3
        sun.color = theme.sun
        cloud.color = theme.cloud
        world.illo.color = theme.sky
    }

    fun onSwitchDay(world: World, theme: SkyTheme, inDay: Boolean) {
        if (inDay) {
            animators.forEach { it.cancel() }
            animators.clear()
            background.addChild(sunshine)
            sun.colorTo(world, theme.sun).duration(1200).start()
            sunshine.children.forEachIndexed { index, dot ->
                dot.translate.set(
                    (16 * Math.cos(index * TAU / 12).toFloat()),
                    (16 * Math.sin(index * TAU / 12).toFloat())
                ).multiply(8 / 16f)
                dot.translateTo(world, dot.translate.copy().multiply(16 / 8f))
                    .delay(index * 100L).start()
            }
            sunshine.rotateBy(world, z = TAU.toFloat()).delay(1200).duration(8000).repeat().attachTo(animators).start()
            bgGroup1.colorTo(world, theme.background1).duration(1200).start()
            bgGroup2.colorTo(world, theme.background2).duration(1200).start()
            bgGroup3.colorTo(world, theme.background3).duration(1200).start()
            cloud.colorTo(world, theme.cloud).duration(1200).start()
            world.illo.colorTo(world, theme.sky).duration(1200).start()
        } else {
            sunshine.children.forEachIndexed { index, dot ->
                dot.translateTo(
                    world, vector(
                        vector(
                            (16 * Math.cos(index * TAU / 12).toFloat()),
                            (16 * Math.sin(index * TAU / 12).toFloat())
                        )
                    ).multiply(8 / 16f)
                )
                    .delay(index * 100L).start()
            }
            sun.colorTo(world, theme.sun).delay(1200).onEnd {
                sunshine.remove()
                animators.forEach { it.cancel() }
                animators.clear()
            }.start()
            bgGroup1.colorTo(world, theme.background1).delay(1200).start()
            bgGroup2.colorTo(world, theme.background2).delay(1200).start()
            bgGroup3.colorTo(world, theme.background3).delay(1200).start()
            cloud.colorTo(world, theme.cloud).delay(1200).start()
            world.illo.colorTo(world, theme.sky).delay(1200).start()
        }
    }
}