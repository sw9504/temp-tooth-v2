package com.utn.temptoothlauria.viewmodels

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.utn.temptoothlauria.entities.Value

class ExpandedViewModel : ViewModel() {
    private var listPosition : Int = 0
    lateinit var date : String
    lateinit var sensor1 : String
    lateinit var sensor2 : String

    fun setListPosition (position : Int) {
        listPosition = position
    }

    fun setValueObject (value : Value) {
        date = value.date
        sensor1 = value.sensor1
        sensor2 = value.sensor2
    }
}