package com.alexeyyuditsky.cardatastorage.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.cardatastorage.data.CarDbEntity
import com.alexeyyuditsky.cardatastorage.databinding.ItemCarBinding

class CarsAdapter : RecyclerView.Adapter<CarViewHolder>() {

    private val cars = mutableListOf<CarDbEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(newCars: List<CarDbEntity>) {
        cars.clear()
        cars.addAll(newCars)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val item = cars[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = cars.size
}

class CarViewHolder(
    private val binding: ItemCarBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(car: CarDbEntity) = with(binding) {
        binding.modelTextView.text = car.model
        binding.colorTextView.text = car.color
        binding.speedTextView.text = car.speed.toString()
        binding.hpTextView.text = car.hp.toString()
    }

}
