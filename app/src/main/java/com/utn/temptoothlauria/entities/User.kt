package com.utn.temptoothlauria.entities

class User (
    var userId : String,
    var userName : String,
    var imgUrl : String,
    var userEmail : String
){
    constructor () : this ("","","","")

    init {
        this.userId = userId!!
        this.userName = userName!!
        this.imgUrl = imgUrl!!
        this.userEmail = userEmail!!
    }
}