package com.zdog.library.render

data class ShaderLayer(
    val radius: Float = 0f,
    val dx: Float = 0f,
    val dy: Float = 0f,
    val color: Int = 0
) {
    companion object {
        fun empty() = ShaderLayer()
    }
}