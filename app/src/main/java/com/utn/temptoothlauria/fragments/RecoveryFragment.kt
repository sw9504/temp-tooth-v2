package com.utn.temptoothlauria.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.utn.temptoothlauria.R
import com.utn.temptoothlauria.databinding.FragmentRecoveryBinding
import com.utn.temptoothlauria.viewmodels.RecoveryViewModel
private var auth: FirebaseAuth = Firebase.auth

class RecoveryFragment : Fragment() {

    companion object {
        fun newInstance() = RecoveryFragment()
    }

    private lateinit var binding : FragmentRecoveryBinding
    private val viewModel: RecoveryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.btnRecovery.setOnClickListener {
            var inputEmail : String = binding.inputEmail.editText?.text.toString()

            if (inputEmail != null && inputEmail.isNotEmpty()) {
                auth.sendPasswordResetEmail(inputEmail)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            Toast.makeText(activity, "A email has been sent.",Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(activity, "An error has been ocurred.",Toast.LENGTH_SHORT).show()
                    }
            }
            else {
                Toast.makeText(activity, "Input an email.",Toast.LENGTH_SHORT).show()
            }
        }

    }
}