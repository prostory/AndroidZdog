package com.zdog.library.render

import android.graphics.Path

data class Segment(
    var start: Float = 0f,
    var end: Float = 1f
) {
    private var onBegin: ((Path)-> Unit)? = null

    fun unbroken() = start == 0f && end == 1f

    fun set(start: Float, end: Float, block: ((Path)-> Unit)? = null): Segment {
        this.start = start
        this.end   = end
        this.onBegin = block
        return this
    }

    fun reset(): Segment {
        this.start = 0f
        this.end = 1f
        this.onBegin = null
        return this
    }

    fun begin(path: Path) {
        onBegin?.invoke(path)
    }
}