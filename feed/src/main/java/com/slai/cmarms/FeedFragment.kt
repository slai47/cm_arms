package com.slai.cmarms

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.slai.cmarms.adapters.FeedAdapter
import com.slai.cmarms.interfaces.IPostsReceived
import com.slai.cmarms.listeners.EndlessRecyclerViewScrollListener
import com.slai.cmarms.model.Post
import com.slai.cmarms.presenters.FeedPresenter
import com.slai.cmarms.viewmodel.CmarmsViewModel
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.coroutines.*

class FeedFragment : Fragment(), IPostsReceived {

    val viewModel by lazy { ViewModelProviders.of(activity!!).get(CmarmsViewModel::class.java) }
    val presenter: FeedPresenter by lazy { FeedPresenter(this) }

    lateinit var adapter : FeedAdapter
    lateinit var manager : LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onResume() {
        super.onResume()

        setupAdapter()

        viewModel.getLivePosts().observe(this, Observer {
            // Update UI
            Log.d(FeedFragment::class.java.simpleName, "Live Data updated size = ${it.size}")
            // find the difference and only add new ones.
            adapter.addPosts(it)
        })

        setupSwipeToRefresh()

        if(viewModel.getLivePosts().value.isNullOrEmpty()){
            feed_progress.visibility = View.VISIBLE
            presenter.searchForPosts(viewModel.query)
        } else {
            feed_progress.visibility = View.GONE
        }
    }

    private fun setupSwipeToRefresh() {
        feed_swipe_refresh.setOnRefreshListener {
            GlobalScope.launch {
                viewModel.clearPosts()
                viewModel.reset(context!!)
                presenter.searchForPosts(viewModel.query)
            }
        }
    }

    fun setupAdapter() {
        manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        adapter = FeedAdapter()

        feed_recycler.layoutManager = manager

        val divider = DividerItemDecoration(feed_recycler.context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(feed_recycler.context, R.drawable.shape_divider)!!)
        feed_recycler.addItemDecoration(divider)

        val listener = object : EndlessRecyclerViewScrollListener(manager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if(!viewModel.endOfQueue) {
                    viewModel.query.page++
                    feed_progress.visibility = View.VISIBLE
                    presenter.searchForPosts(viewModel.query)
                }
            }
        }
        feed_recycler.addOnScrollListener(listener)

        feed_recycler.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        presenter.dispose()
    }

    override fun onPostsReceived(list : ArrayList<Post>) {
        feed_swipe_refresh.isRefreshing = false
        if(list.isNotEmpty())
            viewModel.addPosts(list)
        else if(list.isEmpty() && adapter.posts.isNotEmpty()) {
            Snackbar.make(feed_recycler, "No more items", Snackbar.LENGTH_SHORT).show()
            viewModel.endOfQueue = true
        } else
            Snackbar.make(feed_recycler, "Failed to grab data", Snackbar.LENGTH_SHORT). show()

        feed_progress.visibility = View.GONE
    }
}