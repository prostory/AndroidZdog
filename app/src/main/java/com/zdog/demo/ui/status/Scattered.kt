package com.zdog.demo.ui.status

import com.zdog.demo.ui.shapes.Colors.white
import com.zdog.demo.ui.shapes.Cloud
import com.zdog.library.render.anchor
import com.zdog.library.render.set

open class Scattered(private val showers: Boolean = false) : Sunny(60f) {
    protected val cloud = anchor {
        translate { x = -5f; y = 30f }
        addTo = sky
    }.also {
        Cloud().set {
            color = white.color
            scale(1.2f)
            addTo = it
        }.shader(5f) {
            translate { z = -4f }
            color = "#ddd"
            alpha = 0.3f
        }
    }

    init {
        sky.translate { x = -60f; y = -40f }
        if (showers) {
            moon.translate { x = -15f; y = -25f; z = -8f }
        }
    }

    override fun onDynamic(inDay: Boolean) {
        if (showers) {
            super.onDynamic(inDay)
        }
    }

    override fun onStatic(inDay: Boolean) {
        super.onStatic(inDay)
        cloud.translate { x = -5f; y = 30f }
    }

    override fun switchDay(inDay: Boolean) {
        if (showers) {
            super.switchDay(inDay)
        }
    }
}