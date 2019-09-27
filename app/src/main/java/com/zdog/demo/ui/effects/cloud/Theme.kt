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

internal data class Theme(
    override val sky: String,
    override val background1: String,
    override val background2: String,
    override val background3: String,
    override val sun: String,
    override val cloud: String,

    val grass1: String,
    val fan1:String,
    val body1: String,

    val grass2: String,
    val fan2:String,
    val body2: String,

    val grass3: String,
    val fan3:String,
    val body3: String
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

                grass1 = green1.color,
                fan1 = gray1.color,
                body1 = gray2.color,

                grass2 = green2.color,
                fan2 = gray1.color,
                body2 = gray2.color,

                grass3 = green3.color,
                fan3 = gray1.color,
                body3 = gray2.color
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

                grass1 = magenta.color,
                fan1 = magenta.color,
                body1 = magenta.color,

                grass2 = eggplant.color,
                fan2 = eggplant.color,
                body2 = eggplant.color,

                grass3 = midnight.color,
                fan3 = midnight.color,
                body3 = midnight.color
            )
        }

        fun get(inDay: Boolean) = if (inDay) day else night
    }
}