
package com.example.registro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registro.R.id.recycler_view
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    )
            : View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val itemlist = generateDummyList(500)
       //this.recycler_view = view.findViewById(R.id.recycler_view)as RecyclerView



        return view

    }

     private fun generateDummyList(size:Int): List<Homeitem>{
        val list = ArrayList<Homeitem>()
        for (i in 0 until size){
            val drawable = when (i % 3){
                0 -> R.drawable.ic_lock_outline_black_24dp
                1 -> R.drawable.ic_directions_bike_black_24dp
                else -> R.drawable.ic_local_shipping_black_24dp
            }
            val item = Homeitem(drawable, "Item $i", "Line 2")
            list += item
        }
        return list
    }
}
