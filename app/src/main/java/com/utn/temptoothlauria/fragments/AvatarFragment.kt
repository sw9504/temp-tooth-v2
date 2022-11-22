package com.utn.temptoothlauria.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.utn.temptoothlauria.R
import com.utn.temptoothlauria.activities.MainActivity
import com.utn.temptoothlauria.databinding.FragmentAvatarBinding
import com.utn.temptoothlauria.viewmodels.AvatarViewModel

class AvatarFragment : Fragment() {

    companion object {
        fun newInstance() = AvatarFragment()
    }

    private lateinit var binding: FragmentAvatarBinding
    private val viewModel: AvatarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAvatarBinding.inflate(inflater, container, false)

        // HIde bottom bar
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomBar)
        view.visibility = View.VISIBLE
        ///
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.btnLogOut.setOnClickListener {
                // Dialog for Logout confirmation.
                // Use material design library
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to Logout?")
                    .setNegativeButton("NO") { dialog, which ->
                        // Nothing
                    }
                    .setPositiveButton("YES") { dialog, which ->
                        //
                        // Go to LoginFragment with no back stack
                        viewModel.logOut()
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent,null)
                        activity?.finish()
                    }
                    .show()
        }
    }
}