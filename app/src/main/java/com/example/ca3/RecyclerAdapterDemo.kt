package com.example.ca3

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ca3.PetDataModel

class RecyclerAdapterDemo(
    private val list: MutableList<PetDataModel>,
    private val itemClickListener: (PetDataModel) -> Unit
) : RecyclerView.Adapter<RecyclerAdapterDemo.MyDemoHolder>() {

    class MyDemoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val type: TextView = itemView.findViewById(R.id.type)
        val img: ImageView = itemView.findViewById(R.id.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyDemoHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerlayout, parent, false)
        return MyDemoHolder(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyDemoHolder, position: Int) {
        val data = list[position]
        holder.name.text = data.getPetName()
        holder.type.text = data.getPetType()
        data.getPetImage()?.let { holder.img.setImageResource(it) }

        // Set an OnClickListener for the item
        holder.itemView.setOnClickListener {
            itemClickListener(data)
        }
    }
}
