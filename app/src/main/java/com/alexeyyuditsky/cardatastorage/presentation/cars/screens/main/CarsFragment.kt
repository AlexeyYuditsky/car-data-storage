package com.alexeyyuditsky.cardatastorage.presentation.cars.screens.main

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.alexeyyuditsky.cardatastorage.App
import com.alexeyyuditsky.cardatastorage.R
import com.alexeyyuditsky.cardatastorage.core.Const.KEY_COLOR
import com.alexeyyuditsky.cardatastorage.core.Const.KEY_HP
import com.alexeyyuditsky.cardatastorage.core.Const.KEY_ID
import com.alexeyyuditsky.cardatastorage.core.Const.KEY_IMAGE
import com.alexeyyuditsky.cardatastorage.core.Const.KEY_MODEL
import com.alexeyyuditsky.cardatastorage.core.Const.KEY_SPEED
import com.alexeyyuditsky.cardatastorage.databinding.FragmentCarsBinding
import com.alexeyyuditsky.cardatastorage.presentation.cars.CarUi
import com.alexeyyuditsky.cardatastorage.presentation.cars.adapters.CarsAdapter
import com.alexeyyuditsky.cardatastorage.presentation.cars.screens.editcar.EditCarDialogFragment
import com.alexeyyuditsky.cardatastorage.presentation.cars.FragmentRouter
import com.alexeyyuditsky.cardatastorage.presentation.cars.screens.newcar.NewCarDialogFragment
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var isSort = false
var isFilter = false

class CarsFragment : Fragment(R.layout.fragment_cars) {

    private val viewModel by viewModels<CarsViewModel>(
        factoryProducer = { (requireActivity().application as App).carsFactory() }
    )
    private val fragmentRouter get() = (requireActivity() as FragmentRouter)

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
            imageClickListener = { uri: String -> fragmentRouter.showFullscreenDialog(uri) }
        )

        binding.recyclerView.adapter = carsAdapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
        (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        return carsAdapter
    }

    private fun floatButtonListener() {
        binding.addCarFloatButton.setOnClickListener {
            fragmentRouter.showNewCarDialog()
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
                fragmentRouter.showEditCarDialog(car)
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
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
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
            viewModel.updateCar(
                id = result.getLong(KEY_ID),
                model = result.getString(KEY_MODEL, ""),
                color = result.getString(KEY_COLOR, ""),
                speed = result.getInt(KEY_SPEED),
                hp = result.getInt(KEY_HP),
                image = result.getString(KEY_IMAGE, ""),
            )
            checkBoxObserver()
        }
    }

    private fun newCarDialogListener() {
        parentFragmentManager.setFragmentResultListener(
            NewCarDialogFragment.REQUEST_KEY, this
        ) { _, result ->
            viewModel.addNewCar(
                model = result.getString(KEY_MODEL, ""),
                color = result.getString(KEY_COLOR, ""),
                speed = result.getInt(KEY_SPEED),
                hp = result.getInt(KEY_HP),
                image = result.getString(KEY_IMAGE, ""),
            )
            checkBoxObserver()
        }
    }

}