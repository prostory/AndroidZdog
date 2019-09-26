package com.zdog.demo.ui.effects

import com.zdog.demo.ui.shapes.World

const val layerSpace = 56f

class Effect : World() {
    private var weather: Entity? = null
    val sky = Sky(this)

    fun switch(weather: Entity, inDay: Boolean) {
        this.weather?.onDetachTo(this)
        this.weather = weather
        weather.onAttachTo(this, inDay)
        weather.onSwitchDay(this, inDay)
        invalidateSelf()
    }

    fun switchDay(inDay: Boolean) {
        this.weather?.onSwitchDay(this, inDay)
        invalidateSelf()
    }
}