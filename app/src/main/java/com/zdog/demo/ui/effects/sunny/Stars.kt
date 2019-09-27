package com.zdog.demo.ui.effects.sunny

import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.effects.layerSpace
import com.zdog.demo.ui.effects.star
import com.zdog.demo.ui.shapes.*
import com.zdog.demo.ui.shapes.Colors.gold1
import com.zdog.library.render.*

class Stars: Entity() {
    private val star1 = star {
        translate { x = -50f; y = -50f;
            z = layerSpace *-1.5f}
        color = gold1.colour
    }

    private val star2 = star {
        translate { x = 42f; y = -20f;
            z = -layerSpace *0.5f }
        color = gold1.colour
    }

    private fun leave(world: World) {
        star1.alphaTo(world, 0f).onEnd {
            star1.remove()
        }.start()
        star2.alphaTo(world, 0f).onEnd {
            star2.remove()
        }.start()
    }

    override fun onDetachTo(world: World) {
        leave(world)
    }

    override fun onSwitchDay(world: World, inDay: Boolean) {
        if (inDay) {
            leave(world)
        } else {
            world.addChild(star1)
            world.addChild(star2)
            star1.alpha = 0f
            star1.alphaTo(world, 1f).delay(2000).duration(800)
                .toReverse().start()
            star2.alpha = 0f
            star2.alphaTo(world, 1f).delay(1200).duration(1200)
                .toReverse().start()
        }
    }
}