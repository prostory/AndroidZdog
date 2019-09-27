package com.zdog.library.render

class Colour(c: Int) {
    private var value = c

    constructor(c: String) : this(c.color)

    constructor(a: Int, r: Int, g: Int, b: Int):
            this((a shl 24) + (r shl 16) + (g shl 8) + b)

    constructor(colour: Colour): this(colour.get())

    var alpha : Int
        get() = colorPart(value, 24)
        set(value) {
            this.value = (value shl 24) + (this.value and 0x00ffffff)
        }

    var red: Int
        get() = colorPart(value, 16)
        set(value) {
            this.value = (value shl 16) + (this.value and 0xff00ffff.toInt())
        }

    var green: Int
        get() = colorPart(value, 8)
        set(value) {
            this.value = (value shl 8) + (this.value and 0xffff00ff.toInt())
        }

    var blue: Int
        get() = colorPart(value, 0)
        set(value) {
            this.value = value + (this.value and 0xffffff00.toInt())
        }

    fun set(a: Int, r: Int, g: Int, b: Int): Colour {
        value = (a shl 24) + (r shl 16) + (g shl 8) + b
        return this
    }

    fun set(c: Int): Colour {
        value = c
        return  this
    }

    fun set(c: String): Colour {
        value = c.color
        return this
    }

    fun set(c: Colour?): Colour {
        if (c == null) return this

        value = c.get()
        return this
    }

    fun get(): Int{
        return value
    }

    fun add(c: Colour): Colour {
        set(alpha+c.alpha,
            red+c.red,
            green+c.green,
            blue+c.blue)
        return this
    }

    fun subtract(c: Colour): Colour {
        set(alpha-c.alpha,
            red-c.red,
            green-c.green,
            blue-c.blue)
        return this
    }

    fun multiply(factor: Float): Colour {
        set((alpha * factor).toInt(),
            (red * factor).toInt(),
            (green * factor).toInt(),
            (blue * factor).toInt())
        return this
    }

    fun copy(): Colour {
        return Colour(value)
    }

    override fun toString(): String {
        return value.toColor()
    }
}