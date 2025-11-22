package com.iav.randomstringgenerator.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iav.randomstringgenerator.R
import com.iav.randomstringgenerator.data.remote.response.random_string_res.RandomString

class RandomStringAdapter(
    private var strings: MutableList<RandomString>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<RandomStringAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textViewString)
        val deleteButton: Button = view.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_string, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = strings[position]
        holder.textView.text = "String: ${item.value}\nLength: ${item.length}\nCreated: ${item.created}"
        holder.deleteButton.setOnClickListener { onDelete(position) }
    }

    override fun getItemCount() = strings.size
    fun addString(item: RandomString) {
        strings.add(item)
        notifyItemInserted(strings.size - 1)
    }

    fun updateList(newList: MutableList<RandomString>) {
        strings = newList
        notifyDataSetChanged()
    }
}
