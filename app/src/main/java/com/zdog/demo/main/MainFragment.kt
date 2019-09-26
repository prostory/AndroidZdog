package com.zdog.demo.main

import android.os.Bundle
import android.view.View
import com.zdog.demo.R
import com.zdog.demo.core.BaseFragment
import com.zdog.demo.effect.WeatherEffectFragment
import com.zdog.demo.extention.addFragment
import com.zdog.demo.extention.clicks
import com.zdog.demo.shapes.BasicShapesFragment
import com.zdog.demo.status.WeatherStatusFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    override fun layoutId() = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }

    private fun initializeView() {
        basicShapes.clicks {
            addFragment(BasicShapesFragment.newInstance()) {
                addToBackStack(null)
            }
        }
        weatherStatus.clicks {
            addFragment(WeatherStatusFragment.newInstance()) {
                addToBackStack(null)
            }
        }
        weatherEffect.clicks {
            addFragment(WeatherEffectFragment.newInstance()) {
                addToBackStack(null)
            }
        }
    }
}