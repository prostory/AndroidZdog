package com.zdog.demo.ui.effects.sunny

import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.effects.grass
import com.zdog.demo.ui.effects.layerSpace
import com.zdog.demo.ui.effects.tree
import com.zdog.demo.ui.shapes.*
import com.zdog.library.render.*

class Mountain3: Entity() {
    private val mountainA: Shape
    private val mountainB: Shape
    private val trunk1: Shape
    private val trunk2: Shape

    init {
        mountainA = shape {
            path(
                line(x = -96f, y = 52f),
                line(x = -84f, y = 52f),
                arc(
                    vector(x = -72f, y = 72f),
                    vector(x = -44f, y = 72f)
                ),
                arc(
                    vector(x = -32f, y = 90f),
                    vector(x = 0f, y = 90f)
                ),
                line(x = -96f, y = 90f)
            )
            translate(z = layerSpace *2)
            stroke = 48f
            fill = true
        }

        val treeA: Shape.()-> Unit = {
            addTo = mountainA
            stroke = 2f
        }
        tree(treeA,
            width = 18f,
            height = 44f,
            translate = vector(x = -80f, y = 18f)
        )

        val treeTranslate = vector(x = -44f, y = 14f)

        trunk1 = shape {
            path(
                line(y = 22f),
                line(y = 38f)
            )
            addTo = mountainA
            translate.set(treeTranslate)
            stroke = 6f
        }
        tree(treeA,
            width = 18f,
            height = 44f,
            translate = treeTranslate)

        tree(treeA,
            width = 16f,
            height = 36f,
            translate = vector(x = -2f, y = 64f))

        val grass = grass {
            addTo = mountainA
            translate(x = -20f, y = 56f, z = layerSpace*-0.5f)
        }
        grass.copy {
            translate(x = -33f, y = 50f, z = layerSpace*-0.5f)
            rotate(z = (TAU /2 + 0.2).toFloat())
        }
        grass.copy {
            translate(x = -62f, y = 40f)
            rotate(z = 0.8f)
            scale(7f)
        }
        grass.copy {
            translate(x = -64f, y = 35f)
            rotate(z = 0.4f)
            scale(7f)
        }

        mountainB = shape {
            path(
                line(x = 96f, y = 52f),
                arc(
                    vector(x = 80f, y = 72f),
                    vector(x = 56f, y = 72f)
                ),
                arc(
                    vector(x = 40f, y = 90f),
                    vector(x = 8f, y = 90f)
                ),
                line(x = 0f, y = 90f),
                line(x = 96f, y = 90f)
            )
            translate(z = layerSpace *2)
            stroke = 48f
            fill = true
        }

        val treeB: Shape.()-> Unit = {
            addTo = mountainB
            stroke = 2f
        }
        tree(treeB,
            width = 16f,
            height = 36f,
            translate = vector(x = 10f, y = 54f))

        val bigTreeTranslate = vector(x = 58f, y = 2f)
        trunk2 = shape {
            path(
                line(y = 32f),
                line(y = 48f)
            )
            addTo = mountainB
            translate.set(bigTreeTranslate)
            stroke = 6f
        }
        tree(treeB,
            width = 20f,
            height = 64f,
            translate = bigTreeTranslate)

        tree(treeB,
            width = 16f,
            height = 36f,
            translate = vector(x = 86f, y = 26f))

        grass.copy {
            addTo = mountainB
            scale(12f)
            translate(x = 46f, y = 54f, z = layerSpace*-0.5f)
            rotate(z = 0f)
        }
        grass.copy {
            addTo = mountainB
            scale(10f)
            translate(x = 28f, y = 58f, z = layerSpace*-0.5f)
            rotate(z = (TAU /2 - 0.4).toFloat())
        }
    }

    init {
        mountainA.translate.x = -240f
        mountainB.translate.x = -240f
    }

    private fun initColor(theme: Theme) {
        mountainA.children.filter { it != trunk1 }.forEach {
            (it as Shape).color = theme.leaf3
        }
        mountainB.children.filter { it != trunk2 }.forEach {
            (it as Shape).color = theme.leaf3
        }
        mountainA.color = theme.mountain3
        mountainB.color = theme.mountain3
        trunk1.color = theme.trunk3
        trunk2.color = theme.trunk3
    }

    override fun onAttachTo(world: World, inDay: Boolean) {
        initColor(Theme.get(inDay))
        world.addChild(mountainA)
        world.addChild(mountainB)
        mountainA.translateTo(world, 0f).duration(((mountainA.translate.x/-240) * 600).toLong()).start()
        mountainB.translateTo(world, 0f).duration(((mountainB.translate.x/-240) * 600).toLong()).start()
    }

    override fun onDetachTo(world: World) {
        mountainA.translateTo(world, -240f).duration(600).onEnd {
            mountainA.remove()
        }.start()
        mountainB.translateTo(world, -240f).duration(600).onEnd {
            mountainB.remove()
        }.start()
    }

    override fun onSwitchDay(world: World, inDay: Boolean) {
        val theme = Theme.get(inDay)
        mountainA.children.filter { it != trunk1 }.forEach {
            it.colorTo(world, theme.leaf3).delay(1200).start()
        }
        mountainB.children.filter { it != trunk2 }.forEach {
            it.colorTo(world, theme.leaf3).delay(1200).start()
        }
        mountainA.colorTo(world, theme.mountain3).delay(1200).start()
        mountainB.colorTo(world, theme.mountain3).delay(1200).start()
        trunk1.colorTo(world, theme.trunk3).delay(1200).start()
        trunk2.colorTo(world, theme.trunk3).delay(1200).start()
    }
}