package com.alexeyyuditsky.cardatastorage.presentation.cars.screens.editcar

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import com.alexeyyuditsky.cardatastorage.R
import com.alexeyyuditsky.cardatastorage.core.Const
import com.alexeyyuditsky.cardatastorage.core.Const.KEY_MODEL
import com.alexeyyuditsky.cardatastorage.presentation.cars.screens.base.BaseCarDialogFragment
import kotlin.properties.Delegates

class EditCarDialogFragment : BaseCarDialogFragment() {

    private var carId by Delegates.notNull<String>()
    private var carImageUri by Delegates.notNull<String>()

    override val positiveListener = DialogInterface.OnClickListener { _, _ ->
        val modelTemp = binding.modelInputEditText.text.toString()
        val colorTemp = binding.colorInputEditText.text.toString()
        val speedTemp = binding.speedInputEditText.text.toString()
        val hpTemp = binding.hpInputEditText.text.toString()
        val imageUriTemp = if (binding.imageView.tag == null)
            carImageUri
        else
            binding.imageView.tag.toString()

        arrayOf(modelTemp, colorTemp, speedTemp, hpTemp).forEach {
            if (it.isBlank()) {
                Toast.makeText(requireContext(), R.string.fill_gaps, Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
        }

        val carArgs = arrayOf(carId, modelTemp, colorTemp, speedTemp, hpTemp, imageUriTemp)

        parentFragmentManager.setFragmentResult(
            REQUEST_KEY,
            bundleOf(Const.KEY_ARGS_CAR to carArgs)
        )
    }

    override fun getTitle(): String {
        return requireArguments().getString(KEY_MODEL, "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val carArgs = requireArguments().getStringArray(Const.KEY_ARGS_CAR)!!
        carId = carArgs[0]
        binding.modelInputEditText.setText(carArgs[1])
        binding.colorInputEditText.setText(carArgs[2])
        binding.speedInputEditText.setText(carArgs[3])
        binding.hpInputEditText.setText(carArgs[4])
        binding.imageView.setImageURI(Uri.parse(carArgs[5].apply { carImageUri = this }))
    }

    companion object {
        const val REQUEST_KEY = "editCarDialogRequestKey"
    }

}