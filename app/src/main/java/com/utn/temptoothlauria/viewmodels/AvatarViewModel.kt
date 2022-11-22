package com.utn.temptoothlauria.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject

import com.google.firebase.ktx.Firebase
import com.utn.temptoothlauria.entities.User
import com.utn.temptoothlauria.entities.Value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private var auth: FirebaseAuth = Firebase.auth
private val db = Firebase.firestore

private var COLLECTION_PATH : String = "user"

class AvatarViewModel : ViewModel() {
    var userShow : MutableLiveData<MutableList<User>?> = MutableLiveData()
    var userList : MutableList<User> = mutableListOf()

    fun logOut () {
        auth.signOut()
    }

    fun updateScreen () {
        viewModelScope.launch (Dispatchers.Main) {
            userShow.value = getUserFromDb()
        }
    }

    suspend fun getUserFromDb () : MutableList<User>{
        val queryRef = db.collection(COLLECTION_PATH)

        try {
            val users = queryRef.get().await()
            if (users != null) {
                userList.clear()
                for (user in users) {
                    userList.add(user.toObject<User>())
                }
                Log.w("Firestore", "Success!!")
            }
        }
        catch (e : Exception) {
            Log.w("Firestore", "Error getting documents")
        }
        return userList
    }

    fun getUserUid () : String {
        val user = auth.currentUser
        return user?.uid.toString()
    }
}