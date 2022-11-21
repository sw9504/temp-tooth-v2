package com.utn.temptoothlauria.fragments

import android.content.ContentValues
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.temptoothlauria.R
import com.utn.temptoothlauria.adapters.ValueAdapter
import com.utn.temptoothlauria.databinding.FragmentListBinding
import com.utn.temptoothlauria.entities.Value
import com.utn.temptoothlauria.viewmodels.ListViewModel

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var binding : FragmentListBinding
    private val viewModel: ListViewModel by viewModels()
    lateinit var adapter : ValueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)

        // Disable bottomBar on this fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomBar)
        view.visibility = View.VISIBLE
        //
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getValueList()

        binding.btnUpdate.setOnClickListener{
            viewModel.getValueList()
        }

            viewModel.valueList.observe(viewLifecycleOwner, Observer {
                adapter = ValueAdapter(it) { position ->
                    var action = ListFragmentDirections.actionListFragmentToExpandedFragment()
                    findNavController().navigate(action)
                }
                binding.recValue.layoutManager = LinearLayoutManager(requireContext())
                binding.recValue.adapter = adapter
            })
    }
}