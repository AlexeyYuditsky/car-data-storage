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
                override fun map(vararg a: Any?) {
                    binding.modelTextView.text = a[1].toString()
                    binding.colorTextView.text = a[2].toString()
                    binding.speedTextView.text = a[3].toString()
                    binding.hpTextView.text = a[4].toString()
                }
            })
            itemView.tag = item
        }

    }

    class Fail(
        private val binding: ItemFailBinding,
        private val retry: RetryClickListener,
    ) : CarViewHolder(binding.root) {

        override fun bind(item: CarUi) {
            item.map(object : TextMapper {
                override fun map(vararg a: Any?) {
                    binding.message.text = a[0].toString()
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