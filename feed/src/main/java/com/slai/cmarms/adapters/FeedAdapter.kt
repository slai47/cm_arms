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
import android.net.Uri
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide


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

        if(!current.price.contains("offer", true)) holder.cost.text = "$${current.price}"
        else holder.cost.text = holder.itemView.context.getString(R.string.offer)

        holder.location.text = current.location

        holder.view.tag = position
        holder.view.setOnClickListener {
            val post = posts[it.tag as Int]
            Log.d(FeedAdapter::class.java.simpleName, post.url)
            if(post.url.isNotEmpty()) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse("http://www.armslist.com${post.url}")
                it.context.startActivity(i)
            }
        }
        holder.saleType.text = current.saleType
//        Glide.with(holder.image).load(current.image).into(holder.image)
    }

    fun addPosts(array: List<Post>){
        if(array.isNotEmpty()) {
            posts = array as ArrayList<Post>
        } else {
            posts.clear()
        }
        notifyDataSetChanged()
    }
}

class FeedViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val title = view.findViewById<TextView>(R.id.list_feed_title)
    val location = view.findViewById<TextView>(R.id.list_feed_location)
    val cost = view.findViewById<TextView>(R.id.list_feed_amount)
    val image = view.findViewById<ImageView>(R.id.list_feed_image)
    val saleType = view.findViewById<TextView>(R.id.list_feed_sale_type)

}

class PostDiff(var oldPosts : List<Post>, var newPosts : List<Post>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldPosts[oldItemPosition] == newPosts[newItemPosition]

    override fun getOldListSize(): Int = oldPosts.size

    override fun getNewListSize(): Int = newPosts.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldPosts[oldItemPosition].id == newPosts[newItemPosition].id

}