package com.slai.cmarms.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.slai.cmarms.R
import com.slai.cmarms.model.Post

class FeedAdapter : RecyclerView.Adapter<FeedViewHolder>() {

    val posts = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(View.inflate(parent.context, R.layout.list_feed_regular, parent))
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val current = posts.get(position)
        holder.text.text = current.description
    }
}

class FeedViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val text = view.findViewById<TextView>(R.id.list_feed_text)
}