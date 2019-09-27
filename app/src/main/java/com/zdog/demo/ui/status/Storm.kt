package com.zdog.demo.ui.status

import com.zdog.demo.ui.shapes.Dropable
import com.zdog.demo.ui.shapes.Raindrop
import com.zdog.library.render.Anchor
import com.zdog.library.render.TAU

open class Storm(vararg components: Anchor =
    arrayOf(
        Raindrop(),
        Raindrop()
    )) : Windy() {
    private val drops = components.toList()

    init {
        wind.apply {
            scale(0.5f)
            translate { x=-20f }
        }
        drops.forEach {
            it.translate { x= 20f; y = 10f; z = -8f }
            illo.addChild(it)
        }
    }

    override fun onDynamic(inDay: Boolean) {
        sky.translate { y = -40f }
        wind.translate { x = -20f; y = 70f }
        drops.forEach {
            it.translate { x = 20f; y = 10f }
            it.rotate { z = 0f }
        }
        super.onDynamic(inDay)
        drops.forEachIndexed { index, drop ->
            if (drop is Dropable) {
                play(drop.drop(this, index.toFloat()/drops.size,
                    80f-index*20f))
            }
        }
    }

    override fun onStatic(inDay: Boolean) {
        sky.translate { y = -20f }
        wind.translate { x = -30f; y = 80f }
        super.onStatic(inDay)
        drops.forEachIndexed { index, drop ->
            drop.translate { x = 20f + index * 30f ;y = 60f + index * 30f }
            drop.rotate { z=- (TAU/24).toFloat() * (index+1) }
            if (drop is Dropable) {
                drop.toStatic()
            }
        }
    }
}