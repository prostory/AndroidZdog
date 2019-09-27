package com.zdog.demo.ui.status

import android.animation.ValueAnimator
import android.view.animation.CycleInterpolator
import com.zdog.demo.ui.shapes.Colors.wind
import com.zdog.demo.ui.shapes.tornado

class Tornado : Scattered() {
    private val offsets = arrayOf(0f, 1f, 11f, 22f, 10f)
    private val tornado = tornado {
        translate { y = 110f; z = -8f }
        color = wind.colour
        addTo = illo
    }

    override fun onDynamic(inDay: Boolean) {
        super.onDynamic(inDay)
        play {
            repeatCount = ValueAnimator.INFINITE
            interpolator = CycleInterpolator(1.0f)
            duration = 4000

            val src = cloud.translate.copy()

            onReset {
                cloud.translate.set(src)
                tornado.translate { x = 0f }
                tornado.children.forEach {
                    it.translate { x = 0f }
                }
            }

            update {
                cloud.translate { x = src.x + 20 * it }
                tornado.translate { x = 40 * it }
                tornado.children.forEachIndexed { index, shape ->
                    shape.translate { x = offsets[index] * it }
                }
            }
        }
    }

    override fun onStatic(inDay: Boolean) {
        super.onStatic(inDay)
        tornado.translate { x = -10f }
        tornado.children.forEachIndexed { index, shape ->
            shape.translate { x = offsets[index] }
        }
    }
}
