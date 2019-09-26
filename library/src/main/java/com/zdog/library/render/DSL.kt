package com.zdog.library.render

// PathString
fun move(x: Float = 0f, y: Float = 0f, z: Float = 0f): PathCommand {
    return Move(vector(x, y, z))
}

fun move(vector: Vector): PathCommand {
    return Move(vector)
}

fun line(x: Float = 0f, y: Float = 0f, z: Float = 0f): PathCommand {
    return Line(vector(x, y, z))
}

fun line(vector: Vector): PathCommand {
    return Line(vector)
}

fun bezier(cp0: Vector, cp1: Vector, cp2: Vector): PathCommand {
    return Bezier(arrayOf(cp0, cp1, cp2))
}

fun arc(cp0: Vector, cp1: Vector): PathCommand {
    return Arc(arrayOf(cp0, cp1))
}

// Shape
fun vector(f: Float): Vector {
    return Vector(f, f, f)
}

fun vector(x: Float = 0f, y: Float = 0f, z: Float = 0f): Vector {
    return Vector(x, y, z)
}

fun vector(vector: Vector): Vector {
    return Vector(vector)
}

fun anchor(block: (Anchor.()-> Unit)? = null): Anchor {
    return Anchor().set(block)
}

fun group(block: (Group.()-> Unit)? = null): Group {
    return Group().set(block)
}

fun illustration(block: (Illustration.()-> Unit)? = null) : Illustration {
    return Illustration().set(block)
}

fun shape(block: (Shape.()-> Unit)? = null): Shape {
    return Shape().set(block)
}

fun rect(block: (Rect.()-> Unit)? = null): Rect {
    return Rect().set(block)
}

fun roundedRect(block: (RoundedRect.()-> Unit)? = null): RoundedRect {
    return RoundedRect().set(block)
}

fun ellipse(block: (Ellipse.() -> Unit)? = null): Ellipse {
    return Ellipse().set(block)
}

fun polygon(block: (Polygon.() -> Unit)? = null): Polygon {
    return Polygon().set(block)
}

fun hemisphere(block: (Hemisphere.()-> Unit)? = null): Hemisphere {
    return Hemisphere().set(block)
}

fun cone(block: (Cone.()-> Unit)? = null): Cone {
    return Cone().set(block)
}

fun cylinder(block: (Cylinder.()-> Unit)? = null): Cylinder {
    return Cylinder().set(block)
}

fun box(block: (Box.() -> Unit)? = null): Box {
    return Box().set(block)
}

fun combine(block: (Combine.() -> Unit)? = null): Combine {
    return Combine().set(block)
}

fun text(block: (Text.() -> Unit)? = null): Text {
    return Text().set(block)
}

@Suppress("UNCHECKED_CAST")
fun <T: Anchor> T.copy(block: T.()-> Unit): T = (this.copy() as T).set(block)

fun <T: Anchor> T.copyEx(block: T.()-> Unit): T = this.copyGraph(block)

fun <T: IProperty> T.set(block: (T.()-> Unit)? = null): T = apply {
    block?.invoke(this)
    onCreate()
}

fun <T: IProperty> T.setup(block: (T)-> Unit): T = apply {
    block.invoke(this)
    onCreate()
}

fun <T> T.attachTo(collection: MutableCollection<T>) = apply {
    collection.add(this)
}