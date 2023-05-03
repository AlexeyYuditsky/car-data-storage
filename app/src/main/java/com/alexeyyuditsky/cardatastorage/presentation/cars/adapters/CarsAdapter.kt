package com.alexeyyuditsky.cardatastorage.presentation.cars.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.cardatastorage.R
import com.alexeyyuditsky.cardatastorage.databinding.ItemCarBinding
import com.alexeyyuditsky.cardatastorage.databinding.ItemFailBinding
import com.alexeyyuditsky.cardatastorage.presentation.cars.CarUi

typealias RetryClickListener = () -> Unit
typealias ImageClickListener = (uri: String) -> Unit

class CarsAdapter(
    private val retryClickListener: RetryClickListener,
    private val imageClickListener: ImageClickListener,
) : RecyclerView.Adapter<CarViewHolder>() {

    private val cars = mutableListOf<CarUi>()

    fun update(newCars: List<CarUi>) {
        val diffCallback = DiffUtilCallback(cars, newCars)
        val result = DiffUtil.calculateDiff(diffCallback, false)
        cars.clear()
        cars.addAll(newCars)
        result.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return when (cars[position]) {
            is CarUi.Base -> R.layout.item_car
            is CarUi.Fail -> R.layout.item_fail
            is CarUi.Empty -> R.layout.item_empty
            else -> R.layout.item_progress
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        return when (viewType) {
            R.layout.item_car -> {
                val binding = ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CarViewHolder.Base(binding, imageClickListener)
            }
            R.layout.item_fail -> {
                val binding = ItemFailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CarViewHolder.Fail(binding, retryClickListener)
            }
            R.layout.item_empty -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_empty, parent, false)
                CarViewHolder.Empty(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false)
                CarViewHolder.Progress(view)
            }
        }
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val item = cars[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = cars.size

}