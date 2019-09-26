package com.zdog.demo.status

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zdog.demo.R
import com.zdog.demo.extention.inflate
import com.zdog.demo.ui.status.WeatherStatus
import kotlinx.android.synthetic.main.fragment_weather_status_item.view.*
import kotlin.properties.Delegates

class WeatherStatusAdapter: RecyclerView.Adapter<WeatherStatusAdapter.ViewHolder>() {
    enum class PayloadType {
        STATUS_CHANGED
    }

    enum class Status {
        DynamicDay,
        StaticDay,
        DynamicNight,
        StaticNight
    }

    internal var collection = IntArray(48) { it }

    internal var status: Status by Delegates.observable(Status.DynamicDay) { _, old, new->
        if (old != new) {
            collection.indices.forEach {
                notifyItemChanged(it, PayloadType.STATUS_CHANGED)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.fragment_weather_status_item))

    override fun getItemCount() = collection.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collection[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            when (payloads[0] as PayloadType) {
                PayloadType.STATUS_CHANGED -> {
                    setIcon(holder.itemView, collection[position])
                }
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(code: Int) {
            with(itemView) {
                setItem(this, code)
            }
        }

        private fun setItem(itemView: View, code: Int) {
            with(itemView) {
                title.text = context.resources.getStringArray(R.array.conditions)[code]
                setIcon(icon, code)
            }
        }
    }

    private fun setIcon(view: View, code: Int) {
        with(view) {
            when (status) {
                Status.DynamicDay -> {
                    icon.setImageDrawable(WeatherStatus.toDynamicDay(code))
                    icon.start()
                }
                Status.DynamicNight -> {
                    icon.setImageDrawable(WeatherStatus.toDynamicNight(code))
                    icon.start()
                }
                Status.StaticDay -> {
                    icon.setImageDrawable(WeatherStatus.toStaticDay(code))
                }
                Status.StaticNight -> {
                    icon.setImageDrawable(WeatherStatus.toStaticNight(code))
                }
            }
        }
    }
}