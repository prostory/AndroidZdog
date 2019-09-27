package com.zdog.demo.ui.effects.rain

import com.zdog.demo.ui.effects.SkyTheme
import com.zdog.demo.ui.shapes.Colors.*

internal data class Theme(
    override val sky: String,
    override val background1: String,
    override val background2: String,
    override val background3: String,
    override val sun: String,
    override val cloud: String,

    val sea1: String,
    val sea2: String,
    val sea3: String
): SkyTheme() {
    companion object {
        private val day by lazy {
            Theme(
                sky = gray6.color,
                background1 = gray5.color,
                background2 = gray4.color,
                background3 = gray3.color,
                sun = gold1.color,
                cloud = gray4.color,

                sea1 = blue4.color,
                sea2 = blue5.color,
                sea3 = blue6.color
            )
        }

        private val night by lazy {
            Theme(
                sky = "#301B41",
                background1 = "#413252",
                background2 = "#534964",
                background3 = "#656076",
                sun = white.color,
                cloud = "#665D72",

                sea1 = "#3F3759",
                sea2 = "#37264C",
                sea3 = "#301B41"
            )
        }

        fun get(inDay: Boolean) = if (inDay) day else night
    }
}