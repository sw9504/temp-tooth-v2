package com.utn.temptoothlauria.viewmodels

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.utn.temptoothlauria.entities.Value

class ExpandedViewModel : ViewModel() {
    private var listPosition : Int = 0
    lateinit var date : String
    lateinit var sensor1 : String
    lateinit var sensor2 : String
    var temp1 : Int = 0
    var hum1 : Int = 0
    var temp2 : Int = 0
    var hum2 : Int = 0

    fun setListPosition (position : Int) {
        listPosition = position
    }

    fun setValueObject (value : Value) {
        date = value.date
        sensor1 = value.sensor1
        sensor2 = value.sensor2
        temp1 = value.temp1
        hum1 = value.hum1
        temp2 = value.temp2
        hum2 = value.hum2
    }
}