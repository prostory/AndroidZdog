package com.zdog.demo.ui.status

import android.animation.ValueAnimator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.zdog.demo.ui.shapes.Colors
import com.zdog.demo.ui.shapes.Wind
import com.zdog.library.render.set

open class Windy : Scattered() {
    protected val wind = Wind().set {
        color = Colors.wind.colour
        addTo = illo
        alpha = 0f
        scale(0.7f)
        translate { y = 70f; z = -8f }
    }

    override fun onDynamic(inDay: Boolean) {
        super.onDynamic(inDay)
        play {
            repeatCount = ValueAnimator.INFINITE
            repeatMode  = ValueAnimator.REVERSE
            interpolator = FastOutSlowInInterpolator()
            duration = 1500

            onReset {
                wind.alpha = 0.2f
                wind.segment = null
            }

            update {
                wind.alpha = 0.2f + 0.8f * it
                wind.updateSegment(0f, it)
            }
        }.with(
            cloud.translateBy(x=20f) {
                repeatCount = ValueAnimator.INFINITE
                repeatMode  = ValueAnimator.REVERSE
                interpolator = FastOutSlowInInterpolator()
                duration = 1500
            }
        )
    }

    override fun onStatic(inDay: Boolean) {
        super.onStatic(inDay)
        wind.alpha = 1f
        wind.segment = null
    }
}