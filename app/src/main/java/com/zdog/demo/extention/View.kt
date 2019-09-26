package com.zdog.demo.extention

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.zdog.demo.core.Throttle
import java.util.concurrent.TimeUnit

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun View.clicks(skipDuration:Long = 500, block: (View)-> Unit) {
    val throttle = Throttle(skipDuration, TimeUnit.MILLISECONDS)
    setOnClickListener {
        if (throttle.needSkip()) return@setOnClickListener
        block(it)
    }
}