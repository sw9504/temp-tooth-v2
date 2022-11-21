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
import com.utn.temptoothlauria.viewmodels.ExpandedViewModel
import com.utn.temptoothlauria.viewmodels.ListViewModel

class ExpandedFragment : Fragment() {

    companion object {
        fun newInstance() = ExpandedFragment()
    }

    private val viewModel: ExpandedViewModel by viewModels()
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

        binding.txtDate.text = viewModel.date
    }

}