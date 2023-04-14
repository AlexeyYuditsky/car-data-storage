package com.alexeyyuditsky.cardatastorage.presentation.cars

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.cardatastorage.databinding.ItemCarBinding
import com.alexeyyuditsky.cardatastorage.databinding.ItemFailBinding

abstract class CarViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    open fun bind(item: CarUi) = Unit

    class Base(
        private val binding: ItemCarBinding,
    ) : CarViewHolder(binding.root) {

        override fun bind(item: CarUi) {
            item.map(object : TextMapper {
                override fun map(model: String, color: String, speed: Int, hp: Int, image: String?) {
                    binding.modelTextView.text = model
                    binding.colorTextView.text = color
                    binding.speedTextView.text = speed.toString()
                    binding.hpTextView.text = hp.toString()
                }
            })
        }

    }

    class Fail(
        private val binding: ItemFailBinding,
        private val retry: RetryClickListener,
    ) : CarViewHolder(binding.root) {

        override fun bind(item: CarUi) {
            item.map(object : TextMapper {
                override fun map(model: String, color: String, speed: Int, hp: Int, image: String?) {
                    binding.message.text = model
                    binding.tryAgain.setOnClickListener { retry.invoke() }
                }
            })
        }

    }

    class Progress(
        view: View,
    ) : CarViewHolder(view)

    class Empty(
        view: View,
    ) : CarViewHolder(view)

}