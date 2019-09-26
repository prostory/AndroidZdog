package com.zdog.demo.ui.effects.sunny

import com.zdog.demo.ui.effects.Entity
import com.zdog.demo.ui.effects.Sky
import com.zdog.demo.ui.shapes.World
import com.zdog.library.render.attachTo

class Sunny(sky: Sky): Entity() {
    private val entities = mutableListOf<Entity>()

    init {
        SunnySky(sky).attachTo(entities)
        Mountain1().attachTo(entities)
        Mountain2().attachTo(entities)
        Mountain3().attachTo(entities)
        Glowworm().attachTo(entities)
        Clouds().attachTo(entities)
        Stars().attachTo(entities)
        Bird().attachTo(entities)
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