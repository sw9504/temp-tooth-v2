package com.utn.temptoothlauria.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.utn.temptoothlauria.R
import com.utn.temptoothlauria.databinding.FragmentSelectorBinding
import com.utn.temptoothlauria.viewmodels.SelectorViewModel

class SelectorFragment : Fragment() {
    private lateinit var binding : FragmentSelectorBinding
    private val viewModel: SelectorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectorBinding.inflate(inflater, container, false)

        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomBar)
        view.visibility = View.INVISIBLE

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.btnRealTime.setOnClickListener {
            val action = SelectorFragmentDirections.actionSelectorFragmentToBthActivity()
            findNavController().navigate(action)
        }

        binding.btnHistory.setOnClickListener {
            val action = SelectorFragmentDirections.actionSelectorFragmentToListFragment()
            findNavController().navigate(action)
        }

        // Back press callback to exit app on List
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Dialog for exit confirmation.
                    // Use material design library
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Exit TempTooth App")
                        .setMessage("Are you sure you want to quit?")
                        .setNegativeButton("NO") { dialog, which ->
                            // Nothing
                        }
                        .setPositiveButton("YES") { dialog, which ->
                            activity?.finish()
                        }
                        .show()
                }
            })
    }
}