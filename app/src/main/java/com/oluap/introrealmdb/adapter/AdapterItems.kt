package com.oluap.introrealmdb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oluap.introrealmdb.R
import com.oluap.introrealmdb.model.ItemDB

/**
 * Created by Paulo Fernandes on 28,maio,2023
 */
class AdapterItems(private val items: List<ItemDB>) : RecyclerView.Adapter<AdapterItems.ViewHolderItems>() {

    class ViewHolderItems(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtIsCompleted: TextView = itemView.findViewById(R.id.txt_iscompleted)
        val txtSummary:     TextView = itemView.findViewById(R.id.txt_summary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItems {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_items, parent, false)
        return ViewHolderItems(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolderItems, position: Int) {
        holder.txtIsCompleted   .text = items[position].isComplete.toString()
        holder.txtSummary       .text = items[position].summary
    }

}