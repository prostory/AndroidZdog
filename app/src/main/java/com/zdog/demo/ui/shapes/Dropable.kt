package com.zdog.demo.ui.shapes

import android.animation.ValueAnimator
import com.zdog.demo.ui.status.Status

interface Dropable {
    fun drop(target: Status, offset: Float, acceleration: Float=0f): ValueAnimator
    fun toStatic() {}
}