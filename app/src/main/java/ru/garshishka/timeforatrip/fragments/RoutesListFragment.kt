package ru.garshishka.timeforatrip.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.garshishka.timeforatrip.TripViewModel
import ru.garshishka.timeforatrip.databinding.FragmentRoutesListBinding

class RoutesListFragment : Fragment() {
    private val viewModel: TripViewModel by activityViewModels()

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
            testButton.setOnClickListener {
                viewModel.load()
            }
        }

        viewModel.tripsData.observe(viewLifecycleOwner) {
            binding.testText.text = it
        }
    }

}