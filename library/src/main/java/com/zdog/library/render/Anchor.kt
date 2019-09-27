package com.zdog.library.render

import android.animation.ValueAnimator
import android.util.SparseArray


open class Anchor: IProperty {
    companion object {
        val onePoint = vector(1.0f, 1.0f, 1.0f)
    }

    val translate by lazy { vector() }
    val rotate by lazy { vector() }
    val scale by lazy { vector(onePoint) }
    open var color = "#333".toColour()
        set(value) {
            field.set(value)
        }

    var alpha: Float = 1f

    var addTo: Anchor? = null

    val renderOrigin by lazy { vector() }
    var sortValue = renderOrigin.z

    val children by lazy { mutableListOf<Anchor>() }

    protected val origin by lazy { vector() }
    protected var flatGraph: List<Anchor>? = null
        get() {
            if (field == null) {
                updateFlatGraph()
            }
            return field
        }

    protected var renderAlpha = 255

    enum class AnimatorType {
        Custom,
        Translate,
        Scale,
        Rotate,
        Alpha,
        Color
    }

    private val animators by lazy { SparseArray<ValueAnimator>(AnimatorType.values().size) }

    override fun onCreate() {
        addTo?.addChild(this)
    }

    open fun addChild(shape: Anchor) {
        if (children.indexOf(shape) != -1) {
            return
        }
        shape.remove()
        shape.addTo = this
        this.children.add(shape)
    }

    fun removeChild(shape: Anchor) {
        children.remove(shape)
    }

    fun remove() {
        addTo?.removeChild(this)
        clearAnimators()
    }

    private fun update() {
        reset()
        children.forEach {
            it.update()
            if (alpha < it.alpha) {
                it.renderAlpha = renderAlpha
            } else {
                it.renderAlpha = (it.alpha * 255).toInt().bound(0, 255)
            }
        }
        transform(translate, rotate, scale)
    }

    protected open fun reset() {
        renderOrigin.set(origin)
        renderAlpha = (alpha * 255).toInt().bound(0, 255)
    }

    fun translate(x: Float = 0f, y:Float = 0f, z:Float = 0f) =
        this.translate.set(x, y, z)

    fun translate(block: Vector.() -> Unit) =
        this.translate.apply(block)

    fun rotate(x: Float = 0f, y:Float = 0f, z:Float = 0f) =
        this.rotate.set(x, y, z)

    fun rotate(block: Vector.() -> Unit) =
        this.rotate.apply(block)

    open fun scale(scale: Float): Vector {
        return this.scale.set(scale, scale, scale)
    }

    fun scale(block: Vector.()-> Unit) =
        this.scale.apply(block)

    open fun transform(translation: Vector, rotation: Vector, scale: Vector) {
        renderOrigin.transform(translation, rotation, scale)
        children.forEach {
            it.transform(translation, rotation, scale)
        }
    }

    protected fun updateGraph() {
        update()
        updateFlatGraph()
        flatGraph?.apply {
            forEach {
                it.updateSortValue()
            }
        }
        sortFlatGraph()
    }

    protected fun sortFlatGraph() {
        flatGraph = flatGraph?.sortedWith(Comparator { a, b ->
            a.sortValue.compareTo(b.sortValue)
        })
    }

    protected open fun updateFlatGraph() {
        flatGraph = flatGraph()
    }

    protected open fun flatGraph(): List<Anchor> {
        return addChildFlatGraph(mutableListOf(this))
    }

    protected fun addChildFlatGraph(flatGraph: MutableList<Anchor>): List<Anchor> {
        children.forEach {
            flatGraph.addAll(it.flatGraph())
        }
        return flatGraph
    }

    open fun updateSortValue() {
        sortValue = renderOrigin.z
    }

    open fun render(renderer: Renderer) {}

    open fun renderGraph(renderer: Renderer) {
        flatGraph?.forEach {
            it.render(renderer)
        }
    }

    open fun copy(): Anchor {
        return copy(Anchor())
    }

    protected open fun copy(shape: Anchor): Anchor {
        return shape.also {
            it.translate.set(translate)
            it.rotate.set(rotate)
            it.scale.set(scale)
            it.color.set(color)
            it.alpha = alpha
            it.addTo = addTo
        }
    }

    @Suppress("UNCHECKED_CAST")
    open fun <T: Anchor> copyGraph(block: (T.()-> Unit)? = null): T {
        val clone = (copy() as T).set(block)
        children.forEach {
            it.copyGraph<T> {
                addTo = clone
            }
        }
        return clone
    }

    fun normalizeRotate() {
        val tau = TAU.toFloat()
        rotate.x = modulo(rotate.x, tau)
        rotate.y = modulo(rotate.y, tau)
        rotate.z = modulo(rotate.z, tau)
    }

    fun clearAnimators() {
        for (i in 0 until animators.size()) {
            val animator = animators.valueAt(i)
            if (animator != null) {
                animator.pause()
                animator.cancel()
            }
        }
        animators.clear()
        children.forEach { it.clearAnimators() }
    }

    fun addAnimator(type: AnimatorType, animator: ValueAnimator) {
        val old = animators[type.ordinal]
        if (old != null) {
            old.pause()
            old.cancel()
        }
        animators.put(type.ordinal, animator)
    }

    fun getAnimator(type: AnimatorType): ValueAnimator? =
        animators[type.ordinal]
}