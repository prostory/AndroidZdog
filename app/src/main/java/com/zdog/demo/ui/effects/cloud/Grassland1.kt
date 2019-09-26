package com.zdog.demo.ui.effects.cloud

import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.shapes.*
import com.zdog.library.render.*
import kotlin.random.Random

class Grassland1 : Entity() {
    private val container = anchor()
    private val grasslandA: Shape
    private val grasslandB: Shape
    private val windmills = mutableListOf<Windmill>()

    init {
        grasslandA = shape {
            path(
                line(x = -96f, y = 30f),
                line(x = -86f, y = 30f),
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
        grasslandB = grasslandA.copy {
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
        val windmill = Windmill().set {
            addTo = container
            translate { x = -86f; y = -14f; z = -8f }
            scale(0.5f)
        }.attachTo(windmills)

        windmill.copy {
            translate { x = -60f; y = -8f; z = 4f }
        }.attachTo(windmills)

        windmill.copy {
            translate { x = -40f; y = -4f; z = 8f }
        }.attachTo(windmills)

        windmill.copy {
            translate { x = -16f; y = 0f; z = 12f }
        }.attachTo(windmills)

        windmill.copy {
            translate { x = 10f; y = 24f; z = 8f }
        }.attachTo(windmills)
    }

    init {
        container.translate.x = -240f
    }

    private fun initColor(world: World, theme: Theme) {
        grasslandA.color = theme.grass1
        grasslandB.color = theme.grass1
        windmills.forEach { it.changeColor(world, theme.fan1, theme.body1) }
    }

    override fun onAttachTo(world: World, inDay: Boolean) {
        initColor(world, Theme.get(inDay))
        world.addChild(container)
        container.translateTo(world, x = 0f).duration(((container.translate.x / -240) * 600).toLong()).start()
        windmills.forEach {
            it.animate(
                world, (Random.nextFloat().bound(0.5f, 1f) * 10000).toLong(),
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
        grasslandA.colorTo(world, theme.grass1).delay(1200).start()
        grasslandB.colorTo(world, theme.grass1).delay(1200).start()
        windmills.forEach { it.changeColor(world, theme.fan1, theme.body1, 200, 1200) }
    }
}