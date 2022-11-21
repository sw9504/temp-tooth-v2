package com.utn.temptoothlauria.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.utn.temptoothlauria.R
import com.utn.temptoothlauria.viewmodels.CreateAccViewModel

class CreateAccFragment : Fragment() {

    companion object {
        fun newInstance() = CreateAccFragment()
    }

    private lateinit var viewModel: CreateAccViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_acc, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateAccViewModel::class.java)
        // TODO: Use the ViewModel
    }

}