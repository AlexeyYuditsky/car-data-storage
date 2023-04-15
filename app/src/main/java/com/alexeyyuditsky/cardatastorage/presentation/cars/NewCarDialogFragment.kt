package com.alexeyyuditsky.cardatastorage.presentation.cars

import android.content.DialogInterface
import android.widget.Toast
import androidx.core.os.bundleOf
import com.alexeyyuditsky.cardatastorage.R

class NewCarDialogFragment : BaseCarDialogFragment() {

    override fun getTitle(): String {
        return getString(R.string.new_car)
    }

    override fun createPositiveListener(): DialogInterface.OnClickListener {
        return object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                val model = binding.modelInputEditText.text.toString()
                val color = binding.colorInputEditText.text.toString()
                val speed = binding.speedInputEditText.text.toString()
                val hp = binding.hpInputEditText.text.toString()
                val imageUri = if (binding.imageView.tag == null)
                    ""
                else
                    binding.imageView.tag.toString()

                listOf(model, color, speed, hp, imageUri).forEach {
                    if (it.isBlank()) {
                        Toast.makeText(requireContext(), R.string.fill_gaps, Toast.LENGTH_SHORT).show()
                        return
                    }
                }

                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY, bundleOf(
                        KEY_MODEL to model,
                        KEY_COLOR to color,
                        KEY_SPEED to speed.toInt(),
                        KEY_HP to hp.toInt(),
                        KEY_IMAGE to imageUri
                    )
                )
            }
        }
    }

    companion object {
        const val REQUEST_KEY = "newCarDialogRequestKey"

        fun newInstance(): NewCarDialogFragment {
            return NewCarDialogFragment()
        }
    }

}