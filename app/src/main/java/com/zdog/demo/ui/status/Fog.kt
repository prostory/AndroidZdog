package com.zdog.demo.ui.status

import android.animation.ValueAnimator
import com.zdog.demo.ui.shapes.Colors.rain
import com.zdog.library.render.combine
import com.zdog.library.render.line
import com.zdog.library.render.move
import com.zdog.library.render.shape

class Fog : Scattered() {
    private val fog1 = combine {
        addTo = illo
        translate { x = -5f; y = 35f; z = -8f }
        color = rain.color
    }.also {
        shape {
            path(move(x=-85f), line(x=-55f))
            stroke = 8f
            addTo = it
        }
        shape {
            path(move(x=-40f), line(x=85f))
            stroke = 8f
            addTo = it
        }
    }
    private val fog2 = combine {
        addTo = illo
        translate { x = 5f; y = 50f; z = -8f }
        color = rain.color
    }.also {
        shape {
            path(move(x=-85f), line(x=-20f))
            stroke = 8f
            addTo = it
        }
        shape {
            path(move(x=-5f), line(x=60f))
            stroke = 8f
            addTo = it
        }
        shape {
            path(move(x=75f), line(x=85f))
            stroke = 8f
            addTo = it
        }
    }
    private val fog3 = combine {
        addTo = illo
        translate { x = -5f; y = 65f; z = -8f }
        color = rain.color
    }.also {
        shape {
            path(move(x=-85f), line(x=-75f))
            stroke = 8f
            addTo = it
        }
        shape {
            path(move(x=-60f), line(x=-40f))
            stroke = 8f
            addTo = it
        }
        shape {
            path(move(x=-25f), line(x=30f))
            stroke = 8f
            addTo = it
        }
        shape {
            path(move(x=45f), line(x=85f))
            stroke = 8f
            addTo = it
        }
    }
    private val fog4 = combine {
        addTo = illo
        translate { x = 5f; y = 80f; z = -8f }
        color = rain.color
    }.also {
        shape {
            path(move(x=-85f), line(x=-65f))
            stroke = 8f
            addTo = it
        }
        shape {
            path(move(x=-50f), line(x=0f))
            stroke = 8f
            addTo = it
        }
        shape {
            path(move(x=15f), line(x=30f))
            stroke = 8f
            addTo = it
        }
        shape {
            path(move(x=45f), line(x=55f))
            stroke = 8f
            addTo = it
        }
        shape {
            path(move(x=70f), line(x=85f))
            stroke = 8f
            addTo = it
        }
    }
    private val fog5 = combine {
        addTo = illo
        translate { x = -5f; y = 95f; z = -8f }
        color = rain.color
    }.also {
        shape {
            path(move(x=-85f), line(x=-70f))
            stroke = 8f
            addTo = it
        }
        shape {
            path(move(x=-55f), line(x=5f))
            stroke = 8f
            addTo = it
        }
        shape {
            path(move(x=20f), line(x=85f))
            stroke = 8f
            addTo = it
        }
    }

    override fun onDynamic(inDay: Boolean) {
        super.onDynamic(inDay)
        play(
            fog1.translateBy(x = 10f) {
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                duration = 2000
            }
        ).with(
            fog2.translateBy(x = -10f) {
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                duration = 2000
                startDelay = 200
            }
        ).with(
            fog3.translateBy(x = 10f) {
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                duration = 2000
                startDelay = 400
            }
        ).with(
            fog4.translateBy(x = -10f) {
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                duration = 2000
                startDelay = 600
            }
        ).with(
            fog5.translateBy(x = 10f) {
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                duration = 2000
                startDelay = 800
            }
        ).with(
            fog1.alphaTo(0f) {
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                duration = 4000
                startDelay = 1400
            }
        ).with(
            fog2.alphaTo(0f) {
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                duration = 3000
                startDelay = 800
            }
        ).with(
            fog3.alphaTo(0f) {
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                duration = 4500
                startDelay = 1000
            }
        ).with(
            fog4.alphaTo(0f) {
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                duration = 2500
                startDelay = 1600
            }
        ).with(
            fog5.alphaTo(0f) {
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                duration = 3500
                startDelay = 2000
            }
        )
    }
}