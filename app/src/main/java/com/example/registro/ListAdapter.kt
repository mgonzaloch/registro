package com.example.registro

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import kotlin.coroutines.coroutineContext

class ListAdapter( private val listAdapter: List<Homeitem>,val context:Context)  : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val currentItem = listAdapter[position]

        holder.imagenView.setImageResource(currentItem.imageResource)
        holder.textViewTi.text = currentItem.texttit
        holder.textViewCo.text = currentItem.textcon

        holder.itemView.setOnClickListener {

            val model = listAdapter.get(position)

            var gtitle: String = model.texttit
            var gdescription : String = model.textcon
            var gImageView :Int = model.imageResource

            val intent = Intent(context,AnotherActivity::class.java)
            intent.putExtra("iTitle", gtitle)
            intent.putExtra("iDescription", gdescription)
            intent.putExtra("iTmageView",gImageView)

            context.startActivities(arrayOf(intent))

        }

    }
    override fun getItemCount()=listAdapter.size

    class  ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imagenView: ImageView = itemView.image_list
        val textViewTi: TextView = itemView.text_list_titel
        val textViewCo: TextView = itemView.text_list_context

    }
}