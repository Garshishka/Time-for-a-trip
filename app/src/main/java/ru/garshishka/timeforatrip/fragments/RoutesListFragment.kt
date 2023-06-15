package ru.garshishka.timeforatrip.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.garshishka.timeforatrip.R
import ru.garshishka.timeforatrip.TripViewModel
import ru.garshishka.timeforatrip.api.DataFeedState
import ru.garshishka.timeforatrip.databinding.FragmentRoutesListBinding
import ru.garshishka.timeforatrip.dto.Flight
import ru.garshishka.timeforatrip.fragments.OneFlightFragment.Companion.tokenArg
import ru.garshishka.timeforatrip.utils.FlightInteractionListener
import ru.garshishka.timeforatrip.viewholder.FlightsAdapter

class RoutesListFragment : Fragment() {
    private val viewModel: TripViewModel by activityViewModels()

    private val binding: FragmentRoutesListBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private val flightInteractionListener = object : FlightInteractionListener {
        override fun onFlightClick(flight: Flight) {
            findNavController().navigate(
                R.id.action_routesListFragment_to_oneFlightFragment,
                Bundle().apply
                { tokenArg = flight.searchToken })
        }
    }

    private val adapter = FlightsAdapter(flightInteractionListener)

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
            dataState.observe(viewLifecycleOwner) {
                binding.loading.isVisible = it == DataFeedState.Loading
            }
        }
    }
}