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
import ru.garshishka.timeforatrip.TripViewModel
import ru.garshishka.timeforatrip.databinding.FragmentOneFlightBinding
import ru.garshishka.timeforatrip.utils.StringArg
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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
            binding.apply {
                cityText.text = "${flight.startCity} - ${flight.endCity}"
                cityCodeText.text = "${flight.startLocationCode} - ${flight.endLocationCode}"
                serviceClassText.text = flight.serviceClass
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z z")
                val formatterNew = DateTimeFormatter.ofPattern("HH:mm:ss yyyy.MM.dd Z z")
                val startDateTime = ZonedDateTime.parse(flight.startDate, formatter)
                val endDateTime = ZonedDateTime.parse(flight.endDate, formatter)
                startDate.text = startDateTime.format(formatterNew)
                endDate.text = endDateTime.format(formatterNew)
                priceText.text = "${flight.price} ₽"
                likeButton.isChecked = flight.likedByMe
                likeButton.setOnClickListener {
                    viewModel.likeFlight(flight)
                }
                backButton.setOnClickListener { findNavController().navigateUp() }
            }
        }
    }

    companion object {
        var Bundle.tokenArg by StringArg
    }
}