package com.utn.temptoothlauria.viewmodels

import androidx.lifecycle.ViewModel
import com.utn.temptoothlauria.entities.Value

class ListViewModel : ViewModel() {

    fun getValueList () : MutableList<Value> {
        var valueList : MutableList<Value> = mutableListOf()

        valueList.add(Value("1234","domingo",
            29,49,"aaaa",
            30,48,"aaaaa"
        ))
        valueList.add(Value("12345","domingo",
            29,49,"aaaa",
            30,48,"aaaaa"
        ))
        valueList.add(Value("1234","domingo",
            29,49,"aaaa",
            30,48,"aaaaa"
        ))

        return valueList
    }
}