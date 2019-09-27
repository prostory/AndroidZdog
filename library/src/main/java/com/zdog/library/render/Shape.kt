package com.zdog.library.render

import android.graphics.Path
import android.graphics.PathEffect
import android.graphics.Shader
import com.condor.weather.library.extension.empty

open class Shape : Anchor() {
    open var stroke = 1f
    open var fill = false
    open var visible = true
    var closed = true
    var front = vector(z = 1f)
    var backface: String? = String.empty()
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) {
                backfaceColor = value.color
            }
        }
    val renderNormal = vector()
    var effect: PathEffect? = null
    var shader: Shader? = null
    var segment: Segment? = null
    var layer: ShaderLayer? = null

    private var isFacingBack = false
    private val renderFront = vector(front)
    var path: MutableList<PathCommand> = mutableListOf()
        private set

    internal var backfaceColor = colour

    private val renderColor: Int
        get() {
            val isBackFaceColor = !backface.isNullOrEmpty() && isFacingBack
            return if (isBackFaceColor) backfaceColor else colour
        }

    override fun onCreate() {
        super.onCreate()
        updatePath()
    }

    fun path(vararg commands: PathCommand): MutableList<PathCommand> {
        path.clear()
        commands.forEach { path.add(it) }
        return path
    }

    fun path(commands: List<PathCommand>): MutableList<PathCommand> {
        path.clear()
        commands.forEach { path.add(it) }
        return path
    }

    internal fun updatePath() {
        setPath()
        updatePathCommands()
    }

    protected open fun setPath() {

    }

    fun updatePathCommands() {
        var previousPoint: Vector? = null
        if (path.isEmpty()) {
            path.add(move())
        } else {
            val first = path.first()
            if (first !is Move) {
                path[0] = move(first.point())
            }
            path.forEach {
                it.previous(previousPoint)
                previousPoint = it.endRenderPoint
            }
        }
    }

    fun updateSegment(start: Float, end: Float, block: ((Path) -> Unit)? = null) {
        segment = (segment ?: Segment()).set(start, end, block)
    }

    override fun reset() {
        super.reset()
        renderFront.set(front)
        path.forEach { it.reset() }
    }

    override fun transform(translation: Vector, rotation: Vector, scale: Vector) {
        renderOrigin.transform(translation, rotation, scale)
        renderFront.transform(translation, rotation, scale)
        renderNormal.set(renderOrigin).subtract(renderFront)

        path.forEach {
            it.transform(translation, rotation, scale)
        }
        children.forEach {
            it.transform(translation, rotation, scale)
        }
    }

    override fun updateSortValue() {
        var pointCount = path.size
        val firstPoint = path[0].endRenderPoint
        val lastPoint = path.last().endRenderPoint
        val isSelfClosing = pointCount > 2 && firstPoint.isSame(lastPoint)
        if (isSelfClosing) {
            pointCount -= 1
        }

        var sortValueTotal = 0f
        for (i in 0 until pointCount) {
            sortValueTotal += path[i].endRenderPoint.z
        }
        sortValue = sortValueTotal / pointCount
    }

    override fun render(renderer: Renderer) {
        val length = path.size
        if (!visible || length == 0) {
            return
        }
        isFacingBack = renderNormal.z > 0
        if (backface == null && isFacingBack) {
            return
        }

        val isDot = length == 1
        val oldSegment = renderer.segment
        if (oldSegment == null) {
            renderer.segment = segment
        }
        if (isDot) {
            renderDot(renderer)
        } else {
            renderPath(renderer)
        }
        renderer.segment = oldSegment
    }

    private fun renderDot(renderer: Renderer) {
        if (stroke == 0f) {
            return
        }
        renderer.fillStyle = renderColor
        val point = path[0].endRenderPoint
        renderer.begin()
        val radius = stroke / 2
        renderer.move(point)
        renderer.circle(point.x, point.y, radius)
        renderer.fill(renderAlpha, shader, layer)
    }

    private fun renderPath(renderer: Renderer) {
        val isTwoPoints = path.size == 2 &&
                (path[1] is Line)
        val isClosed = !isTwoPoints && closed
        val color = renderColor
        renderer.renderPath(path, isClosed)
        renderer.stroke(stroke > 0f, color, stroke, renderAlpha, effect, layer)
        renderer.fill(fill, color, renderAlpha, shader)
        renderer.end()
    }

    fun toPath(renderer: Renderer): Path {
        return Path().apply {
            if (path.isNotEmpty()) {
                val isTwoPoints = path.size == 2 &&
                        (path[1] is Line)
                val isClosed = !isTwoPoints && closed
                renderer.renderToPath(this, path, isClosed)
            }
        }
    }

    override fun copy(): Shape {
        return copy(Shape())
    }

    override fun copy(shape: Anchor): Shape {
        return (super.copy(shape) as Shape).also {
            it.stroke = stroke
            it.fill = fill
            it.closed = closed
            it.visible = visible
            it.path = path.map { it.clone() }.toMutableList()
            it.front.set(front)
            it.backface = backface
        }
    }
}

