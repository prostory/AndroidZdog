package com.zdog.library.render

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.condor.weather.library.extension.empty
import com.condor.weather.library.extension.sp

class Text : Anchor() {
    companion object val paint = Paint().apply {
        isAntiAlias = true
    }

    var text = String.empty()
    var textSize = 12.sp
    var typeface = if (VERSION.SDK_INT >= VERSION_CODES.P) {
        Typeface.create(Typeface.DEFAULT, 300, false)
    } else {
        Typeface.DEFAULT
    }
    var centerX: Float = 0f
        private set
    var centerY: Float = 0f
        private set

    override fun onCreate() {
        super.onCreate()
        updateDimention()
    }

    fun updateDimention() {
        paint.textSize = textSize
        paint.typeface = typeface
        centerX = paint.measureText(text) / 2
        centerY = with(paint.fontMetrics) { bottom - top } / 4
    }

    override fun render(renderer: Renderer) {
        if (text.isEmpty()) {
            return
        }
        renderer.save()
        renderer.translate(translate.x, translate.y)
        renderer.scale(scale.x, scale.y)
        renderer.rotate(rotate.z)
        renderer.text(text, textSize, centerX, centerY, colour, renderAlpha, typeface)
        renderer.restore()
    }

    override fun copy(): Text {
        return copy(Text())
    }

    override fun copy(shape: Anchor): Text {
        return (super.copy(shape) as Text).also {
            it.text = text
            it.textSize = textSize
            it.typeface = typeface
        }
    }
}