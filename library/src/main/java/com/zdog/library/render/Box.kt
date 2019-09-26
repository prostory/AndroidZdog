package com.zdog.library.render

typealias FaceOptions = (BoxRect)-> Unit

class Box : Anchor() {
    var stroke = 1f
        set(value) {
            field = value
            setProperty { _, rect -> rect.stroke = stroke }
        }
    var fill = true
        set(value) {
            field = value
            setProperty { _, rect -> rect.fill = fill }
        }
    override var color = "#333".toColour()
        set(value) {
            field.set(value)
            setProperty { face, rect ->
                if (face == null) rect.color.set(color)
            }
        }
    var visible = true
        set(value) {
            field = value
            setProperty { _, rect -> rect.visible = visible }
        }
    var front = vector(z = 1f)
        set(value) {
            field = value
            setProperty { _, rect -> rect.front = front }
        }
    var backface: Colour? = null
        set(value) {
            if (field == null) field = value else field?.set(value)
            setProperty { _, rect -> rect.backface = backface }
        }
    var width = 1f
    var height = 1f
    var depth = 1f

    internal var showBackFace = true
        set(value) {
            field = value
            setProperty { _, rect -> rect.showBackFace = showBackFace }
        }

    private val frontFaceOptions:FaceOptions = {
        it.width = width
        it.height = height
        it.translate(z = depth/2)
    }

    private val rearFaceOptions:FaceOptions = {
        it.width = width
        it.height = height
        it.translate(z = -depth/2)
        it.rotate(y = (TAU/2).toFloat())
    }

    private val leftFaceOptions:FaceOptions = {
        it.width = depth
        it.height = height
        it.translate(x = -width/2)
        it.rotate(y = (-TAU/4).toFloat())
    }

    private val rightFaceOptions: FaceOptions = {
        it.width = depth
        it.height = height
        it.translate(x = width/2)
        it.rotate(y = (TAU/4).toFloat())
    }

    private val topFaceOptions: FaceOptions = {
        it.width = width
        it.height = depth
        it.translate(y = -height/2)
        it.rotate(x = (-TAU/4).toFloat())
    }

    private val bottomFaceOptions: FaceOptions = {
        it.width = width
        it.height = depth
        it.translate(y = height/2)
        it.rotate(x = (TAU/4).toFloat())
    }

    var frontFace: Colour? = color
        set(value) {
            field = value
            frontRect = initRect(frontRect, value, frontFaceOptions)
        }

    private var frontRect: BoxRect? = null

    var rearFace: Colour? = color
        set(value) {
            field = value
            rearRect = initRect(rearRect, value, rearFaceOptions)
        }
    private var rearRect: BoxRect? = null

    var leftFace: Colour? = color
        set(value) {
            field = value
            leftRect = initRect(leftRect, value, leftFaceOptions)
        }
    private var leftRect: BoxRect? = null

    var rightFace: Colour? = color
        set(value) {
            field = value
            rightRect = initRect(rightRect, value, rightFaceOptions)
        }
    private var rightRect: BoxRect? = null

    var topFace: Colour? = color
        set(value) {
            field = value
            topRect = initRect(topRect, value, topFaceOptions)
        }
    private var topRect: BoxRect? = null

    var bottomFace: Colour? = color
        set(value) {
            field = value
            bottomRect = initRect(bottomRect, value, bottomFaceOptions)
        }
    private var bottomRect: BoxRect? = null

    private fun initRect(face: BoxRect?, value: Colour?, block: (BoxRect)-> Unit): BoxRect? {
        var rect = face
        if (value == null) {
            if (rect != null) {
                removeChild(rect)
            }
            return null
        }

        rect = if (rect != null) {
            rect.also {
                block(it)
                it.color = value
            }
        } else {
            BoxRect().setup {
                block(it)
                it.color = value
            }
        }
        rect.updatePath()
        addChild(rect)

        return rect
    }

    override fun onCreate() {
        initRect()
        super.onCreate()
        updatePath()
    }

    private fun updatePath() {
        setProperty { face, rect ->
            rect.color = if(face == null) color else face
            rect.stroke = stroke
            rect.fill = fill
            rect.backface = backface
            rect.front = front
            rect.visible = visible
            rect.showBackFace = showBackFace
        }
    }

    private fun initRect() {
        if (frontFace != null) {
            frontRect = frontRect ?: newRect(frontFaceOptions)
        }
        if (rearFace != null) {
            rearRect = rearRect ?: newRect(rearFaceOptions)
        }
        if (leftFace != null) {
            leftRect = leftRect ?: newRect(leftFaceOptions)
        }
        if (rightFace != null) {
            rightRect = rightRect ?: newRect(rightFaceOptions)
        }
        if (topFace != null) {
            topRect = topRect ?: newRect(topFaceOptions)
        }
        if (bottomFace != null) {
            bottomRect = bottomRect ?: newRect(bottomFaceOptions)
        }
    }

    private fun newRect(options: FaceOptions) =
        BoxRect().setup(options).also {
            it.updatePath()
            addChild(it)
        }

    private fun setProperty(block: (Colour?, BoxRect) -> Unit) {
        if (frontRect != null) block(frontFace, frontRect!!)
        if (rearRect != null) block(rearFace, rearRect!!)
        if (leftRect != null) block(leftFace, leftRect!!)
        if (rightRect != null) block(rightFace, rightRect!!)
        if (topRect != null) block(topFace, topRect!!)
        if (bottomRect != null) block(bottomFace, bottomRect!!)
    }

    override fun copy(): Box {
        return copy(Box())
    }

    override fun copy(shape: Anchor): Box {
        return (super.copy(shape) as Box).also {
            it.stroke = stroke
            it.fill = fill
            it.color = color
            it.visible = visible
            it.front = front
            it.backface = backface
            it.showBackFace = showBackFace
            it.width = width
            it.height = height
            it.depth = depth

            it.frontFace = frontFace
            it.rearFace = rearFace
            it.leftFace = leftFace
            it.rightFace = rightFace
            it.topFace = topFace
            it.bottomFace = bottomFace
        }
    }
}