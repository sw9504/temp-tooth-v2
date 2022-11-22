package com.utn.temptoothlauria.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utn.temptoothlauria.R
import com.utn.temptoothlauria.entities.Value

class ValueAdapter (private var valueList : MutableList<Value>,
                    private var onClick : (Int) -> Unit) : RecyclerView.Adapter<ValueAdapter.ValueHolder> (){

    class ValueHolder (v: View) : RecyclerView.ViewHolder(v) {
        private var view: View
        init {
            this.view = v
        }

        fun setDate (date : String) {
            var txtDate : TextView = view.findViewById(R.id.txtDate)
            txtDate.text = date
        }

        fun setSensor1 (sensor1 : String) {
            var txtSensor1 : TextView = view.findViewById(R.id.txtSensor1)
            txtSensor1.text = sensor1
        }

        fun setSensor2 (sensor2 : String) {
            var txtSensor2 : TextView = view.findViewById(R.id.txtSensor2)
            txtSensor2.text = sensor2
        }

        fun setImg (){
            var imgUrl = "https://www.tlccalibrations.com/wp-content/uploads/2017/05/TLC-Cali-Humidity-Temperature.png"
            var imgCircle: ImageView = view.findViewById((R.id.imgCircle))

            Glide.with(view)
                .load(imgUrl)
                .into(imgCircle)
        }

        fun getCard () : CardView {
            return view.findViewById(R.id.cardValue)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValueHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_value,parent,false)
        return (ValueHolder(view))
    }

    override fun onBindViewHolder(holder: ValueHolder, position: Int) {
        holder.setDate(valueList[position].date.toDate().toString().replace(" GMT-03:00",""))
        holder.setSensor1(valueList[position].sensor1)
        holder.setSensor2(valueList[position].sensor2)
        holder.setImg()

        holder.getCard().setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return valueList.size
    }
}