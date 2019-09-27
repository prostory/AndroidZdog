package com.zdog.library.render

class Hemisphere: Ellipse() {
    init {
        fill = true
    }
    private lateinit var apex: Anchor
    private lateinit var renderCentroid: Vector

    override fun onCreate() {
        super.onCreate()
        apex = anchor {
            addTo = this
            translate(z = diameter/2)
        }
        renderCentroid = vector()
    }

    override fun updateSortValue() {
        renderCentroid.set(renderOrigin)
            .lerp(apex.renderOrigin, 3/8f)
        sortValue = renderCentroid.z
    }

    override fun render(renderer: Renderer) {
        renderDemo(renderer)
        super.render(renderer)
    }

    private fun renderDemo(renderer: Renderer) {
        if (!visible) {
            return
        }

        val contourAngle = Math.atan2(renderNormal.y.toDouble(), renderNormal.x.toDouble())
        val demoRadius = diameter/2 * renderNormal.magnitude()
        val x = renderOrigin.x
        val y = renderOrigin.y

        val startAngle = contourAngle + TAU/4
        val endAnchor  = contourAngle - TAU/4

        renderer.begin()
        renderer.arc(x, y, demoRadius, startAngle.toFloat(), endAnchor.toFloat())
        renderer.stroke(stroke > 0, colour, stroke, renderAlpha)
        renderer.fill(fill, colour, renderAlpha)
        renderer.end()
    }
}