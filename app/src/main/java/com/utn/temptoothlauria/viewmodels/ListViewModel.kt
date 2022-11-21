package com.utn.temptoothlauria.viewmodels

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.toObject
import com.utn.temptoothlauria.entities.Value

val db = Firebase.firestore

    var valueList : MutableList<Value> = mutableListOf()
class ListViewModel : ViewModel() {

    fun getValueList () : MutableList<Value> {
        db.collection("value")
            .get()
            .addOnSuccessListener { values ->
                valueList.clear()
                if (values != null) {
                    for (value in values) {
                        valueList.add(value.toObject<Value>())
                    }
                }
            }
            .addOnFailureListener {
                Log.w("Firestore", "Error getting documents: ")
            }

        return valueList
    }
}





/*
        valueList.add(Value("ys76374",
            "21/11/2022 - 8:17",
            "T = 25°C - H = 50%",
            "T = 25°C - H = 50%"
        ))

        valueList.add(Value("yos76374",
            "21/11/2022 - 8:18",
            "T = 26°C - H = 50%",
            "T = 25°C - H = 50%"
        ))

        valueList.add(Value("yos76374",
            "21/11/2022 - 8:18",
            "T = 26°C - H = 50%",
            "T = 25°C - H = 50%"
        ))
 */

/*
        val db = Firebase.firestore

        for (value in valueList) {
            val newValueRef = db.collection("value").document()
            newValueRef.set(value)
                .addOnSuccessListener {
                    Log.d("Firebase", "Succes")

                }
                .addOnFailureListener {
                    Log.d("Firebase", "Error")
                }
        }

 */

