package com.zdog.demo.status

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.zdog.demo.R
import com.zdog.demo.core.BaseFragment
import com.zdog.demo.status.WeatherStatusAdapter.Status
import com.zdog.demo.status.WeatherStatusAdapter.Status.*
import kotlinx.android.synthetic.main.fragment_weather_status.*

class WeatherStatusFragment : BaseFragment() {
    companion object {
        fun newInstance() = WeatherStatusFragment()
    }

    private val adapter by lazy { WeatherStatusAdapter() }

    override fun layoutId() = R.layout.fragment_weather_status

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }

    private fun initializeView() {
        renderDayText(adapter.status)
        renderDynamicText(adapter.status)
        day.setOnClickListener {
            adapter.status = when(adapter.status) {
                DynamicDay-> DynamicNight
                DynamicNight-> DynamicDay
                StaticDay-> StaticNight
                StaticNight -> StaticDay
            }
            renderDayText(adapter.status)
        }
        dynamic.setOnClickListener {
            adapter.status = when(adapter.status) {
                DynamicDay-> StaticDay
                DynamicNight-> StaticNight
                StaticDay-> DynamicDay
                StaticNight-> DynamicNight
            }
            renderDynamicText(adapter.status)
        }
        iconList.adapter = adapter
        iconList.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    private fun renderDayText(status: Status) {
        day.text = if (status == DynamicDay || status == StaticDay) {
            getString(R.string.text_night)
        } else {
            getString(R.string.text_day)
        }
    }

    private fun renderDynamicText(status: Status) {
        dynamic.text = if (status == DynamicDay || status == DynamicNight) {
            getString(R.string.text_static)
        } else {
            getString(R.string.text_dynamic)
        }
    }
}