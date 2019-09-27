package com.zdog.demo.ui.status

import android.animation.ValueAnimator
import android.view.animation.CycleInterpolator
import com.zdog.demo.ui.shapes.Colors.brown
import com.zdog.demo.ui.shapes.Moon
import com.zdog.demo.ui.shapes.Sun
import com.zdog.library.render.TAU
import com.zdog.library.render.anchor
import com.zdog.library.render.copy
import com.zdog.library.render.set

open class Sunny(diameter: Float=100f): Status() {
    protected val sky = anchor {
        addTo = illo
    }

    protected val moon by lazy {
        anchor().also {
            Moon().set {
                radius = diameter/2
                color = brown.colour
                addTo = it
            }.copy {
                radius = diameter/2+5
                translate { z = -12f }
                alpha = 0.3f
            }
        }
    }

    protected val sun by lazy {
        Sun(diameter).set {
            translate { z = -8f }
        }
    }


    override fun onDynamic(inDay: Boolean) {
        super.onDynamic(inDay)
        if (inDay) {
            sun.toDynamic(this)
        } else {
            play(
                moon.rotateTo(z = (TAU / 36).toFloat()) {
                    repeatCount = ValueAnimator.INFINITE
                    interpolator = CycleInterpolator(1.0f)
                    duration = 3000
                }
            )
        }
    }

    override fun onStatic(inDay: Boolean) {
        super.onStatic(inDay)
        if (inDay) {
            sun.toStatic(this)
        }
    }

    override fun switchDay(inDay: Boolean) {
        super.switchDay(inDay)
        if (inDay) {
            sky.addChild(sun)
            moon.remove()
        } else {
            sky.addChild(moon)
            sun.remove()
        }
    }

}