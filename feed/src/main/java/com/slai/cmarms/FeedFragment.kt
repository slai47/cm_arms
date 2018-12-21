package com.slai.cmarms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.slai.cmarms.adapters.FeedAdapter
import com.slai.cmarms.model.Post
import com.slai.cmarms.presenters.FeedPresenter
import com.slai.cmarms.viewmodel.CmarmsViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {

    lateinit var presenter: FeedPresenter
    lateinit var viewModel: CmarmsViewModel

    lateinit var adapter : FeedAdapter
    lateinit var manager : LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onResume() {
        super.onResume()
        presenter = FeedPresenter(this)

        viewModel = ViewModelProviders.of(this).get(CmarmsViewModel::class.java)
        viewModel.getPosts().observe(this, Observer {
            // Update UI
            adapter?.posts.addAll(it)
        })

        setupAdapter()

        if(viewModel.getPosts().value.isNullOrEmpty()){
            feed_progress.visibility = View.VISIBLE
            presenter.searchForPosts(viewModel.query)
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun setupAdapter() {
        manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        adapter = FeedAdapter()

        feed_recycler.layoutManager = manager
        feed_recycler.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        presenter.dispose()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun onPostsReceived(list : List<Post>) {
        viewModel.addPosts(list)
        feed_progress.visibility = View.GONE
    }
}