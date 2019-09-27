package com.zdog.library.render

class Cylinder : Shape() {
    var diameter = 1f
    var length = 1f
    var frontFace: String? = null

    override var stroke: Float = 1f
        set(value) {
            field = value
            frontBase?.stroke = value
            rearBase?.stroke = value
            group?.stroke = value
        }

    override var fill: Boolean = true
        set(value) {
            field = value
            frontBase?.fill = value
            rearBase?.fill = value
            group?.fill = value
        }

    override var color
        get() = colour.toColor()
        set(value) {
            colour = value.color
            frontBase?.color = value
            rearBase?.color = value
            group?.color = value
        }

    override var visible: Boolean = true
        set(value) {
            field = value
            frontBase?.visible = value
            rearBase?.visible = value
            group?.visible = value
        }

    private var group: CylinderGroup? = null
    private var frontBase: Ellipse? = null
    private var rearBase: Ellipse? = null

    override fun onCreate() {
        super.onCreate()

        val group = CylinderGroup().setup {
            it.addTo = this
            it.color = color
            it.visible = visible
        }
        val baseZ = length/2
        val baseColor = if (backface != null) backface else null
        group.frontBase = Ellipse().setup {
            it.addTo = group
            it.diameter = diameter
            it.translate(z = baseZ)
            it.rotate(y = (TAU/2).toFloat())
            it.color = color
            it.stroke = stroke
            it.fill = fill
            it.backface = if (frontFace != null) frontFace else baseColor
            it.visible = visible
        }
        frontBase = group.frontBase

        group.rearBase = group.frontBase.copy {
            translate(z = -baseZ)
            rotate(y = 0f)
            backface = baseColor
        }
        rearBase = group.rearBase

        this.group = group
    }

    override fun render(renderer: Renderer) {
    }

    override fun copy(): Cylinder {
        return copy(Cylinder())
    }

    override fun copy(shape: Anchor): Cylinder {
        return (super.copy(shape) as Cylinder).also {
            it.diameter = diameter
            it.length = length
            it.frontFace = frontFace
        }
    }
}