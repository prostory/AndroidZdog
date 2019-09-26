package com.zdog.library.render

data class Vector(var x: Float = 0f, var y: Float = 0f, var z: Float = 0f) {
    constructor(pos: Vector) : this(pos.x, pos.y, pos.z)

    fun set(vector: Vector?) = apply {
        vector?.let {
            set(it.x, it.y, it.z)
        }
    }

    fun set(x: Float = 0f, y: Float = 0f, z: Float = 0f) = apply {
        synchronized(this) {
            this.x = x
            this.y = y
            this.z = z
        }
    }

    fun rotate(rotation: Vector) = apply {
        rotateZ(rotation.z)
        rotateY(rotation.y)
        rotateX(rotation.x)
    }

    fun rotateZ(angle: Float) = apply {
        if (angle == 0f || angle % TAU == 0.0) {
            return this
        }
        val cos = Math.cos(angle.toDouble())
        val sin = Math.sin(angle.toDouble())
        val tempA = x
        val tempB = y
        x = (tempA * cos - tempB * sin).toFloat()
        y = (tempB * cos + tempA * sin).toFloat()
    }

    fun rotateY(angle: Float) = apply {
        if (angle == 0f || angle % TAU == 0.0) {
            return this
        }
        val cos = Math.cos(angle.toDouble())
        val sin = Math.sin(angle.toDouble())
        val tempA = x
        val tempB = z
        x = (tempA * cos - tempB * sin).toFloat()
        z = (tempB * cos + tempA * sin).toFloat()
    }

    fun rotateX(angle: Float) = apply {
        if (angle == 0f || angle % TAU == 0.0) {
            return this
        }
        val cos = Math.cos(angle.toDouble())
        val sin = Math.sin(angle.toDouble())
        val tempA = y
        val tempB = z
        y = (tempA * cos - tempB * sin).toFloat()
        z = (tempB * cos + tempA * sin).toFloat()
    }

    fun isSame(pos: Vector): Boolean {
        return x == pos.x && y == pos.y && z == pos.z
    }

    fun add(pos: Vector) = apply {
        add(pos.x, pos.y, pos.z)
    }

    fun add(x: Float = 0f, y: Float = 0f, z: Float = 0f) = apply {
        this.x += x
        this.y += y
        this.z += z
    }

    fun subtract(pos: Vector) = apply {
        subtract(pos.x, pos.y, pos.z)
    }

    fun subtract(x: Float = 0f, y: Float = 0f, z: Float = 0f) = apply {
        this.x -= x
        this.y -= y
        this.z -= z
    }

    fun multiply(pos: Vector) = apply {
        multiply(pos.x, pos.y, pos.z)
    }

    fun multiply(x: Float = 1f, y: Float = 1f, z: Float = 1f) = apply {
        this.x *= x
        this.y *= y
        this.z *= z
    }

    fun multiply(pos: Float) = apply {
        x *= pos
        y *= pos
        z *= pos
    }

    fun transform(translation: Vector, rotation: Vector, scale: Vector) = apply {
        multiply(scale)
        rotate(rotation)
        add(translation)
    }

    fun lerp(pos: Vector, alpha: Float) {
        lerp(pos.x, pos.y, pos.z, alpha)
    }

    fun lerp(x: Float = 0f, y: Float = 0f, z: Float = 0f, alpha: Float) = apply {
        this.x = lerp(this.x, x, alpha)
        this.y = lerp(this.y, y, alpha)
        this.z = lerp(this.z, z, alpha)
    }

    fun magnitude(): Float {
        return magnitudeSqrt(x * x + y * y + z * z)
    }

    fun magnitude2d(): Float {
        return magnitudeSqrt(x * x + y * y)
    }

    fun to2d(): String {
        return "($x, $y)"
    }

    fun to3d(): String {
        return "($x, $y, $z)"
    }
}