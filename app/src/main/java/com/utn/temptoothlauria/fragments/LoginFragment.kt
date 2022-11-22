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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.utn.temptoothlauria.R
import com.utn.temptoothlauria.databinding.FragmentLoginBinding
import com.utn.temptoothlauria.viewmodels.LoginViewModel

private var auth: FirebaseAuth = Firebase.auth

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }
    private lateinit var binding : FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater,container,false)

        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomBar)
        view.visibility = View.INVISIBLE

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if (viewModel.checkLogin()){
            val action = LoginFragmentDirections.actionLoginFragmentToSelectorFragment()
            findNavController().navigate(action)
        }

        binding.btnLogin.setOnClickListener {

            var email : String = binding.mMail.editText?.text.toString()
            var password : String = binding.mPassword.editText?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        val action = LoginFragmentDirections.actionLoginFragmentToSelectorFragment()
                        findNavController().navigate(action)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(activity, "Input email and password.",Toast.LENGTH_SHORT).show()
            }
        }

        binding.txtCreateAcc.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToCreateAccFragment()
            findNavController().navigate(action)
        }

        binding.txtRecovery.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRecoveryFragment()
            findNavController().navigate(action)
        }
    }

}