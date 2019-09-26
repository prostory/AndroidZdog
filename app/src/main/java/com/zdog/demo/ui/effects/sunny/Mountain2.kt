package com.zdog.demo.ui.effects.sunny

import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.effects.layerSpace
import com.zdog.demo.ui.effects.tree
import com.zdog.demo.ui.shapes.*
import com.zdog.library.render.*

class Mountain2: Entity() {
    private val container = anchor {
        translate(z = layerSpace)
    }
    private val mountainA: Shape
    private val mountainB: Shape

    init {
        mountainA = shape {
            path(
                line(x = 96f, y = 26f),
                line(x = 72f, y = 26f),
                arc(
                    vector(x = 56f, y = 50f),
                    vector(x = 18f, y = 50f)
                ),
                line(x = 18f, y = 90f),
                line(x = 96f, y = 90f)
            )
            addTo = container
            stroke = 48f
            fill = true
        }
        mountainB = mountainA.copy {
            path(
                line(x = 18f, y = 50f),
                arc(
                    vector(x = -16f, y = 90f),
                    vector(x = -48f, y = 72f)
                ),
                line(x = -64f, y = 56f),
                line(x = -96f, y = 48f),
                line(x = -96f, y = 90f),
                line(x = 18f, y = 90f)
            )
        }

        val ball = shape {
            addTo = container
            translate(x = -92f, y = 18f)
            stroke = 20f
        }
        ball.copy {
            translate(x = -104f, y = 28f)
        }
        ball.copy {
            translate(x = -84f, y = 28f)
            stroke = 24f
        }
        ball.copy {
            translate(x = -74f, y = 20f)
        }
        ball.copy {
            translate(x = -60f, y = 28f)
        }
        ball.copy {
            translate(x = -50f, y = 36f)
        }
        ball.copy {
            translate(x = -44f, y = 46f)
        }

        val block: Shape.()-> Unit = {
            addTo = container
            stroke = 2f
        }
        tree(block,
            width = 10f,
            height = 24f,
            translate = vector(x = -12f, y = 42f)
        )
        tree(block,
            width = 10f,
            height = 24f,
            translate = vector(x = 10f, y = 22f, z = 2f)
        )
        tree(block,
            width = 16f,
            height = 36f,
            translate = vector(x = 22f, y = 18f, z = -6f)
        )
        tree(block,
            width = 16f,
            height = 36f,
            translate = vector(x = 76f, y = -6f, z = 12f)
        )
        tree(block,
            width = 10f,
            height = 24f,
            translate = vector(x = 86f, y = -4f, z = -10f)
        )
    }

    init {
        container.translate.x = 240f
    }

    private fun initColor(theme: Theme) {
        container.children.filter { it != mountainA && it != mountainB }
            .forEach {
                (it as Shape).color = theme.leaf2
            }
        mountainA.color = theme.mountain2
        mountainB.color = theme.mountain2
    }

    override fun onAttachTo(world: World, inDay: Boolean) {
        initColor(Theme.get(inDay))
        world.addChild(container)
        container.translateTo(world, 0f).duration(((container.translate.x/240) * 600).toLong()).start()
    }

    override fun onDetachTo(world: World) {
        container.translateTo(world, 240f).duration(600).onEnd {
            container.remove()
        }.start()
    }

    override fun onSwitchDay(world: World, inDay: Boolean) {
        val theme = Theme.get(inDay)
        container.children.filter { it != mountainA && it != mountainB }
            .forEach {
                it.colorTo(world, theme.leaf2).delay(1200).start()
            }
        mountainA.colorTo(world, theme.mountain2).delay(1200).start()
        mountainB.colorTo(world, theme.mountain2).delay(1200).start()
    }
}