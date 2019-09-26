package com.zdog.demo.ui.effects.sunny

import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.effects.bird
import com.zdog.demo.ui.effects.layerSpace
import com.zdog.demo.ui.shapes.*
import com.zdog.demo.ui.shapes.Colors.midnight

class Bird : Entity() {
    private val shape = bird {
        translate { x = 24f; y = -35f
            z = layerSpace * -1f }
        color = midnight.colour
    }

    private fun leave(world: World) {
        shape.alphaTo(world, 0f).delay(1200).onEnd {
            shape.remove()
        }.start()
    }

    override fun onDetachTo(world: World) {
        leave(world)
    }

    override fun onSwitchDay(world: World, inDay: Boolean) {
        if (inDay) {
            world.addChild(shape)
            shape.alpha = 0f
            shape.translate.y = -35f
            shape.translateBy(world, y=-5f).duration(1400).toReverse().start()
            shape.alphaTo(world, 1f).delay(1800).start()
        } else {
            leave(world)
        }
    }
}