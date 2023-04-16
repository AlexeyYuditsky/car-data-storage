package com.alexeyyuditsky.cardatastorage.presentation.cars

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import com.alexeyyuditsky.cardatastorage.R
import com.alexeyyuditsky.cardatastorage.core.Const.KEY_COLOR
import com.alexeyyuditsky.cardatastorage.core.Const.KEY_HP
import com.alexeyyuditsky.cardatastorage.core.Const.KEY_ID
import com.alexeyyuditsky.cardatastorage.core.Const.KEY_IMAGE
import com.alexeyyuditsky.cardatastorage.core.Const.KEY_MODEL
import com.alexeyyuditsky.cardatastorage.core.Const.KEY_SPEED

class EditCarDialogFragment : BaseCarDialogFragment() {

    override val positiveListener = DialogInterface.OnClickListener { _, _ ->
        val modelTemp = binding.modelInputEditText.text.toString()
        val colorTemp = binding.colorInputEditText.text.toString()
        val speedTemp = binding.speedInputEditText.text.toString()
        val hpTemp = binding.hpInputEditText.text.toString()
        val imageUriTemp =
            if (binding.imageView.tag == null) arguments?.getString(KEY_IMAGE) else binding.imageView.tag.toString()

        listOf(modelTemp, colorTemp, speedTemp, hpTemp).forEach {
            if (it.isBlank()) {
                Toast.makeText(requireContext(), R.string.fill_gaps, Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
        }

        parentFragmentManager.setFragmentResult(
            REQUEST_KEY, bundleOf(
                KEY_ID to arguments?.getLong(KEY_ID),
                KEY_MODEL to modelTemp,
                KEY_COLOR to colorTemp,
                KEY_SPEED to speedTemp.toInt(),
                KEY_HP to hpTemp.toInt(),
                KEY_IMAGE to imageUriTemp
            )
        )
    }

    override fun getTitle(): String {
        return requireArguments().getString(KEY_MODEL, "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model = requireArguments().getString(KEY_MODEL)
        val color = requireArguments().getString(KEY_COLOR)
        val speed = requireArguments().getInt(KEY_SPEED)
        val hp = requireArguments().getInt(KEY_HP)
        val image = requireArguments().getString(KEY_IMAGE)

        binding.modelInputEditText.setText(model)
        binding.colorInputEditText.setText(color)
        binding.speedInputEditText.setText(speed.toString())
        binding.hpInputEditText.setText(hp.toString())
        binding.imageView.setImageURI(Uri.parse(image))
    }

    companion object {
        const val REQUEST_KEY = "editCarDialogRequestKey"

        fun newInstance(car: CarUi): EditCarDialogFragment {
            return EditCarDialogFragment().apply {
                car.map(object : TextMapper {
                    override fun map(vararg a: Any) {
                        arguments = bundleOf(
                            KEY_ID to a[0].toString().toLong(),
                            KEY_MODEL to a[1].toString(),
                            KEY_COLOR to a[2].toString(),
                            KEY_SPEED to a[3].toString().toInt(),
                            KEY_HP to a[4].toString().toInt(),
                            KEY_IMAGE to a[5].toString()
                        )
                    }
                })
            }
        }
    }

}