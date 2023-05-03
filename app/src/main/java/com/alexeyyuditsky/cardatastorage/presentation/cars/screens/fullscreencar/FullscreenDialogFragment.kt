package com.alexeyyuditsky.cardatastorage.presentation.cars.screens.fullscreencar

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.alexeyyuditsky.cardatastorage.databinding.DialogFullscreenBinding

class FullscreenDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogFullscreenBinding.inflate(layoutInflater)

        val uri = requireArguments().getString(KEY_URI)
        binding.imageView.setImageURI(Uri.parse(uri))

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setNegativeButton(android.R.string.cancel, null)
            .create()
    }

    companion object {
        private const val KEY_URI = "uri"

        fun newInstance(uri: String): FullscreenDialogFragment {
            return FullscreenDialogFragment().apply {
                arguments = bundleOf(KEY_URI to uri)
            }
        }
    }

}