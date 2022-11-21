package com.utn.temptoothlauria.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.ekn.gruzer.gaugelibrary.Range
import com.ingenieriajhr.blujhr.BluJhr
import com.utn.temptoothlauria.R
import com.utn.temptoothlauria.viewmodels.BthViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.activity_bth.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BthActivity : AppCompatActivity() {
    private val viewModel : BthViewModel by viewModels()

    lateinit var blue : BluJhr
    var measCount : Int = 0
    var devicesBluetooth = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bth)

        // Set the ranges for gauges
        temp1.addRange(viewModel.getRange1())
        temp1.minValue = 0.0
        temp1.maxValue = 50.0
        temp1.value = 0.0

        hum1.addRange(viewModel.getRange2())
        hum1.minValue = 0.0
        hum1.maxValue = 100.0
        hum1.value = 0.0

        temp2.addRange(viewModel.getRange1())
        temp2.minValue = 0.0
        temp2.maxValue = 50.0
        temp2.value = 0.0
        hum2.addRange(viewModel.getRange2())
        hum2.minValue = 0.0
        hum2.maxValue = 100.0
        hum2.value = 0.0

        // Bluetooth app
        blue = BluJhr(this)
        blue.onBluetooth()

        // Btn to go back because backPress is bugged
        btnBack.setOnClickListener {
            this.onBackPressed()
            //blue.updateStateConnectBluetooth(BluJhr.Connected.Disconnect)
            blue.closeConnection()
        }

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        btnUpload.setOnClickListener {
            scope.launch {
                viewModel.setDataToFirestore()
            }
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

                viewModel.setListString(rx)
                viewModel.uploadValues()

                viewModel.temp1.observe(this@BthActivity, Observer {
                    temp1.value = it
                })
                viewModel.hum1.observe(this@BthActivity, Observer {
                    hum1.value = it
                })
                viewModel.temp2.observe(this@BthActivity, Observer {
                    temp2.value = it
                })
                viewModel.hum2.observe(this@BthActivity, Observer {
                    hum2.value = it
                })

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