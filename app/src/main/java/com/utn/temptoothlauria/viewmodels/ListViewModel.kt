package com.utn.temptoothlauria.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.Query
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
var userId : String = "pedro45" // test value
private val db = Firebase.firestore
private var realList : MutableList<Value> = mutableListOf()


class ListViewModel : ViewModel() {
    var valueList : MutableLiveData<MutableList<Value>?> = MutableLiveData()

    fun getValueList () {
        viewModelScope.launch (Dispatchers.Main) {
            valueList.value = getDatafromFirestore()
        }
    }

    public fun getObject (position : Int) : Value {
        return realList[position]
    }

    fun setUserId (user : String) {
        userId = user
    }

    suspend fun getDatafromFirestore() : MutableList<Value> {
        val queryRef = db.collection(COLLECTION_PATH)

         try {
            val values = queryRef
                .orderBy("date", Query.Direction.ASCENDING)
                .whereEqualTo(USER_FIELD,userId)
                .get()
                .await()

             if (values != null) {
                 realList.clear()
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