package com.utn.temptoothlauria.fragments

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.utn.temptoothlauria.R
import com.utn.temptoothlauria.databinding.FragmentCreateAccBinding
import com.utn.temptoothlauria.viewmodels.CreateAccViewModel
import kotlinx.android.synthetic.main.fragment_create_acc.view.*
private var auth: FirebaseAuth = Firebase.auth

class CreateAccFragment : Fragment() {

    companion object {
        fun newInstance() = CreateAccFragment()
    }

    private lateinit var binding : FragmentCreateAccBinding
    private val viewModel: CreateAccViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateAccBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnCreate.setOnClickListener {
            viewModel.setUserValues(binding.inputEmail.editText?.text.toString(),
            binding.inputName.editText?.text.toString(),
            binding.inputPassword.editText?.text.toString())

            var email : String = viewModel.userEmail
            var password : String = viewModel.userPassword

            if (email.isNotEmpty() && email.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {  task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        findNavController().navigateUp()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                Toast.makeText(activity, "Input email and password.",Toast.LENGTH_SHORT).show()

            }
        }
    }
}