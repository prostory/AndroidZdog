package com.zdog.demo.ui.effects.sunny

import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.effects.tree
import com.zdog.demo.ui.shapes.*
import com.zdog.library.render.*

class Mountain1 : Entity() {
    private val container = anchor()
    private val mountainA: Shape
    private val mountainB: Shape
    private val trunk: Shape

    init {
        mountainA = shape {
            path(
                line(x = -96f, y = 10f),
                line(x = -86f, y = 10f),
                arc(
                    vector(x = -60f, y = 42f),
                    vector(x = -26f, y = 42f)
                ),
                line(x = -26f, y = 74f),
                line(x = -96f, y = 74f)
            )
            addTo = container
            stroke = 48f
            fill = true
        }
        mountainB = mountainA.copy {
            path(
                line(x = -26f, y = 42f),
                arc(
                    vector(x = -8f, y = 74f),
                    vector(x = 36f, y = 74f)
                ),
                line(x = 96f, y = 74f),
                line(x = -26f, y = 74f)
            )
        }

        val block: Shape.() -> Unit = {
            addTo = container
            stroke = 2f
        }
        tree(
            block,
            width = 10f,
            height = 24f,
            translate = vector(x = -86f, y = -14f, z = -8f)
        )
        tree(
            block,
            width = 16f,
            height = 36f,
            translate = vector(x = -70f, y = -12f, z = 14f)
        )
        tree(
            block,
            width = 10f,
            height = 24f,
            translate = vector(x = -60f, y = -4f)
        )
        tree(
            block,
            width = 10f,
            height = 24f,
            translate = vector(x = -26f, y = 12f, z = -8f)
        )
        tree(
            block,
            width = 10f,
            height = 24f,
            translate = vector(x = -18f, y = 18f, z = 2f)
        )

        val lonelyTranslate = vector(x = 32f, y = 24f)
        tree(
            block,
            width = 16f,
            height = 36f,
            translate = lonelyTranslate
        )

        trunk = shape {
            path(
                line(y = 18f),
                line(y = 28f)
            )
            addTo = container
            translate.set(lonelyTranslate)
            stroke = 4f
        }

        tree(
            block,
            width = 10f,
            height = 24f,
            translate = vector(x = 64f, y = 40f, z = 6f)
        )
        tree(
            block,
            width = 10f,
            height = 24f,
            translate = vector(x = 72f, y = 44f, z = -2f)
        )
    }

    private fun initColor(theme: Theme) {
        container.children.filter { it != trunk && it != mountainA && it != mountainB }
            .forEach {
                (it as Shape).color = theme.leaf1
            }
        mountainA.color = theme.mountain1
        mountainB.color = theme.mountain1
        trunk.color = theme.trunk1
    }

    init {
        container.translate.x = -240f
    }

    override fun onAttachTo(world: World, inDay: Boolean) {
        initColor(Theme.get(inDay))
        world.addChild(container)
        container.translateTo(world, x = 0f).duration(((container.translate.x / -240) * 600).toLong()).start()
    }

    override fun onDetachTo(world: World) {
        container.translateTo(world, -240f).duration(600).onEnd {
            container.remove()
        }.start()
    }

    override fun onSwitchDay(world: World, inDay: Boolean) {
        val theme = Theme.get(inDay)
        container.children.filter { it != trunk && it != mountainA && it != mountainB }
            .forEach {
                it.colorTo(world, theme.leaf1).delay(1200).start()
            }
        mountainA.colorTo(world, theme.mountain1).delay(1200).start()
        mountainB.colorTo(world, theme.mountain1).delay(1200).start()
        trunk.colorTo(world, theme.trunk1).delay(1200).start()
    }
}