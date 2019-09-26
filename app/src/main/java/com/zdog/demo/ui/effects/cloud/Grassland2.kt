package com.zdog.demo.ui.effects.cloud

import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.effects.layerSpace
import com.zdog.demo.ui.shapes.*
import com.zdog.library.render.*
import kotlin.random.Random

class Grassland2 : Entity() {
    private val container = anchor {
        translate(z = layerSpace)
    }
    private val grasslandA: Shape
    private val grasslandB: Shape
    private val windmills = mutableListOf<Windmill>()

    init {
        grasslandA = shape {
            path(
                line(x = 96f, y = 46f),
                line(x = 72f, y = 46f),
                arc(
                    vector(x = 56f, y = 80f),
                    vector(x = 18f, y = 80f)
                ),
                line(x = 18f, y = 90f),
                line(x = 96f, y = 90f)
            )
            addTo = container
            stroke = 48f
            fill = true
        }
        grasslandB = grasslandA.copy {
            path(
                line(x = 18f, y = 80f),
                line(x = -96f, y = 90f),
                line(x = 18f, y = 90f)
            )
        }
        val windmill = Windmill().set {
            addTo = container
            translate { x = 94f; y = -8f; z = 8f }
            scale(0.7f)
        }.attachTo(windmills)

        windmill.copy {
            translate { x = 60f; y = 0f; z = 12f }
        }.attachTo(windmills)
    }

    init {
        container.translate.x = 240f
    }

    private fun initColor(world: World, theme: Theme) {
        grasslandA.color = theme.grass2
        grasslandB.color = theme.grass2
        windmills.forEach { it.changeColor(world, theme.fan2, theme.body2) }
    }

    override fun onAttachTo(world: World, inDay: Boolean) {
        initColor(world, Theme.get(inDay))
        world.addChild(container)
        container.translateTo(world, x = 0f).duration(((container.translate.x / 240) * 600).toLong()).start()
        windmills.forEach {
            it.animate(
                world, (Random.nextFloat().bound(0.5f, 1f) * 10000).toLong(),
                Random.nextInt(1, 5) * 200L
            ).start()
        }
    }

    override fun onDetachTo(world: World) {
        container.translateTo(world, x = 240f).duration(600).onEnd {
            container.remove()
        }.start()
    }

    override fun onSwitchDay(world: World, inDay: Boolean) {
        val theme = Theme.get(inDay)
        grasslandA.colorTo(world, theme.grass2).delay(1200).start()
        grasslandB.colorTo(world, theme.grass2).delay(1200).start()
        windmills.forEach { it.changeColor(world, theme.fan2, theme.body2, 200, 1200) }
    }
}