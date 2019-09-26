package com.zdog.demo.ui.effects.sunny

import com.zdog.demo.ui.effects.SkyTheme
import com.zdog.demo.ui.shapes.Colors.*
import com.zdog.library.render.Colour

internal data class Theme(
    override val sky: Colour,
    override val background1: Colour,
    override val background2: Colour,
    override val background3: Colour,
    override val sun: Colour,
    override val cloud: Colour,

    val leaf1: Colour,
    val trunk1: Colour,
    val mountain1: Colour,

    val leaf2: Colour,
    val trunk2: Colour,
    val mountain2: Colour,

    val leaf3: Colour,
    val trunk3: Colour,
    val mountain3: Colour
): SkyTheme() {
    companion object {
        private val day by lazy {
            Theme(
                sky = midday.colour,
                background1 = blue1.colour,
                background2 = blue2.colour,
                background3 = blue3.colour,
                sun = gold1.colour,
                cloud = white.colour,

                leaf1 = green1.colour,
                trunk1 = brown.colour,
                mountain1 = black1.colour,

                leaf2 = green2.colour,
                trunk2 = brown.colour,
                mountain2 = black2.colour,

                leaf3 = green3.colour,
                trunk3 = brown.colour,
                mountain3 = black3.colour
            )
        }

        private val night by lazy {
            Theme(
                sky = midnight.colour,
                background1 = magenta.colour,
                background2 = amber.colour,
                background3 = gold1.colour,
                sun = white.colour,
                cloud = amber.colour,

                leaf1 = magenta.colour,
                trunk1 = magenta.colour,
                mountain1 = magenta.colour,

                leaf2 = eggplant.colour,
                trunk2 = eggplant.colour,
                mountain2 = eggplant.colour,

                leaf3 = midnight.colour,
                trunk3 = midnight.colour,
                mountain3 = midnight.colour
            )
        }

        fun get(inDay: Boolean) = if (inDay) day else night
    }
}