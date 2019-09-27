package com.zdog.demo.ui.effects.sunny

import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.effects.layerSpace
import com.zdog.demo.ui.shapes.Colors
import com.zdog.demo.ui.shapes.Colors.amber
import com.zdog.demo.ui.shapes.World
import com.zdog.library.render.alphaTo
import com.zdog.library.render.delay
import com.zdog.library.render.anchor
import com.zdog.library.render.copy
import com.zdog.library.render.shape
import com.zdog.library.render.vector
import kotlin.random.Random

class Glowworm: Entity() {
    private val container = anchor()
    private val bound = vector(-110f, 20f)

    init {
        val particle = shape {
            translate(x = -70f, y = -50f,
                z = layerSpace *-0.25f)
            stroke = 4f
            addTo = container
            color = Colors.gold1.colour
        }

        particle.copy {
            translate(x = 68f, y = -28f,
                z = layerSpace *0.5f)
        }

        particle.copy {
            translate(x = -70f, y = 2f,
                z = layerSpace *0.75f)
            color = amber.colour
        }

        particle.copy {
            translate(x = 74f, y = 14f,
                z = layerSpace *1.5f)
        }

        particle.copy {
            translate(x = -24f, y = 34f,
                z = layerSpace *1.75f)
        }

        particle.copy {
            translate(x = 34f, y = 34f,
                z = layerSpace *1.9f)
            color = amber.colour
        }

        particle.copy {
            translate(x = 22f, y = 40f,
                z = layerSpace *2.2f)
        }
    }

    override fun onDetachTo(world: World) {
        container.remove()
    }

    override fun onSwitchDay(world: World, inDay: Boolean) {
        if (inDay) {
            container.remove()
        } else {
            world.addChild(container)
            container.alpha = 1f
            container.children.forEach {
                it.alpha = 0f
                it.alphaTo(world, 1f).
                    delay(Random.nextInt(1, 5) * 200+1200L).start()
            }
            run(world)
        }
    }

    private fun run(world: World) {
        container.children.forEach {
            RandomMover(it, bound).animate(world).start()
        }
    }
}