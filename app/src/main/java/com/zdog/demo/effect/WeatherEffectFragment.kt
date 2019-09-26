package com.zdog.demo.effect

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.zdog.demo.R
import com.zdog.demo.core.BaseFragment
import kotlinx.android.synthetic.main.fragment_weather_effect.*

class WeatherEffectFragment : BaseFragment() {
    companion object {
        fun newInstance() = WeatherEffectFragment()
    }

    override fun layoutId() = R.layout.fragment_weather_effect

    private val adapter = WeatherEffectAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }

    private fun initializeView() {
        pages.adapter = adapter
        pages.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                effect.type = adapter.getEffect(position)
            }
        })
        effect.type = adapter.getEffect(pages.currentItem)
        day.setOnClickListener {
            effect.inDay = !effect.inDay
            renderDayText(effect.inDay)
        }
        renderDayText(effect.inDay)
        rotate.setOnClickListener {
            effect.rotate = !effect.rotate
            renderRotateText(effect.rotate)
        }
        renderRotateText(effect.rotate)
    }

    private fun renderDayText(inDay: Boolean) {
        day.text = if (inDay) {
            getString(R.string.text_night)
        } else {
            getString(R.string.text_day)
        }
    }

    private fun renderRotateText(rotating: Boolean) {
        rotate.text = if (rotating) {
            getString(R.string.text_stop_rotating)
        } else {
            getString(R.string.text_rotate)
        }
    }
}