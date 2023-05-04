package com.alexeyyuditsky.cardatastorage.presentation.cars.screens.main

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.alexeyyuditsky.cardatastorage.R
import com.alexeyyuditsky.cardatastorage.databinding.FragmentCarsBinding
import com.alexeyyuditsky.cardatastorage.presentation.cars.CarUi
import com.alexeyyuditsky.cardatastorage.presentation.cars.TextMapper
import com.alexeyyuditsky.cardatastorage.presentation.cars.adapters.CarsAdapter
import com.alexeyyuditsky.cardatastorage.presentation.cars.screens.editcar.EditCarDialogFragment
import com.alexeyyuditsky.cardatastorage.presentation.cars.screens.fullscreencar.FullscreenDialogFragment
import com.alexeyyuditsky.cardatastorage.presentation.cars.screens.newcar.NewCarDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CarsFragment : Fragment(R.layout.fragment_cars) {

    private val viewModel by viewModels<CarsViewModel>()

    private var _binding: FragmentCarsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCarsBinding.bind(view)
        val carsAdapter = setupRecyclerView()
        carsObserver(carsAdapter)
        swipeListener(carsAdapter)
        sortListener()
        filterListener()
        editCarDialogListener()
        newCarDialogListener()
        floatButtonListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun carsObserver(carsAdapter: CarsAdapter) {
        viewModel.carsLiveData.observe(viewLifecycleOwner) {
            carsAdapter.update(it)
        }
    }

    private fun sortListener() {
        binding.sortByBrandCheckBox.setOnClickListener {
            isSort = binding.sortByBrandCheckBox.isChecked
            checkBoxObserver()
        }
    }

    private fun filterListener() {
        binding.filterBySpeedCheckBox.setOnClickListener {
            isFilter = binding.filterBySpeedCheckBox.isChecked
            checkBoxObserver()
        }
    }

    private fun checkBoxObserver() {
        lifecycleScope.launch {
            delay(100)
            if (isSort && isFilter) {
                viewModel.fetchSortAndFilterByCars()
            } else if (isSort && !isFilter) {
                viewModel.fetchSortByBrandCars()
            } else if (!isSort && isFilter) {
                viewModel.fetchFilterBySpeedCars()
            } else if (!isSort && !isFilter) {
                viewModel.fetchAllCars()
            }
            binding.recyclerView.scrollToPosition(0)
        }
    }

    private fun setupRecyclerView(): CarsAdapter {
        val carsAdapter = CarsAdapter(
            retryClickListener = { viewModel.fetchAllCars() },
            imageClickListener = { uri: String ->
                findNavController().navigate(
                    R.id.action_carsFragment_to_fullscreenDialogFragment,
                    bundleOf(FullscreenDialogFragment.KEY_URI to uri)
                )
            }
        )

        binding.recyclerView.adapter = carsAdapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
        (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        return carsAdapter
    }

    private fun floatButtonListener() {
        binding.addCarFloatButton.setOnClickListener {
            findNavController().navigate(R.id.action_carsFragment_to_newCarDialogFragment)
        }
    }

    private fun swipeListener(carsAdapter: CarsAdapter) {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean = false

            @SuppressLint("NotifyDataSetChanged")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val car = viewHolder.itemView.tag as? CarUi ?: return
                val carArgs = Array<String?>(6) { null }
                car.map(object : TextMapper {
                    override fun map(vararg a: Any) {
                        carArgs[0] = a[0].toString()
                        carArgs[1] = a[1].toString()
                        carArgs[2] = a[2].toString()
                        carArgs[3] = a[3].toString()
                        carArgs[4] = a[4].toString()
                        carArgs[5] = a[5].toString()
                    }
                })
                findNavController().navigate(
                    R.id.action_carsFragment_to_editCarDialogFragment,
                    bundleOf(KEY_CAR_ARGS to carArgs)
                )
                carsAdapter.notifyDataSetChanged()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean,
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(ContextCompat.getColor(requireContext(), R.color.my_background))
                    .addActionIcon(R.drawable.ic_edit)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun editCarDialogListener() {
        parentFragmentManager.setFragmentResultListener(
            EditCarDialogFragment.REQUEST_KEY, this
        ) { _, result ->
            val carArgs = result.getStringArray(KEY_CAR_ARGS)!!
            viewModel.updateCar(
                id = carArgs[0].toLong(),
                model = carArgs[1],
                color = carArgs[2],
                speed = carArgs[3].toInt(),
                hp = carArgs[4].toInt(),
                image = carArgs[5],
            )
            checkBoxObserver()
        }
    }

    private fun newCarDialogListener() {
        parentFragmentManager.setFragmentResultListener(
            NewCarDialogFragment.REQUEST_KEY, this
        ) { _, result ->
            val carArgs = result.getStringArray(KEY_CAR_ARGS)!!
            viewModel.addNewCar(
                model = carArgs[0],
                color = carArgs[1],
                speed = carArgs[2].toInt(),
                hp = carArgs[3].toInt(),
                image = carArgs[4],
            )
            checkBoxObserver()
        }
    }

    companion object {
        const val KEY_CAR_ARGS = "carArgs"
        private var isSort = false
        private var isFilter = false
    }

}