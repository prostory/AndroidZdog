package com.zdog.library.render

class BoxRect : Rect() {
    override fun <T : Anchor> copyGraph(block: (T.() -> Unit)?): T {
        throw IllegalAccessException("Couldn't copy graph for BoxRect")
    }
}