package com.zdog.library.render

import android.graphics.Paint.Cap.BUTT
import android.graphics.Paint.Cap.ROUND

class CylinderGroup : Group() {
    var stroke = 1f
    var fill = true

    private lateinit var pathCommands: List<PathCommand>
    internal lateinit var frontBase: Ellipse
    internal lateinit var rearBase: Ellipse

    init {
        updateSort = true
    }

    override fun onCreate() {
        super.onCreate()
        pathCommands = listOf(
            move(),
            line()
        )
    }

    override fun render(renderer: Renderer) {
        renderCylinderSurface(renderer)
        super.render(renderer)
    }

    private fun renderCylinderSurface(renderer: Renderer) {
        if (!visible) {
            return
        }
        val scale = frontBase.renderNormal.magnitude()
        val strokeWidth = frontBase.diameter * scale + frontBase.stroke
        pathCommands[0].renderPoint().set(frontBase.renderOrigin)
        pathCommands[1].renderPoint().set(rearBase.renderOrigin)

        renderer.setLineCap(BUTT)
        renderer.renderPath(pathCommands)
        renderer.stroke(true, colour, strokeWidth, renderAlpha)
        renderer.end()
        renderer.setLineCap(ROUND)
    }

    override fun copy(): CylinderGroup {
        return copy(CylinderGroup())
    }

    override fun copy(shape: Anchor): CylinderGroup {
        return (super.copy(shape) as CylinderGroup).also {
            it.stroke = stroke
            it.fill = fill
        }
    }

    override fun <T : Anchor> copyGraph(block: (T.() -> Unit)?): T {
        throw IllegalAccessException("Couldn't copy graph for CylinderGroup")
    }
}