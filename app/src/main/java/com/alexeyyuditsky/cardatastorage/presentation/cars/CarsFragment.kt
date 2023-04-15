package com.alexeyyuditsky.cardatastorage.presentation.cars

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
import com.alexeyyuditsky.App
import com.alexeyyuditsky.cardatastorage.R
import com.alexeyyuditsky.cardatastorage.databinding.FragmentCarsBinding
import com.alexeyyuditsky.cardatastorage.presentation.FragmentRouter
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        val carsAdapter = CarsAdapter(
            retryClickListener = { viewModel.fetchAllCars() },
        )
        binding.recyclerView.adapter = carsAdapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
        (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        viewModel.carsLiveData.observe(viewLifecycleOwner) {
            carsAdapter.update(it)
        }

        binding.sortByBrandCheckBox.setOnClickListener {
            isSort = binding.sortByBrandCheckBox.isChecked
            checkVisible()
        }
        binding.filterBySpeedCheckBox.setOnClickListener {
            isFilter = binding.filterBySpeedCheckBox.isChecked
            checkVisible()
        }

        swipeListener(carsAdapter)
        setupEditCarDialogListener()
        setupNewCarDialogListener()
        floatButtonListener()
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

    private fun checkVisible() {
        if (isSort && isFilter) {
            viewModel.fetchSortAndFilterByCars()
        } else if (isSort && !isFilter) {
            viewModel.fetchSortByBrandCars()
        } else if (!isSort && isFilter) {
            viewModel.fetchFilterBySpeedCars()
        } else if (!isSort && !isFilter) {
            viewModel.fetchAllCars()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupEditCarDialogListener() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            EditCarDialogFragment.REQUEST_KEY, this
        ) { _, result ->
            lifecycleScope.launch {
                viewModel.updateCar(
                    id = result.getLong(EditCarDialogFragment.KEY_ID),
                    model = result.getString(EditCarDialogFragment.KEY_MODEL)!!,
                    color = result.getString(EditCarDialogFragment.KEY_COLOR)!!,
                    speed = result.getInt(EditCarDialogFragment.KEY_SPEED),
                    hp = result.getInt(EditCarDialogFragment.KEY_HP)
                )
                delay(100)
                checkVisible()
            }
        }
    }

    private fun setupNewCarDialogListener() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            NewCarDialogFragment.REQUEST_KEY, this
        ) { _, result ->
            lifecycleScope.launch {
                viewModel.addNewCar(
                    model = result.getString(NewCarDialogFragment.KEY_MODEL)!!,
                    color = result.getString(NewCarDialogFragment.KEY_COLOR)!!,
                    speed = result.getInt(NewCarDialogFragment.KEY_SPEED),
                    hp = result.getInt(NewCarDialogFragment.KEY_HP)
                )
                delay(100)
                checkVisible()
            }
        }
    }

    private companion object {
        var isSort = false
        var isFilter = false
    }

}
