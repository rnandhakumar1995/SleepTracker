package com.interview.usersleepinginterval.view

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.interview.usersleepinginterval.R
import com.interview.usersleepinginterval.model.DurationEntity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SleeperAdapter(var sleepingTime: ArrayList<DurationEntity>) : RecyclerView.Adapter<SleeperAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.sleeper_adapter,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val durationEntity = sleepingTime[holder.adapterPosition]
        val formatter = SimpleDateFormat("dd MMM hh:mm a", Locale.getDefault())
        val prefix = "User may sleep from "
        val str = prefix + (formatter.format(Date(durationEntity.fromTime))) + " - " + formatter.format(
            Date(
                durationEntity.toTime
            )
        )
        val fromDate = SpannableString(str)
        fromDate.setSpan(ForegroundColorSpan(Color.RED), prefix.length, str.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.tv.text = fromDate
    }

    override fun getItemCount() = sleepingTime.size
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById<TextView>(R.id.tv)
    }
}