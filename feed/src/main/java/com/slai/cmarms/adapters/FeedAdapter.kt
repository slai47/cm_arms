package com.slai.cmarms.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.slai.cmarms.R
import com.slai.cmarms.model.Post
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent.ACTION_VIEW
import android.net.Uri


class FeedAdapter : RecyclerView.Adapter<FeedViewHolder>() {

    var posts = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_feed_regular, parent, false))
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val current = posts.get(position)
        holder.title.text = current.title
        holder.cost.text = "$${current.price}"
        holder.location.text = current.location

        holder.view.tag = position
        holder.view.setOnClickListener {
            val post = posts[it.tag as Int]
            Log.d(FeedAdapter::class.java.simpleName, post.url)
            if(post.url.isNotEmpty()) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(post.url)
                it.context.startActivity(i)
            }
        }
    }

    fun addPosts(array: List<Post>){
        val oldEnd = posts.size
        posts.addAll(array)
        notifyItemRangeChanged(oldEnd, posts.size)
    }

    fun clearPosts() {
        posts = ArrayList()
        notifyDataSetChanged()
    }
}

class FeedViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val title = view.findViewById<TextView>(R.id.list_feed_title)
    val location = view.findViewById<TextView>(R.id.list_feed_location)
    val cost = view.findViewById<TextView>(R.id.list_feed_amount)

}