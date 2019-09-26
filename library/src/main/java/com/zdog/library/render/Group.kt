package com.zdog.library.render

import android.graphics.Path

open class Group : Anchor() {
    var updateSort = false
    var visible = true
    var segment: Segment? = null

    override fun updateSortValue() {
        sortValue = flatGraph?.let {
            it.map {
                it.updateSortValue()
                it.sortValue
            }.sum() / it.size
        } ?: renderOrigin.z

        if (updateSort) {
            sortFlatGraph()
        }
    }

    override fun addChild(shape: Anchor) {
        super.addChild(shape)
        updateFlatGraph()
    }

    override fun render(renderer: Renderer) {
        if (!visible) {
            return
        }

        val oldSegment = renderer.segment
        if (oldSegment == null) {
            renderer.segment = segment
        }
        super.renderGraph(renderer)
        renderer.segment = oldSegment
    }

    override fun updateFlatGraph() {
        flatGraph = addChildFlatGraph(mutableListOf())
    }

    fun updateSegment(start: Float, end: Float, block: ((Path)-> Unit)? = null) {
        segment = (segment?:Segment()).set(start, end, block)
    }

    override fun flatGraph(): List<Anchor> {
        return listOf(this)
    }

    override fun copy(): Group {
        return copy(Group())
    }

    override fun copy(shape: Anchor): Group {
        return (super.copy(shape) as Group).also {
            it.updateSort = updateSort
            it.visible = visible
        }
    }
}