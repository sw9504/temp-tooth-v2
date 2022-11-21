package com.utn.temptoothlauria.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.toObject
import com.utn.temptoothlauria.entities.Value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

private var COLLECTION_PATH : String = "value"
private var USER_FIELD : String  = "userId"
var userId : String = "pedro45"
private val db = Firebase.firestore

class ListViewModel : ViewModel() {
    var valueList : MutableLiveData<MutableList<Value>?> = MutableLiveData()

    fun getValueList () {
        viewModelScope.launch (Dispatchers.Main) {
            valueList.value = getDatafromFirestore()
        }
    }

    suspend fun getDatafromFirestore() : MutableList<Value> {
        var realList : MutableList<Value> = mutableListOf()
        val queryRef = db.collection(COLLECTION_PATH)

         try {
            val values = queryRef.whereEqualTo(USER_FIELD,userId).get().await()
             if (values != null) {
                 for (value in values) {
                     realList.add(value.toObject<Value>())
                     // Ordenar por fecha y hora
                     Log.w("Firestore", "Success! ${value.toObject<Value>()}")
                 }
             }
         }
         catch (e : Exception) {
            Log.w("Firestore", "Error getting documents")
         }
        return realList
    }
}