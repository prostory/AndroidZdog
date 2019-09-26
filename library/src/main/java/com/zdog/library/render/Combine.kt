package com.zdog.library.render

open class Combine : Group() {
    init {
        updateSort = true
    }

    override fun reset() {
        super.reset()
        children.forEach { it.color = color }
    }

    override fun copy(): Combine {
        return copy(Combine())
    }

    override fun copy(shape: Anchor): Combine {
        return super.copy(shape) as Combine
    }
}