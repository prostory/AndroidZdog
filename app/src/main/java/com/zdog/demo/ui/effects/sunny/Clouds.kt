package com.zdog.demo.ui.effects.sunny

import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.effects.cloud1
import com.zdog.demo.ui.effects.cloud2
import com.zdog.demo.ui.effects.layerSpace
import com.zdog.demo.ui.shapes.*
import com.zdog.demo.ui.shapes.Colors.white
import com.zdog.library.render.TAU
import com.zdog.library.render.combine
import com.zdog.library.render.copy

class Clouds : Entity() {
    private val container = combine {
        color = white.colour
    }
    private val cloud1 = cloud1 {
        addTo = container
    }
    private val cloud2 = cloud1.copy {
        rotate(y = (-TAU * 1 / 8).toFloat())
        scale { x = (1 / Math.cos(TAU * 1 / 8) * -1).toFloat() }
    }
    private val cloud3 = cloud2 {
        addTo = container
    }

    private fun init() {
        cloud1.scale(0.3f)
        cloud1.translate {
            x = -84f; y = -28f
            z = layerSpace * -1
        }
        cloud2.scale(0.4f)
        cloud2.translate {
            x = -38f; y = -22f
            z = layerSpace * -0.5f
        }
        cloud3.scale(0.5f)
        cloud3.translate {
            x = 72f; y = -52f
            z = layerSpace * -1
        }
    }

    override fun onAttachTo(world: World, inDay: Boolean) {
        world.addChild(container)
        init()
        cloud1.scaleTo(world, 1.0f).duration(1200).start()
        cloud1.translateBy(world, y = -5f).duration(1200).toReverse().start()
        cloud2.scaleTo(world, 1.0f).duration(1200).start()
        cloud2.translateBy(world, y = 5f).delay(400).duration(1200).toReverse().start()
        cloud3.scaleTo(world, 1.0f).duration(1200).start()
        cloud3.translateBy(world, y=-5f).delay(600).duration(1200).toReverse().start()
    }

    override fun onDetachTo(world: World) {
        cloud1.translateBy(world,x=-120f).duration(1200).onEnd {
            container.remove()
        }.start()
        cloud2.translateBy(world,x=-120f).duration(1200).start()
        cloud3.translateBy(world,x=120f).duration(1200).start()
    }
}