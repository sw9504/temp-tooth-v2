package com.utn.temptoothlauria.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await



class CreateAccViewModel : ViewModel() {
lateinit var userEmail : String
lateinit var userPassword : String
lateinit var userName : String

    fun setUserValues (email : String, name : String, password : String) {
        userEmail = email
        userPassword = password
        userName = name
    }

    fun createNewUser () {

    }

}
