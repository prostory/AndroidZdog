package com.zdog.demo.ui.effects.sunny

import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.effects.Sky
import com.zdog.demo.ui.shapes.World
import com.zdog.library.render.duration
import com.zdog.library.render.translateTo

class SunnySky(private val sky: Sky): Entity() {
    override fun onAttachTo(world: World, inDay: Boolean) {
        sky.onAttachTo(world, Theme.get(inDay))
        sky.cloud.translateTo(world, y=0f).duration(1200).start()
    }

    override fun onSwitchDay(world: World, inDay: Boolean) {
        sky.onSwitchDay(world, Theme.get(inDay), inDay)
    }
}