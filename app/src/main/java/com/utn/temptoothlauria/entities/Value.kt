package com.utn.temptoothlauria.entities

class Value (
    var userId : String,
    var date : String,
    var sensor1 : String,
    var sensor2 : String
) {
    constructor() : this ("","","","")

    init {
        this.userId = userId!!
        this.date = date!!
        this.sensor1 = sensor1!!
        this.sensor2 = sensor2!!
    }
}

/*
  var temperature1 : Int,
  var humidity1 : Int,
  var temperature2 : Int,
  var humidity2 : Int,
 */