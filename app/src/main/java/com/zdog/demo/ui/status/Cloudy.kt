package com.zdog.demo.ui.status

import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import com.zdog.demo.ui.shapes.Colors.white
import com.zdog.demo.ui.shapes.Cloud
import com.zdog.library.render.anchor
import com.zdog.library.render.set

class Cloudy : Status() {
    init {
        anchor {
            translate { x = -80f; y = 40f }
            addTo = illo
        }.also {
            Cloud().set {
                color = white.color
                addTo = it
            }.shader(5f) {
                translate { z = -4f }
                color = "#ddd"
                alpha = 0.3f
            }
        }

        Cloud().set {
            translate { x = -70f; y = 10f; z = -8f }
            scale(1.2f)
            color = white.color
            addTo = illo
        }
    }

    override fun onDynamic(inDay: Boolean) {
        super.onDynamic(inDay)
        play(
            illo.children[0].translateBy(10f, 10f) {
                repeatMode = REVERSE
                repeatCount = INFINITE
                duration = 1000
            }
        ).with(
            illo.children[1].translateBy(10f, 10f) {
                repeatMode = REVERSE
                repeatCount = INFINITE
                duration = 1000
                startDelay = 300
            }
        )
    }
}