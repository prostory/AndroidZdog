package com.zdog.demo.ui.status

import com.zdog.demo.ui.shapes.Dropable
import com.zdog.library.render.Anchor
import com.zdog.library.render.anchor

open class Drop(vararg components: Anchor, showers: Boolean = false)
    : Scattered(showers) {
    protected val drops = anchor {
        addTo = illo
        components.forEach {
            addChild(it)
        }
    }

    init {
        val divider = 160f / (drops.children.size+1)
        drops.translate { x = -80f; y = 10f; z = -8f }
        drops.children.forEachIndexed { index, drop ->
            drop.translate { x = divider * (index + 1) }
        }
    }

    override fun onDynamic(inDay: Boolean) {
        super.onDynamic(inDay)
        sky.translate { y = -40f }
        drops.translate { y = 10f }
        drops.children.forEachIndexed { index, drop ->
            if (drop is Dropable) {
                play(drop.drop(this, index.toFloat() / drops.children.size))
            }
        }
    }

    override fun onStatic(inDay: Boolean) {
        super.onStatic(inDay)
        sky.translate { y = -20f }
        drops.translate { y = 60f }
        drops.children.forEach {
            if (it is Dropable) {
                it.toStatic()
            }
        }
    }
}