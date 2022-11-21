package com.utn.temptoothlauria.entities

class Value (
    var userId : String,
    var date : String,
    var sensor1 : String,
    var sensor2 : String,
    var temp1 : Int,
    var hum1 : Int,
    var temp2 : Int,
    var hum2 : Int
) {
    constructor() : this ("","","","",0,0,0,0)

    init {
        this.userId = userId!!
        this.date = date!!
        this.sensor1 = sensor1!!
        this.sensor2 = sensor2!!
        this.temp1 = temp1!!
        this.temp2 = temp2!!
    }
}

/*
  var temperature1 : Int,
  var humidity1 : Int,
  var temperature2 : Int,
  var humidity2 : Int,
 */