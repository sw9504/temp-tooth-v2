package com.utn.temptoothlauria.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utn.temptoothlauria.R
import com.utn.temptoothlauria.databinding.FragmentExpandedBinding
import com.utn.temptoothlauria.entities.Value
import com.utn.temptoothlauria.viewmodels.BthViewModel
import com.utn.temptoothlauria.viewmodels.ExpandedViewModel
import com.utn.temptoothlauria.viewmodels.ListViewModel
import kotlinx.android.synthetic.main.activity_bth.*

class ExpandedFragment : Fragment() {

    companion object {
        fun newInstance() = ExpandedFragment()
    }

    private val viewModel: ExpandedViewModel by viewModels()
    private val bthViewModel: BthViewModel by viewModels()

    private val listViewModel : ListViewModel by viewModels()

    private lateinit var binding : FragmentExpandedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpandedBinding.inflate(inflater, container, false)

        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomBar)
        view.visibility = View.INVISIBLE

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        var listPosition = ExpandedFragmentArgs.fromBundle(requireArguments()).listPosition
        viewModel.setListPosition(listPosition)

        viewModel.setValueObject(listViewModel.getObject(listPosition))

        // Set the ranges for gauges
        temp1.addRange(bthViewModel.getRange1())
        temp1.minValue = 0.0
        temp1.maxValue = 50.0
        temp1.value = 0.0

        hum1.addRange(bthViewModel.getRange2())
        hum1.minValue = 0.0
        hum1.maxValue = 100.0
        hum1.value = 0.0

        temp2.addRange(bthViewModel.getRange1())
        temp2.minValue = 0.0
        temp2.maxValue = 50.0
        temp2.value = 0.0
        hum2.addRange(bthViewModel.getRange2())
        hum2.minValue = 0.0
        hum2.maxValue = 100.0
        hum2.value = 0.0

        binding.txtDate.text = viewModel.date
        binding.temp1.value = viewModel.temp1.toDouble()
        binding.hum1.value = viewModel.hum1.toDouble()
        binding.temp2.value = viewModel.temp2.toDouble()
        binding.hum2.value = viewModel.hum2.toDouble()
    }
}