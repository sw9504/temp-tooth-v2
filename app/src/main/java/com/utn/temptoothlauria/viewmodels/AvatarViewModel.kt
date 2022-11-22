package com.utn.temptoothlauria.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private var auth: FirebaseAuth = Firebase.auth

class AvatarViewModel : ViewModel() {
    fun logOut () {
        auth.signOut()
    }
}