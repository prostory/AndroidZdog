package com.zdog.demo.effect

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.zdog.demo.R
import com.zdog.demo.extention.inflate
import com.zdog.demo.ui.effects.EffectType
import kotlinx.android.synthetic.main.fragment_weather_effect_item.view.*

class WeatherEffectAdapter : PagerAdapter() {
    private val effects = listOf(
            EffectType.Sunny,
            EffectType.Cloud,
            EffectType.Rain
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return effects.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = container.inflate(R.layout.fragment_weather_effect_item)
        with(item) {
            effectType.text = effects[position].title
        }
        container.addView(item)
        return item
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun getEffect(position: Int) = effects[position]

}