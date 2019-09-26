package com.condor.weather.library.extension

import android.content.res.Resources
import android.util.TypedValue

val Number.dp: Int
    get() = Math.round(
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            toFloat(), Resources.getSystem().displayMetrics
        )
    )

val Number.toDP: Float
    get() = toFloat() / TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        1f, Resources.getSystem().displayMetrics)

val Number.sp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        toFloat(), Resources.getSystem().displayMetrics
    )

val Number.toSP: Float
    get() = toFloat() / TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        1f, Resources.getSystem().displayMetrics)