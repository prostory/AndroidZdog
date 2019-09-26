package com.zdog.demo.ui.effects.rain

import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.effects.layerSpace
import com.zdog.demo.ui.shapes.*
import com.zdog.library.render.Shape
import com.zdog.library.render.anchor
import com.zdog.library.render.line
import com.zdog.library.render.shape

class Sea3 : Entity() {
    private val container = anchor {
        translate(z = layerSpace * 2)
    }
    private val sea: Shape

    init {
        sea = shape {
            path(
                line(-96f, 90f),
                line(96f, 90f)
            )
            addTo = container
            stroke = 48f
            fill = true
        }
    }

    init {
        container.translate.x = -240f
    }

    private fun initColor(theme: Theme) {
        sea.color = theme.sea3
    }

    override fun onAttachTo(world: World, inDay: Boolean) {
        initColor(Theme.get(inDay))
        world.addChild(container)
        container.translateTo(world, x = 0f).duration(((container.translate.x / -240) * 600).toLong()).start()
    }

    override fun onDetachTo(world: World) {
        container.translateTo(world, x = -240f).duration(600).onEnd {
            container.remove()
        }.start()
    }

    override fun onSwitchDay(world: World, inDay: Boolean) {
        val theme = Theme.get(inDay)
        sea.colorTo(world, theme.sea3).delay(1200).start()
    }
}