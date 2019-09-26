package com.zdog.demo.ui.effects

import com.zdog.demo.ui.shapes.World

open class Entity {
    open fun onAttachTo(world: World, inDay: Boolean) {}

    open fun onDetachTo(world: World) {}

    open fun onSwitchDay(world: World, inDay: Boolean) {}
}