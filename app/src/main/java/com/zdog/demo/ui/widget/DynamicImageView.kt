package com.zdog.demo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatImageView
import com.zdog.demo.ui.shapes.World

class DynamicImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var isRunning = false

    fun start() {
        if (drawable is World) {
            (drawable as World).start()
        }
    }

    fun start(delay: Long = 0) {
        if (drawable is World) {
            (drawable as World).start(delay)
        }
    }

    fun resume() {
        if (drawable is World) {
            (drawable as World).resume()
        }
    }

    fun pause() {
        if (drawable is World) {
            (drawable as World).pause()
        }
    }

    fun cancel() {
        if (drawable is World) {
            isRunning = (drawable as World).isRunning()
            (drawable as World).cancel()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isRunning) {
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancel()
    }

    override fun setVisibility(visibility: Int) {
        if (this.visibility != visibility) {
            if (visibility == View.VISIBLE) {
                if (isRunning) {
                    start()
                }
            } else {
                cancel()
            }
        }

        super.setVisibility(visibility)
    }
}