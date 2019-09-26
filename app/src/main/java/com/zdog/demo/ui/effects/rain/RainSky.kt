package com.zdog.demo.ui.effects.rain

import android.animation.ValueAnimator
import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.effects.Sky
import com.zdog.demo.ui.effects.layerSpace
import com.zdog.demo.ui.shapes.*
import com.zdog.library.render.attachTo
import com.zdog.library.render.set
import com.zdog.library.render.vector

class RainSky(private val sky: Sky) : Entity() {
    private val drops1 = Raindrops(
        10,
        0.5f to 0.8f, 6f to 8f,
        -70f to 70f, -90f to 90f
    )
    private val drops2 = Raindrops(
        10,
        1f to 1.5f, 4f to 5f,
        -70f to 70f, -90f to 90f
    ).set {
        translate { z = layerSpace * 2 }
    }

    override fun onAttachTo(world: World, inDay: Boolean) {
        val theme = Theme.get(inDay)
        world.addChild(drops1)
        world.addChild(drops2)
        drops1.alpha = 0f
        drops2.alpha = 0f
        sky.onAttachTo(world, theme)
        drops1.color = theme.sea1
        drops2.color = theme.sea3
        sky.bgGroup1.children[1].translateTo(world, y = 0f).duration(600).start()
        sky.bgGroup2.children[1].translateTo(world, y = 8f).duration(600).start()
        sky.bgGroup3.children[1].translateTo(world, y = 24f).duration(600).start()
        sky.sun.translateTo(world, y = 0f).onEnd {
            sky.sun.remove()
            sky.sunshine.remove()
        }.start()
        sky.sunshine.translateTo(world, y = 0f).start()
        sky.cloud.translateTo(world, vector(y = -120f, z = layerSpace * 4)).duration(1200).onEnd {
            drops1.alpha = 1f
            drops2.alpha = 1f
            drops1.restart(world)
            drops2.restart(world)
        }.start()
    }

    override fun onDetachTo(world: World) {
        sky.background.addChild(sky.sunshine)
        sky.background.addChild(sky.sun)
        sky.sun.alpha = 0f
        sky.sunshine.alpha = 0f
        sky.bgGroup1.children[1].translateTo(world, y = -16f).duration(600).start()
        sky.bgGroup2.children[1].translateTo(world, y = -16f).duration(600).start()
        sky.bgGroup3.children[1].translateTo(world, y = -16f).duration(600).start()
        sky.sun.translateTo(world, y = -16f).duration(600)
            .onEnd {
                sky.sun.alpha = 1f
                sky.sunshine.alpha = 1f
            }.start()
        sky.sunshine.translateTo(world, y = -16f).duration(600).start()
        sky.cloud.translate.z = -layerSpace
        drops1.remove()
        drops2.remove()
    }

    override fun onSwitchDay(world: World, inDay: Boolean) {
        val theme = Theme.get(inDay)
        sky.bgGroup1.colorTo(world, theme.background1).duration(1200).start()
        sky.bgGroup2.colorTo(world, theme.background2).duration(1200).start()
        sky.bgGroup3.colorTo(world, theme.background3).duration(1200).start()
        sky.cloud.colorTo(world, theme.cloud).duration(1200).start()
        drops1.colorTo(world, theme.sea1).duration(1200).start()
        drops2.colorTo(world, theme.sea3).duration(1200).start()
        world.world.colorTo(world, theme.sky).duration(1200).start()
    }
}