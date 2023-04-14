package com.alexeyyuditsky.cardatastorage.presentation.cars

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.SimpleItemAnimator
import com.alexeyyuditsky.App
import com.alexeyyuditsky.cardatastorage.R
import com.alexeyyuditsky.cardatastorage.core.log
import com.alexeyyuditsky.cardatastorage.databinding.FragmentCarsBinding

class CarsFragment : Fragment(R.layout.fragment_cars) {

    private val viewModel by viewModels<CarsViewModel>(
        factoryProducer = { (requireActivity().application as App).carsFactory() }
    )

    private var _binding: FragmentCarsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCarsBinding.bind(view)

        val carsAdapter = CarsAdapter { viewModel.fetchAllCars() }
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

    }

    private fun checkVisible() {
        if (isSort && isFilter) {
            log("isSort = $isSort & isFilter = $isFilter")
            viewModel.fetchSortAndFilterByCars()
        } else if (isSort && !isFilter) {
            log("isSort = $isSort & isFilter = $isFilter")
            viewModel.fetchSortByBrandCars()
        } else if (!isSort && isFilter) {
            viewModel.fetchFilterBySpeedCars()
            log("isSort = $isSort & isFilter = $isFilter")
        } else if (!isSort && !isFilter) {
            log("isSort = $isSort & isFilter = $isFilter")
            viewModel.fetchAllCars()
        }
    }

    private companion object {
        var isSort = false
        var isFilter = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
