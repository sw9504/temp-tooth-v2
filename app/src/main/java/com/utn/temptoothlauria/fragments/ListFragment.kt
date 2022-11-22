package com.utn.temptoothlauria.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utn.temptoothlauria.R
import com.utn.temptoothlauria.adapters.ValueAdapter
import com.utn.temptoothlauria.databinding.FragmentListBinding
import com.utn.temptoothlauria.viewmodels.BthViewModel
import com.utn.temptoothlauria.viewmodels.ListViewModel

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var binding : FragmentListBinding
    private val viewModel: ListViewModel by viewModels()
    private val bthViewModel: BthViewModel by viewModels()
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
        viewModel.setUserId(bthViewModel.getUserUid())
        viewModel.getValueList()

        binding.btnUpdate.setOnClickListener{
            viewModel.getValueList()
        }

        viewModel.valueList.observe(viewLifecycleOwner, Observer {
                adapter = ValueAdapter(it!!) { position ->
                    var action = ListFragmentDirections.actionListFragmentToExpandedFragment(position)
                    findNavController().navigate(action)
                }
                binding.recValue.layoutManager = LinearLayoutManager(requireContext())
                binding.recValue.adapter = adapter
        })

    }
}