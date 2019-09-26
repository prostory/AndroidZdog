package com.zdog.library.render

interface PathCommand {
    val endRenderPoint: Vector
    fun reset()
    fun transform(translation: Vector, rotate: Vector, scale: Vector)
    fun render(renderer: Renderer)
    fun point(index: Int = 0): Vector
    fun renderPoint(index: Int = 0): Vector
    fun previous(previousPoint: Vector?) {}
    fun clone(): PathCommand
}

data class Move(val point: Vector): PathCommand {
    private val renderPoint = point.copy()
    override val endRenderPoint: Vector = renderPoint

    override fun reset() {
        renderPoint.set(point)
    }

    override fun transform(translation: Vector, rotate: Vector, scale: Vector) {
        renderPoint.transform(translation, rotate, scale)
    }

    override fun render(renderer: Renderer) {
        renderer.move(renderPoint)
    }

    override fun point(index: Int): Vector {
        return point
    }

    override fun renderPoint(index: Int): Vector {
        return renderPoint
    }

    override fun clone(): PathCommand {
        return copy()
    }
}

data class Line(val point: Vector): PathCommand {
    private val renderPoint = point.copy()
    override val endRenderPoint: Vector = renderPoint

    override fun reset() {
        renderPoint.set(point)
    }

    override fun transform(translation: Vector, rotate: Vector, scale: Vector) {
        renderPoint.transform(translation, rotate, scale)
    }

    override fun render(renderer: Renderer) {
        renderer.line(renderPoint)
    }

    override fun point(index: Int): Vector {
        return point
    }

    override fun renderPoint(index: Int): Vector {
        return renderPoint
    }

    override fun clone(): PathCommand {
        return copy()
    }
}

data class Bezier(
    private val points: Array<Vector>): PathCommand {
    private val renderPoints = points.map { it.copy() }
    override val endRenderPoint: Vector = renderPoints.last()

    override fun reset() {
        renderPoints.forEachIndexed { index, vector ->
            vector.set(points[index])
        }
    }

    override fun transform(translation: Vector, rotate: Vector, scale: Vector) {
        renderPoints.forEach {
            it.transform(translation, rotate, scale)
        }
    }

    override fun render(renderer: Renderer) {
        renderer.bezier(renderPoints[0], renderPoints[1], renderPoints[2])
    }

    override fun point(index: Int): Vector {
        return points[index]
    }

    override fun renderPoint(index: Int): Vector {
        return renderPoints[index]
    }

    override fun clone(): PathCommand {
        return copy()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bezier

        if (!points.contentEquals(other.points)) return false

        return true
    }

    override fun hashCode(): Int {
        return points.contentHashCode()
    }
}

data class Arc(
    private val points: Array<Vector>,
    private var previous: Vector? = null): PathCommand {
    private val renderPoints = points.map { it.copy() }
    override val endRenderPoint: Vector = renderPoints.last()

    companion object {
        private val controlPoints = arrayOf(Vector(), Vector())
    }

    override fun reset() {
        renderPoints.forEachIndexed { index, vector ->
            vector.set(points[index])
        }
    }

    override fun transform(translation: Vector, rotate: Vector, scale: Vector) {
        renderPoints.forEach {
            it.transform(translation, rotate, scale)
        }
    }

    override fun render(renderer: Renderer) {
        val prev = previous
        val corner = renderPoints[0]
        val end = renderPoints[1]
        val cp0 = controlPoints[0]
        val cp1 = controlPoints[1]
        cp0.set(prev).lerp(corner, arcHandleLength)
        cp1.set(end).lerp(corner, arcHandleLength)

        renderer.bezier(cp0, cp1, end)
    }

    override fun point(index: Int): Vector {
        return points[index]
    }

    override fun renderPoint(index: Int): Vector {
        return renderPoints[index]
    }

    override fun previous(previousPoint: Vector?) {
        previous = previousPoint
    }

    override fun clone(): PathCommand {
        return copy()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Arc

        if (!points.contentEquals(other.points)) return false
        if (previous != other.previous) return false

        return true
    }

    override fun hashCode(): Int {
        var result = points.contentHashCode()
        result = 31 * result + (previous?.hashCode() ?: 0)
        return result
    }
}