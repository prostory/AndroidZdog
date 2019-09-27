package com.zdog.demo.ui.effects.rain

import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.effects.layerSpace
import com.zdog.demo.ui.shapes.*
import com.zdog.library.render.*

class Sea2 : Entity() {
    private val container = anchor {
        translate(z = layerSpace)
    }
    private val sea: Shape
    private val ship: Ship
    private val spray: Spray
    private val smoke: Smoke

    init {
        sea = shape {
            path(
                line(-96f, 80f),
                line(96f, 80f)
            )
            addTo = container
            stroke = 48f
            fill = true
        }

        ship = Ship().set {
            addTo = container
        }

        spray = Spray().set {
            addTo = container
            translate { x = 55f; y = 65f; z = -8f }
        }

        smoke = Smoke().set {
            addTo = ship
            translate { x = 33f; y = -57f }
        }
    }

    init {
        container.translate.x = 240f
    }

    private fun initColor(theme: Theme) {
        sea.color = theme.sea2
        spray.color = theme.sea2
        smoke.color = Colors.gray1.colour
    }

    override fun onAttachTo(world: World, inDay: Boolean) {
        initColor(Theme.get(inDay))
        world.addChild(container)
        container.translateTo(world,x=0f).duration(((container.translate.x/240) * 600).toLong()).start()
        ship.switchDay(world, inDay)
        ship.translate { x=-60f; y=54f; z =-8f }
        ship.rotate()
        ship.translateBy(world, y=-8f).duration(1600).toReverse().start()
        ship.rotateBy(world, z=(-TAU/60f).toFloat()).delay(1200).duration(1600).toReverse().start()
        spray.run(world)
        smoke.run(world)
    }

    override fun onDetachTo(world: World) {
        container.translateTo(world, x=240f).duration(600).onEnd {
            container.remove()
        }.start()
    }

    override fun onSwitchDay(world: World, inDay: Boolean) {
        val theme = Theme.get(inDay)
        sea.colorTo(world, theme.sea2).delay(1200).start()
        spray.colorTo(world, theme.sea2).delay(1200).start()
        ship.switchDay(world, inDay, 0, 1200)
    }
}