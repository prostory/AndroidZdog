package com.zdog.demo.ui.effects.cloud

import com.zdog.demo.ui.shapes.Colors.amber
import com.zdog.demo.ui.shapes.Colors.blue1
import com.zdog.demo.ui.shapes.Colors.blue2
import com.zdog.demo.ui.shapes.Colors.blue3
import com.zdog.demo.ui.shapes.Colors.eggplant
import com.zdog.demo.ui.shapes.Colors.gold1
import com.zdog.demo.ui.shapes.Colors.gray1
import com.zdog.demo.ui.shapes.Colors.gray2
import com.zdog.demo.ui.shapes.Colors.green1
import com.zdog.demo.ui.shapes.Colors.green2
import com.zdog.demo.ui.shapes.Colors.green3
import com.zdog.demo.ui.shapes.Colors.magenta
import com.zdog.demo.ui.shapes.Colors.midday
import com.zdog.demo.ui.shapes.Colors.midnight
import com.zdog.demo.ui.shapes.Colors.white
import com.zdog.demo.ui.effects.SkyTheme
import com.zdog.library.render.Colour

internal data class Theme(
    override val sky: Colour,
    override val background1: Colour,
    override val background2: Colour,
    override val background3: Colour,
    override val sun: Colour,
    override val cloud: Colour,

    val grass1: Colour,
    val fan1:Colour,
    val body1: Colour,

    val grass2: Colour,
    val fan2:Colour,
    val body2: Colour,

    val grass3: Colour,
    val fan3:Colour,
    val body3: Colour
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

                grass1 = green1.colour,
                fan1 = gray1.colour,
                body1 = gray2.colour,

                grass2 = green2.colour,
                fan2 = gray1.colour,
                body2 = gray2.colour,

                grass3 = green3.colour,
                fan3 = gray1.colour,
                body3 = gray2.colour
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

                grass1 = magenta.colour,
                fan1 = magenta.colour,
                body1 = magenta.colour,

                grass2 = eggplant.colour,
                fan2 = eggplant.colour,
                body2 = eggplant.colour,

                grass3 = midnight.colour,
                fan3 = midnight.colour,
                body3 = midnight.colour
            )
        }

        fun get(inDay: Boolean) = if (inDay) day else night
    }
}