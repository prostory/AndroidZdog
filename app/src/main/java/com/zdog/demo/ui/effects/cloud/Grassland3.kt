package com.zdog.demo.ui.effects.cloud

import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.effects.layerSpace
import com.zdog.demo.ui.shapes.*
import com.zdog.library.render.*
import kotlin.random.Random

class Grassland3 : Entity() {
    private val container = anchor {
        translate(z = layerSpace * 2)
    }
    private val grasslandA: Shape
    private val grasslandB: Shape
    private val windmills = mutableListOf<Windmill>()

    init {
        grasslandA = shape {
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
            addTo = container
            stroke = 48f
            fill = true
        }
        grasslandB = grasslandA.copy {
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
        }

        val windmill = Windmill().set {
            addTo = container
            translate { x = -40f; y = 6f }
        }.attachTo(windmills)

        windmill.copy {
            translate { x = 40f; y = 12f }
        }.attachTo(windmills)
    }

    init {
        container.translate.x = -240f
    }

    private fun initColor(world: World, theme: Theme) {
        grasslandA.color = theme.grass3
        grasslandB.color = theme.grass3
        windmills.forEach { it.changeColor(world, theme.fan3, theme.body3) }
    }

    override fun onAttachTo(world: World, inDay: Boolean) {
        initColor(world, Theme.get(inDay))
        world.addChild(container)
        container.translateTo(world, x = 0f).duration(((container.translate.x / -240) * 600).toLong()).start()
        windmills.forEach {
            it.animate(
                world, (Random.nextFloat().bound(0.5f, 1f) * 8000).toLong(),
                Random.nextInt(1, 5) * 200L
            ).start()
        }
    }

    override fun onDetachTo(world: World) {
        container.translateTo(world, x = -240f).duration(600).onEnd {
            container.remove()
        }.start()
    }

    override fun onSwitchDay(world: World, inDay: Boolean) {
        val theme = Theme.get(inDay)
        grasslandA.colorTo(world, theme.grass3).delay(1200).start()
        grasslandB.colorTo(world, theme.grass3).delay(1200).start()
        windmills.forEach { it.changeColor(world, theme.fan3, theme.body3, 200, 1200) }
    }
}