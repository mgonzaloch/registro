package com.example.registro

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registro.Database.UserRepository
import com.example.registro.Local.UserDataSource
import com.example.registro.Local.UserDatabase
import com.example.registro.Model.User
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_orders.*
import kotlin.collections.ArrayList

class MyOrders : AppCompatActivity() {


        private var tam = ArrayList<Homeitem>().size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)
        if (tam == 0){
            val num = 5
            val exampleList = generateDummyList(num)
            recycler_view.adapter = ListAdapter(exampleList,this)

            recycler_view.layoutManager=LinearLayoutManager(this)
            recycler_view.setHasFixedSize(true)
            tam = exampleList.size
        }



    }
    private fun generateDummyList(size: Int): List<Homeitem>{
        val list = ArrayList<Homeitem>()
        for (i in 0 until size){
            val drawable = when (i % 3){
                0 -> R.drawable.ic_restaurant_black_24dp
                1 -> R.drawable.ic_directions_bike_black_24dp
                else -> R.drawable.ic_local_shipping_black_24dp
            }
            val orders = when (i % 3){
                0 -> "Food"
                1 -> "Delivers"
                else -> "Packet"
            }

            val item = Homeitem(drawable, orders, "pedido $i")
            list += item
        }
        val a = list.size
        Toast.makeText(this,"$a", Toast.LENGTH_SHORT).show()
        return list
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fragment_clear,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override  fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId)
        {
            R.id.fragment_Clear -> deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun deleteAllUsers() {
        tam = 0
        val exampleList = generateDummyList(tam)
        recycler_view.adapter = ListAdapter(exampleList,this)
        recycler_view.layoutManager=LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }

    fun agregar(view: View) {
        tam += 1
        val exampleList = generateDummyList(tam)
        recycler_view.adapter = ListAdapter(exampleList,this)
        recycler_view.layoutManager=LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

    }




    }
