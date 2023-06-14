package ru.garshishka.timeforatrip.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.garshishka.timeforatrip.TripViewModel
import ru.garshishka.timeforatrip.api.DataFeedState
import ru.garshishka.timeforatrip.databinding.FragmentRoutesListBinding
import ru.garshishka.timeforatrip.viewholder.FlightsAdapter

class RoutesListFragment : Fragment() {
    private val viewModel: TripViewModel by activityViewModels()
    private val adapter = FlightsAdapter()

    private val binding: FragmentRoutesListBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        subscribe()

        return binding.root
    }

    private fun subscribe() {
        binding.apply {
            flightList.adapter = adapter
        }

        viewModel.apply {
            tripsData.observe(viewLifecycleOwner) {
                adapter.submitList(it.flights)
            }
            dataState.observe(viewLifecycleOwner){
                binding.loading.isVisible = it == DataFeedState.Loading
            }
        }

    }

}