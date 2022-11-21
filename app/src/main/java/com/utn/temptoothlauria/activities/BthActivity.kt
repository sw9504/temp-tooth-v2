package com.utn.temptoothlauria.activities

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ekn.gruzer.gaugelibrary.Range
import com.ingenieriajhr.blujhr.BluJhr
import com.utn.temptoothlauria.R
import kotlinx.android.synthetic.main.activity_bth.*

class BthActivity : AppCompatActivity() {

    lateinit var blue : BluJhr
    var measCount : Int = 0
    var devicesBluetooth = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bth)

        val range1 = Range()
        range1.color = Color.parseColor("#a60000")
        range1.from = 0.0
        range1.to = 50.0
        temp1.addRange(range1)
        temp1.minValue = 0.0
        temp1.maxValue = 50.0
        temp1.value = 0.0

        val range2 = Range()
        range2.color = Color.parseColor("#befbef")
        range2.from = 0.0
        range2.to = 50.0
        hum1.addRange(range2)
        hum1.minValue = 0.0
        hum1.maxValue = 100.0
        hum1.value = 0.0

        temp2.addRange(range1)
        temp2.minValue = 0.0
        temp2.maxValue = 50.0
        temp2.value = 0.0
        hum2.addRange(range2)
        hum2.minValue = 0.0
        hum2.maxValue = 100.0
        hum2.value = 0.0

        blue = BluJhr(this)
        blue.onBluetooth()

        btnBack.setOnClickListener {
            this.onBackPressed()
            //blue.updateStateConnectBluetooth(BluJhr.Connected.Disconnect)
            blue.closeConnection()
        }

        listDeviceBluetooth.setOnItemClickListener { adapterView, view, i, l ->
            if (devicesBluetooth.isNotEmpty()) {
                blue.connect(devicesBluetooth[i])
                blue.setDataLoadFinishedListener(object: BluJhr.ConnectedBluetooth{
                    override fun onConnectState(state: BluJhr.Connected) {
                        when(state) {
                            BluJhr.Connected.True -> {
                                Toast.makeText(applicationContext,"True", Toast.LENGTH_SHORT).show()
                                listDeviceBluetooth.visibility = View.GONE
                                viewConn.visibility = View.VISIBLE
                                rxReceived()
                            }

                            BluJhr.Connected.Pending -> {
                                Toast.makeText(applicationContext,"Pending", Toast.LENGTH_SHORT).show()
                            }

                            BluJhr.Connected.False -> {
                                Toast.makeText(applicationContext,"False", Toast.LENGTH_SHORT).show()
                            }

                            BluJhr.Connected.Disconnect -> {
                                Toast.makeText(applicationContext,"Disconnect", Toast.LENGTH_SHORT).show()
                                listDeviceBluetooth.visibility = View.VISIBLE
                                viewConn.visibility = View.GONE
                            }
                        }
                    }
                })
            }
        }
    }

    // Here goes another functions
    private fun rxReceived() {
        blue.loadDateRx(object: BluJhr.ReceivedData{
            override fun rxDate(rx: String) {
                //consola.text = consola.text.toString()+rx
                //consola.text = rx
                //sensorShow.text = rx

                var list : List<String> = rx.split(",")

                if (list.isNullOrEmpty() || list.size < 7)
                //sensorShow.text = "ERROR"
                else if ((list[0] != "$" && list[6] != "#"))
                //sensorShow.text = "ERROR"
                else if (list[1] == "S1") {
                    sensorShow.text = "#SENSOR1: T = ${list[3]}°C H = ${list[5]} \n"
                    temp1.value = list[3].toDouble()
                    hum1.value = list[5].toDouble()
                }
                else if (list[1] == "S2") {
                    sensorShow.text = sensorShow.text.toString() + "#SENSOR2: T = ${list[3]}°C H = ${list[5]} \n"
                    temp2.value = list[3].toDouble()
                    hum2.value = list[5].toDouble()
                    measCount++
                    measureCount.text = measCount.toString()
                }
                else
                    sensorShow.text = "ERROR"
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (blue.checkPermissions(requestCode,grantResults)){
            Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show()
            blue.initializeBluetooth()
        }else{
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.S){
                blue.initializeBluetooth()
            }else{
                Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!blue.stateBluetoooth() && requestCode == 100){
            blue.initializeBluetooth()
        }else{
            if (requestCode == 100){
                devicesBluetooth = blue.deviceBluetooth()
                if (devicesBluetooth.isNotEmpty()){
                    val adapter = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,devicesBluetooth)
                    listDeviceBluetooth.adapter = adapter
                }else{
                    Toast.makeText(this, "No tienes vinculados dispositivos", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}