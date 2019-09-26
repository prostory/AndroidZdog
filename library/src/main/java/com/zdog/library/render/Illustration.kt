package com.zdog.library.render

import android.graphics.Canvas
import android.graphics.Path

open class Illustration: Anchor() {
    var centered = true
    var zoom = 1f
    var resize = false
    var onPrerender: ((Renderer)-> Unit)? = null
    var onResize: ((Float, Float)-> Unit)? = null
    var width = 0f
    var height = 0f

    val right: Float
        get() = translate.x + width
    val bottom: Float
        get() = translate.y + height

    private val renderer = Renderer()

    init {
        color = "#FDB".toColour()
    }

    fun setSize(width: Float, height: Float) {
        this.width = width
        this.height = height
    }

    fun onResize(width: Float, height: Float) {
        setSize(width, height)
        onResize?.invoke(width, height)
    }

    private fun renderGraph(canvas: Canvas, item: Anchor? = null) {
        setCanvas(canvas)
        preRender()
        (if (item == null) this else item).renderGraph(renderer)
        postRender()
    }

    private fun preRender() {
        renderer.clearRect(translate.x, translate.y, width, height, color)
        renderer.save()
        if (centered) {
            val centerX = width / 2
            val centerY = height / 2
            renderer.translate(centerX, centerY)
        }
        val scale = zoom
        renderer.scale(scale, scale)
        onPrerender?.invoke(renderer)
    }

    private fun postRender() {
        renderer.restore()
    }

    fun updateRenderGraph(canvas: Canvas, item: Anchor? = null) {
        updateGraph()
        renderGraph(canvas, item)
    }

    private fun setCanvas(canvas: Canvas) {
        renderer.canvas = canvas
        if (width == 0f || height == 0f) {
            setSize(canvas.width.toFloat(), canvas.height.toFloat())
        }
    }

    fun renderToPath(item: Shape): Path {
        return item.toPath(renderer)
    }

    override fun copy(): Illustration {
        return copy(Illustration())
    }

    override fun copy(shape: Anchor): Illustration {
        return (super.copy(shape) as Illustration).also {
            it.centered = centered
            it.zoom = zoom
            it.resize = resize
            it.onPrerender = onPrerender
            it.onResize = onResize
            it.width = width
            it.height = height
        }
    }
}