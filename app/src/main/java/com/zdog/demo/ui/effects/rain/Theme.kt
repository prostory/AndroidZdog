package com.zdog.demo.ui.effects.rain

import com.zdog.demo.ui.effects.SkyTheme
import com.zdog.demo.ui.shapes.Colors.*
import com.zdog.library.render.Colour
import com.zdog.library.render.toColour

internal data class Theme(
    override val sky: Colour,
    override val background1: Colour,
    override val background2: Colour,
    override val background3: Colour,
    override val sun: Colour,
    override val cloud: Colour,

    val sea1: Colour,
    val sea2: Colour,
    val sea3: Colour
): SkyTheme() {
    companion object {
        private val day by lazy {
            Theme(
                sky = gray6.colour,
                background1 = gray5.colour,
                background2 = gray4.colour,
                background3 = gray3.colour,
                sun = gold1.colour,
                cloud = gray4.colour,

                sea1 = blue4.colour,
                sea2 = blue5.colour,
                sea3 = blue6.colour
            )
        }

        private val night by lazy {
            Theme(
                sky = "#301B41".toColour(),
                background1 = "#413252".toColour(),
                background2 = "#534964".toColour(),
                background3 = "#656076".toColour(),
                sun = white.colour,
                cloud = "#665D72".toColour(),

                sea1 = "#3F3759".toColour(),
                sea2 = "#37264C".toColour(),
                sea3 = "#301B41".toColour()
            )
        }

        fun get(inDay: Boolean) = if (inDay) day else night
    }
}