package com.alexeyyuditsky.cardatastorage.presentation.cars

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.alexeyyuditsky.cardatastorage.R
import com.alexeyyuditsky.cardatastorage.databinding.DialogCarBinding

class NewCarDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogCarBinding.inflate(layoutInflater)

        return AlertDialog.Builder(requireContext())
            .setTitle("adsadsad")
            .setView(binding.root)
            .setNegativeButton(R.string.action_cancel, null)
            .setPositiveButton(R.string.action_confirm) { _, _ ->
                requireActivity().supportFragmentManager.setFragmentResult(
                    REQUEST_KEY, bundleOf(
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
        const val KEY_MODEL = "model"
        const val KEY_COLOR = "color"
        const val KEY_SPEED = "speed"
        const val KEY_HP = "hp"
        const val REQUEST_KEY = "newCarDialogRequestKey"

        fun newInstance(): NewCarDialogFragment {
            return NewCarDialogFragment()
        }
    }

}