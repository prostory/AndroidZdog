package com.zdog.demo.ui.status

import android.animation.ValueAnimator
import android.view.animation.CycleInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.zdog.demo.ui.shapes.Question
import com.zdog.library.render.TAU
import com.zdog.library.render.set

class Unknown : Scattered() {
    private val question = Question().set {
        addTo = illo
        translate { y = 50f }
    }

    override fun onDynamic(inDay: Boolean) {
        super.onDynamic(inDay)
        question.updateSegment(0f, 0f)
        play {
            interpolator = FastOutSlowInInterpolator()
            duration = 1000

            onReset {
                question.updateSegment(0f, 0f)
            }

            update {
                question.updateSegment(0f, it)
            }
        }.before(
            question.rotateBy(z= (TAU/36).toFloat()) {
                repeatCount = ValueAnimator.INFINITE
                duration = 1000
                interpolator = CycleInterpolator(1.0f)
            }
        )
    }

    override fun onStatic(inDay: Boolean) {
        super.onStatic(inDay)
        question.segment = null
    }
}