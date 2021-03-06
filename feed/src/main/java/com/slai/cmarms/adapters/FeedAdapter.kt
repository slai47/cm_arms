package com.slai.cmarms.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.slai.cmarms.R
import com.slai.cmarms.model.Post


class FeedAdapter(val context: Context) : RecyclerView.Adapter<FeedViewHolder>() {

    var posts = ArrayList<Post>()

    var resId = R.layout.list_feed_regular

    var cardWrapped : Boolean = context
        .getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_PRIVATE)
        .getBoolean("card_wrapped", false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return if(!cardWrapped)
            FeedViewHolder(LayoutInflater.from(parent.context).inflate(resId, parent, false))
        else {
            val wrapper = LayoutInflater.from(context).inflate(R.layout.snippet_card_wrapper, parent, false)
            val layout = LayoutInflater.from(context).inflate(resId, parent, false)
            (wrapper.findViewById(R.id.card_wrapper) as CardView).addView(layout)
            FeedViewHolder(wrapper)
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val current = posts.get(position)
        holder.title.text = current.title

        if(current.price.contains("want", true))
            holder.cost.text = "${current.price}"
        else if(!current.price.contains("offer", true))
            holder.cost.text = "$${current.price}"
        else
            holder.cost.text = holder.itemView.context.getString(R.string.offer)

        holder.location.text = current.location

        holder.view.tag = position
        holder.view.setOnClickListener {
            val post = posts[it.tag as Int]
            Log.d(FeedAdapter::class.java.simpleName, post.url)
            if(post.url.isNotEmpty()) {
                val i = Intent(Intent.ACTION_VIEW)
                Log.d(FeedAdapter::class.java.simpleName, "http://www.armslist.com${post.url}")
                i.data = Uri.parse("http://www.armslist.com${post.url}")
                it.context.startActivity(i)
            }
        }
        holder.saleType.text = current.saleType

        if(resId != R.layout.list_feed_text)
            Glide.with(holder.image).load(current.image).transition(GenericTransitionOptions.with(R.anim.fade_in)).into(holder.image)

        holder.premium.visibility = if(current.premium) View.VISIBLE
                                    else View.INVISIBLE
    }

    fun addPosts(array: List<Post>){
        if(array.isNotEmpty()) {
            val endIndex = posts.size
            val newItems = if(posts.size > 0) array.filter { !posts.contains(it) }
                                        else array
            posts.addAll(newItems)
            notifyItemRangeChanged(endIndex, posts.size)
        } else {
            posts.clear()
            notifyDataSetChanged()
        }
    }
}

class FeedViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val title = view.findViewById<TextView>(R.id.list_feed_title)
    val location = view.findViewById<TextView>(R.id.list_feed_location)
    val cost = view.findViewById<TextView>(R.id.list_feed_amount)
    val image = view.findViewById<ImageView>(R.id.list_feed_image)
    val saleType = view.findViewById<TextView>(R.id.list_feed_sale_type)
    val premium = view.findViewById<TextView>(R.id.list_feed_premium)

}

class PostDiff(var oldPosts : List<Post>, var newPosts : List<Post>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldPosts[oldItemPosition] == newPosts[newItemPosition]

    override fun getOldListSize(): Int = oldPosts.size

    override fun getNewListSize(): Int = newPosts.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val post = oldPosts[oldItemPosition]
        val newPost = newPosts[newItemPosition]
       return post.id == newPost.id
            && post.title == newPost.title
            && post.url == post.url
    }

}