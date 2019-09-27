package com.zdog.library.render

class Cone : Ellipse() {
    var length = 1f

    private lateinit var apex: Anchor
    private lateinit var renderApex: Vector
    private lateinit var renderCentroid: Vector
    private lateinit var tangentA: Vector
    private lateinit var tangentB: Vector
    private lateinit var surfacePathCommands: List<PathCommand>

    init {
        fill = true
    }

    override fun onCreate() {
        super.onCreate()
        apex = Anchor().setup {
            it.addTo = this
            it.translate(z = length)
        }

        renderApex = vector()
        renderCentroid = vector()
        tangentA = vector()
        tangentB = vector()

        surfacePathCommands = listOf(
            move(),
            line(),
            line()
        )
    }

    override fun updateSortValue() {
        renderCentroid.set(renderOrigin)
            .lerp(apex.renderOrigin, 1/3f)
        sortValue = renderCentroid.z
    }

    override fun render(renderer: Renderer) {
        renderConeSurface(renderer)
        super.render(renderer)
    }

    private fun renderConeSurface(renderer: Renderer) {
        if (!visible) {
            return
        }
        renderApex.set(apex.renderOrigin).
                subtract(renderOrigin)
        val scale = renderNormal.magnitude()
        val apexDistance = renderApex.magnitude2d()
        val normalDistance = renderNormal.magnitude2d()

        val eccenAngle = Math.acos((normalDistance/scale).toDouble())
        val eccen = Math.sin(eccenAngle)
        val radius = diameter/2 * scale
        val isApexVisible = radius * eccen < apexDistance
        if (!isApexVisible) {
            return
        }

        val apexAngle = (Math.atan2(renderNormal.y.toDouble(), renderNormal.x.toDouble())
            + TAU/2).toFloat()
        val projectLength = apexDistance/eccen
        val projectAngle = Math.acos(radius/projectLength)

        tangentA.x = (Math.cos(projectAngle) * radius * eccen).toFloat()
        tangentA.y = (Math.sin(projectAngle) * radius).toFloat()

        tangentB.set(tangentA)
        tangentB.y *= -1

        tangentA.rotateZ(apexAngle)
        tangentB.rotateZ(apexAngle)
        tangentA.add(renderOrigin)
        tangentB.add(renderOrigin)

        setSurfaceRenderPoint(0, tangentA)
        setSurfaceRenderPoint(1, apex.renderOrigin)
        setSurfaceRenderPoint(2, tangentB)

        renderer.renderPath(surfacePathCommands)
        renderer.stroke(stroke > 0, colour, stroke, renderAlpha)
        renderer.fill(fill, colour, renderAlpha)
        renderer.end()
    }

    private fun setSurfaceRenderPoint(index: Int, point: Vector) {
        surfacePathCommands[index].renderPoint().set(point)
    }

    override fun copy(): Cone {
        return copy(Cone())
    }

    override fun copy(shape: Anchor): Cone {
        return (super.copy(shape) as Cone).also {
            it.length = length
        }
    }
}