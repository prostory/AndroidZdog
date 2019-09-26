package com.zdog.demo.ui.effects.rain

import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.effects.Sky
import com.zdog.demo.ui.shapes.World
import com.zdog.library.render.attachTo

class Rain(sky: Sky): Entity() {
    private val entities = mutableListOf<Entity>()

    init {
        RainSky(sky).attachTo(entities)
        Sea1().attachTo(entities)
        Sea2().attachTo(entities)
        Sea3().attachTo(entities)
    }

    override fun onAttachTo(world: World, inDay: Boolean) {
        entities.forEach { it.onAttachTo(world, inDay) }
    }

    override fun onDetachTo(world: World) {
        entities.forEach { it.onDetachTo(world) }
    }

    override fun onSwitchDay(world: World, inDay: Boolean) {
        entities.forEach { it.onSwitchDay(world, inDay) }
    }
}