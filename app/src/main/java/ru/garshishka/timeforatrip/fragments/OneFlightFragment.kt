package ru.garshishka.timeforatrip.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.garshishka.timeforatrip.R
import ru.garshishka.timeforatrip.TripViewModel
import ru.garshishka.timeforatrip.databinding.FragmentOneFlightBinding
import ru.garshishka.timeforatrip.utils.StringArg
import ru.garshishka.timeforatrip.utils.getTimeGlobal
import ru.garshishka.timeforatrip.utils.getTimeLocal

class OneFlightFragment : Fragment() {
    private val viewModel: TripViewModel by activityViewModels()

    private val binding: FragmentOneFlightBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val flightToken = arguments?.tokenArg

        flightToken?.let { subscribe(it)}

        return binding.root
    }

    private fun subscribe(token : String) {
        val flight = viewModel.tripsData.value?.flights?.filter { it.searchToken == token}?.firstOrNull()
        if(flight != null){
            var isGlobalTime = true

            binding.apply {
                cityText.text = "${flight.startCity} - ${flight.endCity}"
                cityCodeText.text = "${flight.startLocationCode} - ${flight.endLocationCode}"
                serviceClassText.text = flight.serviceClass
                startDate.text = getTimeGlobal(flight.startDate)
                endDate.text = getTimeGlobal(flight.endDate)
                priceText.text = "${flight.price} ₽"
                likeButton.isChecked = flight.likedByMe
                likeButton.setOnClickListener {
                    viewModel.likeFlight(flight)
                }
                backButton.setOnClickListener { findNavController().navigateUp() }
                convertTimeButton.setOnClickListener {
                    isGlobalTime = !isGlobalTime
                    if(isGlobalTime){
                        startDate.text = getTimeGlobal(flight.startDate)
                        endDate.text = getTimeGlobal(flight.endDate)
                        convertTimeButton.text = getString(R.string.to_local_time)
                    } else{
                        startDate.text = getTimeLocal(flight.startDate)
                        endDate.text = getTimeLocal(flight.endDate)
                        convertTimeButton.text = getString(R.string.to_global_time)
                    }
                }
            }
        }
    }

    companion object {
        var Bundle.tokenArg by StringArg
    }
}