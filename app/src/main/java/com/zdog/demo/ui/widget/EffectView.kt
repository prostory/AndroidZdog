package com.zdog.demo.ui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatImageView
import com.zdog.demo.ui.effects.Effect
import com.zdog.demo.ui.effects.EffectType
import com.zdog.demo.ui.effects.cloud.Cloud
import com.zdog.demo.ui.effects.rain.Rain
import com.zdog.demo.ui.effects.sunny.Sunny

class EffectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0
): AppCompatImageView(context, attrs, defStyleAttr) {
    private val effect = Effect()
    private val weathers = hashMapOf(
        EffectType.Sunny to Sunny(effect.sky),
        EffectType.Cloud to Cloud(effect.sky),
        EffectType.Rain  to Rain(effect.sky)
    )
    var type: EffectType = EffectType.Sunny
        set(value) {
            field = value
            weathers[type]?.let {
                effect.switch(it, inDay)
            }
        }

    var inDay: Boolean = true
        set(value) {
            field = value
            effect.switchDay(field)
        }
    var rotate: Boolean = false
        set(value) {
            field = value
            effect.rotate(field)
        }

    init {
        setImageDrawable(effect)
    }
}