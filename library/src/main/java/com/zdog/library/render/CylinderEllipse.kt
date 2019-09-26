package com.zdog.library.render

class CylinderEllipse : Ellipse() {
    override fun <T : Anchor> copyGraph(block: (T.() -> Unit)?): T {
        throw IllegalAccessException("Couldn't copy graph for CylinderEllipse")
    }
}