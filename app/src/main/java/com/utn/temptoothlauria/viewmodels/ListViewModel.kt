package com.utn.temptoothlauria.viewmodels

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.toObject
import com.utn.temptoothlauria.entities.Value

var realList : MutableList<Value> = mutableListOf()

class ListViewModel : ViewModel() {
    var valueList : MutableLiveData<MutableList<Value>> = MutableLiveData()
    val db = Firebase.firestore

    fun getValueList () {
        db.collection("value")
            .get()
            .addOnSuccessListener { values ->
                valueList.value?.clear()
                if (values != null) {
                    for (value in values) {
                        realList.add(value.toObject<Value>())
                        // Ordenar por fecha y hora
                        Log.w("Firestore", "Success! ${value.toObject<Value>()}")
                    }
                    valueList.value = realList
                }
            }
            .addOnFailureListener {
                Log.w("Firestore", "Error getting documents: ")
            }
    }
}