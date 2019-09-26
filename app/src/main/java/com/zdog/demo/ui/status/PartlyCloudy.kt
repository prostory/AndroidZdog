package com.zdog.demo.ui.status

import android.animation.ValueAnimator
import android.view.animation.CycleInterpolator

class PartlyCloudy : Scattered(true) {
    init {
        sky.translate { y = -10f }
    }

    override fun onDynamic(inDay: Boolean) {
        super.onDynamic(inDay)

        play(
            cloud.translateBy(5f, 5f) {
                repeatCount = ValueAnimator.INFINITE
                interpolator = CycleInterpolator(1.0f)
                duration = 2000
            }
        )
    }
}