package com.utn.temptoothlauria.viewmodels

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekn.gruzer.gaugelibrary.Range
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.utn.temptoothlauria.entities.Value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private val db = Firebase.firestore
private var auth: FirebaseAuth = Firebase.auth

class BthViewModel : ViewModel() {
    private var list : List<String> = listOf()

    var temp1 : MutableLiveData<Double> = MutableLiveData()
    var hum1 : MutableLiveData<Double> = MutableLiveData()
    var temp2 : MutableLiveData<Double> = MutableLiveData()
    var hum2 : MutableLiveData<Double> = MutableLiveData()
    var userId : String = "pedro45" // test value

    fun uploadValues () {
        viewModelScope.launch (Dispatchers.Main) {
            updateValues()
        }
    }

   suspend fun updateValues () {
        if(checkValues()) {
            if (list[1] == "S1") {
                //sensorShow.text = "#SENSOR1: T = ${list[3]}°C H = ${list[5]} \n"
                temp1.value = list[3].toDouble()
                hum1.value = list[5].toDouble()
            }
            else if (list[1] == "S2") {
                //sensorShow.text = sensorShow.text.toString() + "#SENSOR2: T = ${list[3]}°C H = ${list[5]} \n"
                temp2.value = list[3].toDouble()
                hum2.value = list[5].toDouble()
                //measCount++
                //measureCount.text = measCount.toString()
            }
            else{
                //sensorShow.text = "ERROR"
            }
        }
    }

    suspend fun setDataToFirestore () {
        userId = getUserUid()
        // Date
        val date = getDate()
        var value = Value(userId,
            date,
            "T = ${temp1.value!!.toInt()}°C - H = ${hum1.value!!.toInt()}%",
            "T = ${temp2.value!!.toInt()}°C - H = ${hum2.value!!.toInt()}%",
            temp1.value!!.toInt(),hum1.value!!.toInt(),
            temp2.value!!.toInt(),hum2.value!!.toInt())

        val queryRef = db.collection("value")

        try {
            queryRef.add(value)
            Log.w("Firestore", "Success!")
        }
        catch (e : Exception) {
            Log.w("Firestore", "Error")
        }
    }

    fun getUserUid () : String {
        val user = auth.currentUser
        return user?.uid.toString()
    }

    fun getDate(): Timestamp {
        return Timestamp.now()
    }

    fun checkValues () : Boolean {
        if (list.isNullOrEmpty() || list.size < 7) {
            //sensorShow.text = "ERROR"
            return false
        }
        else return !(list[0] != "$" && list[6] != "#")
    }

    fun setListString (receivedData : String) {
        list = receivedData.split(",")
    }

    fun getRange1 () : Range {
        val range1 = Range()
        range1.color = Color.parseColor("#a60000")
        range1.from = 0.0
        range1.to = 50.0

        return range1
    }

    fun getRange2 () : Range {
        val range2 = Range()
        range2.color = Color.parseColor("#befbef")
        range2.from = 0.0
        range2.to = 50.0

        return range2
    }


    //No use
    override fun onCleared() {
        super.onCleared()
    }
}