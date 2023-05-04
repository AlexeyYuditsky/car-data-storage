package com.alexeyyuditsky.cardatastorage.presentation.cars.screens.newcar

import android.content.DialogInterface
import android.widget.Toast
import androidx.core.os.bundleOf
import com.alexeyyuditsky.cardatastorage.R
import com.alexeyyuditsky.cardatastorage.presentation.cars.screens.base.BaseCarDialogFragment
import com.alexeyyuditsky.cardatastorage.presentation.cars.screens.main.CarsFragment.Companion.KEY_CAR_ARGS

class NewCarDialogFragment : BaseCarDialogFragment() {

    override val positiveListener = DialogInterface.OnClickListener { _, _ ->
        val model = binding.modelInputEditText.text.toString()
        val color = binding.colorInputEditText.text.toString()
        val speed = binding.speedInputEditText.text.toString()
        val hp = binding.hpInputEditText.text.toString()
        val imageUri = if (binding.imageView.tag == null) "" else binding.imageView.tag.toString()

        arrayOf(model, color, speed, hp, imageUri).forEach {
            if (it.isBlank()) {
                Toast.makeText(requireContext(), R.string.fill_gaps, Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
        }

        val carArgs = arrayOf(model, color, speed, hp, imageUri)

        parentFragmentManager.setFragmentResult(
            REQUEST_KEY,
            bundleOf(KEY_CAR_ARGS to carArgs)
        )
    }

    override fun getTitle(): String {
        return getString(R.string.new_car)
    }

    companion object {
        const val REQUEST_KEY = "newCarDialogRequestKey"
    }

}