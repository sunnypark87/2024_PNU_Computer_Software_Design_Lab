package com.example.FlightApplication

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.FlightApplication.databinding.FlightRecyclerBinding

class ViewHolder(val binding: FlightRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: String, clickListener: (String) -> Unit) {
        binding.root.setOnClickListener {
            clickListener(data)
        }
    }
}

class RecyclerAdapter(val datas: MutableList<String>, private val clickListener: (String) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = datas.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ViewHolder(
        FlightRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolder).binding

        Log.d("RecyclerView", "Binding position: $position")

        val dataList = datas[position].split(",")
        Log.d("RecyclerView", "Data: $dataList")

        binding.leadTime.text = dataList[0].substringAfter("PT").substringBefore("H") + "시간 소요"
        binding.departureLeaveTime.text = dataList[1].substring(12,17).trim()
        binding.departureArriveTime.text = dataList[2].substring(12,17).trim()
        binding.leaveDate.text = dataList[1].substring(1,11).trim()
        binding.arriveDate.text = dataList[2].substring(1,11).trim()
        binding.price.text = dataList[3] + "$"
        binding.flightName.text = dataList[4].trim()
        binding.seatsLeft.text = dataList[5] + "석 남음"
        binding.departureFrom.text = dataList[6].trim()
        binding.departureTo.text = dataList[7].trim()

        holder.bind(datas[position], clickListener)
    }
}
