package com.zdog.demo.ui.shapes

import com.zdog.library.render.color

enum class Colors(val color: String) {
    midnight("#313"),
    eggplant("#525"),
    magenta("#936"),
    amber("#D65"),
    gold1("#FD4"),
    gold2("#F90"),
    white("#FFF"),
    rain("#5DE"),
    dust("#9AD"),
    wind("#BDD"),
    brown("#9AD"),

    gray1("#DDD"),
    gray2("#BBB"),
    gray3("#AAB"),
    gray4("#99A"),
    gray5("#889"),
    gray6("#778"),

    midday("#5AE"),

    blue1("#7AC"),
    blue2("#9AB"),
    blue3("#AAA"),
    blue4("#579"),
    blue5("#469"),
    blue6("#26A"),

    green1("#BF5"),
    green2("#7F0"),
    green3("#6D1"),
    black1("#544"),
    black2("#444"),
    black3("#334"),

    shader("#60222222");

    val colour = color.color
}