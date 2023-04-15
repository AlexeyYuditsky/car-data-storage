package com.alexeyyuditsky.cardatastorage.presentation.cars

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.alexeyyuditsky.cardatastorage.R
import com.alexeyyuditsky.cardatastorage.databinding.DialogCarBinding

class EditCarDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogCarBinding.inflate(layoutInflater)

        val model = arguments?.getString(KEY_MODEL)
        val color = arguments?.getString(KEY_COLOR)
        val speed = arguments?.getInt(KEY_SPEED)
        val hp = arguments?.getInt(KEY_HP)

        binding.modelInputEditText.setText(model)
        binding.colorInputEditText.setText(color)
        binding.speedInputEditText.setText(speed.toString())
        binding.hpInputEditText.setText(hp.toString())

        return AlertDialog.Builder(requireContext())
            .setTitle(model)
            .setView(binding.root)
            .setNegativeButton(R.string.action_cancel, null)
            .setPositiveButton(R.string.action_confirm) { _, _ ->
                requireActivity().supportFragmentManager.setFragmentResult(
                    REQUEST_KEY, bundleOf(
                        KEY_ID to arguments?.getLong(KEY_ID),
                        KEY_MODEL to binding.modelInputEditText.text.toString(),
                        KEY_COLOR to binding.colorInputEditText.text.toString(),
                        KEY_SPEED to binding.speedInputEditText.text.toString().toInt(),
                        KEY_HP to binding.hpInputEditText.text.toString().toInt(),
                    )
                )
            }
            .create()
    }

    companion object {
        const val KEY_ID = "id"
        const val KEY_MODEL = "model"
        const val KEY_COLOR = "color"
        const val KEY_SPEED = "speed"
        const val KEY_HP = "hp"
        const val REQUEST_KEY = "editCarDialogRequestKey"

        fun newInstance(car: CarUi): EditCarDialogFragment {
            return EditCarDialogFragment().apply {
                car.map(object : TextMapper {
                    override fun map(vararg a: Any?) {
                        arguments = bundleOf(
                            KEY_ID to a[0].toString().toLong(),
                            KEY_MODEL to a[1].toString(),
                            KEY_COLOR to a[2].toString(),
                            KEY_SPEED to a[3].toString().toInt(),
                            KEY_HP to a[4].toString().toInt()
                        )
                    }
                })
            }
        }
    }

}