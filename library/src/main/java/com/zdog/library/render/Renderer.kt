package com.zdog.library.render

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Cap.ROUND
import android.graphics.Paint.Join
import android.graphics.Paint.Style.FILL
import android.graphics.Paint.Style.STROKE
import android.graphics.Path
import android.graphics.Path.Direction.CW
import android.graphics.PathEffect
import android.graphics.PathMeasure
import android.graphics.Shader
import android.graphics.Typeface

class Renderer {
    private var path = Path()
    private val dest = Path()
    private val paint = Paint().apply {
        isAntiAlias = true
        strokeCap = ROUND
        strokeJoin = Join.ROUND
    }
    lateinit var canvas: Canvas
    var segment: Segment? = null
    private val measure = PathMeasure()

    fun setLineCap(value: Paint.Cap) {
        paint.strokeCap = value
    }

    fun clearRect(x: Float, y: Float, width: Float, height: Float, color: Colour) {
        val oldStyle = paint.style
        fillStyle = color
        canvas.drawRect(x, y, x+width, y+height, paint)
        paint.style = oldStyle
    }

    fun save() {
        canvas.save()
    }

    fun restore() {
        canvas.restore()
    }

    fun translate(x: Float, y: Float) {
        canvas.translate(x, y)
    }

    fun scale(x: Float, y: Float) {
        canvas.scale(x, y)
    }

    fun rotate(degree: Float) {
        canvas.rotate(degree)
    }

    fun begin() {
        path.reset()
    }

    fun move(point: Vector) {
        path.moveTo(point.x, point.y)
    }

    fun line(point: Vector) {
        path.lineTo(point.x, point.y)
    }

    fun bezier(cp0: Vector, cp1: Vector, end: Vector) {
        path.cubicTo(cp0.x, cp0.y, cp1.x, cp1.y, end.x, end.y)
    }

    fun arc(x: Float, y: Float, radius: Float, start: Float, end: Float) {
        path.arcTo(x-radius, y-radius, x+radius, y+radius, start.angle, (end-start).angle, false)
    }

    fun circle(x: Float, y: Float, radius: Float) {
        path.addCircle(x, y, radius, CW)
    }

    fun closePath() {
        path.close()
    }

    fun setPath() {
    }

    fun renderToPath(path: Path, pathCommands: List<PathCommand>, isClosed: Boolean) {
        val previousPath = this.path
        this.path = path
        renderPath(pathCommands, isClosed)
        this.path = previousPath
    }

    fun renderPath(pathCommands: List<PathCommand>) {
        begin()
        pathCommands.forEach {
            it.render(this)
        }
    }

    fun renderPath(pathCommands: List<PathCommand>, isClosed: Boolean) {
        renderPath(pathCommands)
        if (isClosed) {
            closePath()
        }
    }

    var strokeStyle: Int
        get() {
            return paint.color
        }
        set(value) {
            paint.color = value
            paint.style = STROKE
        }

    fun stroke(isStroke: Boolean, color: Colour, lineWidth: Float, alpha: Int, effect: PathEffect? = null, layer: ShaderLayer? = null) {
        if (!isStroke) {
            return
        }

        paint.color = color.get()
        paint.strokeWidth = lineWidth
        stroke(alpha, effect, layer)
    }

    fun stroke(alpha: Int, effect: PathEffect? = null, layer: ShaderLayer? = null) {
        paint.style = STROKE
        paint.alpha = alpha
        paint.shader = null
        paint.pathEffect = effect
        if (layer != null) {
            paint.setShadowLayer(layer.radius, layer.dx, layer.dy, layer.color)
            draw()
            paint.clearShadowLayer()
        } else {
            draw()
        }
    }

    var fillStyle: Colour
        get() {
            return Colour(paint.color)
        }
        set(value) {
            paint.color = value.get()
            paint.style = FILL
        }

    fun fill(isFill: Boolean, color: Colour, alpha: Int, shader: Shader? = null, layer: ShaderLayer? = null) {
        if (!isFill) {
            return
        }
        paint.color = color.get()
        fill(alpha, shader, layer)
    }

    fun fill(alpha: Int, shader: Shader? = null, layer: ShaderLayer? = null) {
        paint.style = FILL
        paint.alpha = alpha
        paint.shader = shader
        paint.pathEffect = null
        if (layer != null) {
            paint.setShadowLayer(layer.radius, layer.dx, layer.dy, layer.color)
            draw()
            paint.clearShadowLayer()
        } else {
            draw()
        }
    }

    fun text(text: String, textSize: Float, centerX: Float, centerY: Float, color: Colour,
        alpha: Int, typeface: Typeface) {
        paint.style = FILL
        paint.alpha = alpha
        paint.textSize = textSize
        paint.typeface = typeface
        paint.color = color.get()
        canvas.drawText(text, -centerX, centerY, paint)
    }

    fun end() {}

    private fun draw() {
        if (segment != null) {
            segment?.let {
                if (it.unbroken()) {
                    canvas.drawPath(path, paint)
                } else {
                    measure.setPath(path, false)
                    val length = measure.length
                    if (length != 0f) {
                        drawSegment(it, length * it.start, length * it.end, length)
                    }
                }
            }
        } else {
            canvas.drawPath(path, paint)
        }
    }

    private fun drawSegment(segment: Segment, start: Float, end: Float, length: Float) {
        dest.reset()
        if (start > end) {
            measure.getSegment(start, length, dest, true)
            measure.getSegment(0f, end, dest, false)
        } else {
            measure.getSegment(start, end, dest, true)
        }
        segment.begin(dest)
        canvas.drawPath(dest, paint)
    }
}