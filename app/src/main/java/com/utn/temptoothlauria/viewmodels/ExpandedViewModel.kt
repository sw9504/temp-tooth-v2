package com.utn.temptoothlauria.viewmodels

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.utn.temptoothlauria.entities.User
import com.utn.temptoothlauria.entities.Value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ExpandedViewModel : ViewModel() {
    private var listPosition : Int = 0
    lateinit var date : String
    lateinit var sensor1 : String
    lateinit var sensor2 : String
    var temp1 : Int = 0
    var hum1 : Int = 0
    var temp2 : Int = 0
    var hum2 : Int = 0
    lateinit var user : User

    fun setListPosition (position : Int) {
        listPosition = position
    }

    fun setValueObject (value : Value) {
        date = value.date.toDate().toString().replace(" GMT-03","")
        sensor1 = value.sensor1
        sensor2 = value.sensor2
        temp1 = value.temp1
        hum1 = value.hum1
        temp2 = value.temp2
        hum2 = value.hum2
    }
}