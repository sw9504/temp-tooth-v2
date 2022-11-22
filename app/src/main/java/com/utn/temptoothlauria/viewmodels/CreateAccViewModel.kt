package com.utn.temptoothlauria.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.temptoothlauria.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

private val db = Firebase.firestore
private var auth: FirebaseAuth = Firebase.auth
private var COLLECTION_PATH : String = "user"

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

    fun getUserUid () : String {
        val user = auth.currentUser
        return user?.uid.toString()
    }

  fun userToFirestore () {
      val userId = getUserUid()
      val imgUrl= "https://tonyfernandeztech.files.wordpress.com/2019/04/tlc-cali-humidity-temperature.png"
      val newUser = User (userId, userName, imgUrl, userEmail)

      val queryRef = db.collection(COLLECTION_PATH)
        try {
            queryRef.add(newUser)
            Log.w("Firestore", "Success!")
        }
        catch (e : Exception) {
            Log.w("Firestore", "Error uploading")
        }
    }
}
