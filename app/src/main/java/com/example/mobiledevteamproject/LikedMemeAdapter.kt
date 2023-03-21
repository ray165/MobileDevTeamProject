package com.example.mobiledevteamproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class LikedMemeAdapter(private val mList: List<String>) :
    RecyclerView.Adapter<LikedMemeAdapter.ViewHolder>() {

    // Holds the views
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.textView_liked_meme_date)
        val memeImageView: ImageView = itemView.findViewById(R.id.imageView_liked_meme_fragment)
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_liked_meme, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        var meme = mList[position]

//        holder.dateTextView.text = meme.name
//        holder.memeImageView.setImageResource()
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

}