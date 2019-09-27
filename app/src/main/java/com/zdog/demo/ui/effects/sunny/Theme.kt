package com.zdog.demo.ui.effects.sunny

import com.zdog.demo.ui.effects.SkyTheme
import com.zdog.demo.ui.shapes.Colors.*

internal data class Theme(
    override val sky: String,
    override val background1: String,
    override val background2: String,
    override val background3: String,
    override val sun: String,
    override val cloud: String,

    val leaf1: String,
    val trunk1: String,
    val mountain1: String,

    val leaf2: String,
    val trunk2: String,
    val mountain2: String,

    val leaf3: String,
    val trunk3: String,
    val mountain3: String
): SkyTheme() {
    companion object {
        private val day by lazy {
            Theme(
                sky = midday.color,
                background1 = blue1.color,
                background2 = blue2.color,
                background3 = blue3.color,
                sun = gold1.color,
                cloud = white.color,

                leaf1 = green1.color,
                trunk1 = brown.color,
                mountain1 = black1.color,

                leaf2 = green2.color,
                trunk2 = brown.color,
                mountain2 = black2.color,

                leaf3 = green3.color,
                trunk3 = brown.color,
                mountain3 = black3.color
            )
        }

        private val night by lazy {
            Theme(
                sky = midnight.color,
                background1 = magenta.color,
                background2 = amber.color,
                background3 = gold1.color,
                sun = white.color,
                cloud = amber.color,

                leaf1 = magenta.color,
                trunk1 = magenta.color,
                mountain1 = magenta.color,

                leaf2 = eggplant.color,
                trunk2 = eggplant.color,
                mountain2 = eggplant.color,

                leaf3 = midnight.color,
                trunk3 = midnight.color,
                mountain3 = midnight.color
            )
        }

        fun get(inDay: Boolean) = if (inDay) day else night
    }
}