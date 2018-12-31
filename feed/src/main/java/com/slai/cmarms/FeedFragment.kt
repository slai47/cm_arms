package com.slai.cmarms

import android.os.Bundle
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
import com.slai.cmarms.listeners.EndlessRecyclerViewScrollListener
import com.slai.cmarms.model.Post
import com.slai.cmarms.presenters.FeedPresenter
import com.slai.cmarms.viewmodel.CmarmsViewModel
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FeedFragment : Fragment() {

    val presenter: FeedPresenter by lazy { FeedPresenter(this) }
    private val viewModel: CmarmsViewModel by lazy { CmarmsViewModel.getInstance() }

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
            adapter.addPosts(it)
        })

        setupSwipeToRefresh()

        if(viewModel.getPosts().isNullOrEmpty()){
            feed_progress.visibility = View.VISIBLE
            presenter.searchForPosts(viewModel.query, viewModel.getPostIds())
        } else {
            feed_progress.visibility = View.GONE
        }
    }

    private fun setupSwipeToRefresh() {
        feed_swipe_refresh.setOnRefreshListener {
            if(!feed_swipe_refresh.isRefreshing) {
                GlobalScope.launch {
                    viewModel.clearPosts()
                    adapter.clearPosts()
                    presenter.dispose()
                    presenter.searchForPosts(viewModel.query, viewModel.getPostIds())
                }
                feed_progress.visibility = View.VISIBLE
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
                    presenter.searchForPosts(viewModel.query, viewModel.getPostIds())
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

    fun onPostsReceived(list : ArrayList<Post>) {
        if(!list.isEmpty())
            viewModel.addPosts(list)
        else if(list.isEmpty() && adapter.posts.isNotEmpty()) {
            Snackbar.make(feed_recycler, "No more items", Snackbar.LENGTH_SHORT)
            viewModel.endOfQueue = true
        } else
            Snackbar.make(feed_recycler, "Failed to grab data", Snackbar.LENGTH_SHORT)

        feed_progress.visibility = View.GONE
    }
}