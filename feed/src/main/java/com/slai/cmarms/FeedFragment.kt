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
import com.bumptech.glide.Glide
import com.slai.cmarms.adapters.FeedAdapter
import com.slai.cmarms.listeners.EndlessRecyclerViewScrollListener
import com.slai.cmarms.model.ProgressEvent
import com.slai.cmarms.viewmodel.CmarmsViewModel
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FeedFragment : Fragment() {

    val viewModel by lazy { ViewModelProviders.of(activity!!).get(CmarmsViewModel::class.java) }

    lateinit var feedAdapter : FeedAdapter
    lateinit var manager : LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)

        setupAdapter()

        viewModel.getLivePosts().observe(this, Observer {
            // Update UI
            Log.d(FeedFragment::class.java.simpleName, "Live Data updated size = ${it.size}")
            // find the difference and only add new ones.
            feedAdapter.addPosts(it)
            // Prefetch images to use
            it.forEach {
                Glide.with(context!!).load(it.url).preload()
            }
        })

        setupSwipeToRefresh()
    }

    private fun setupSwipeToRefresh() {
        feed_swipe_refresh.setOnRefreshListener {
            GlobalScope.launch {
                viewModel.clearPosts()
                viewModel.reset(context!!)
                viewModel.getPosts()
            }
        }
    }

    private fun setupAdapter() {
        manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        feedAdapter = FeedAdapter()

        val divider = DividerItemDecoration(feed_recycler.context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(feed_recycler.context, R.drawable.shape_divider)!!)

        val listener = object : EndlessRecyclerViewScrollListener(manager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if(!viewModel.endOfQueue) {
                    viewModel.query.page++
                    feed_progress.visibility = View.VISIBLE
                    viewModel.getPosts()
                }
            }
        }

        feed_recycler.apply {

            layoutManager = manager

            addItemDecoration(divider)

            addOnScrollListener(listener)

            adapter = feedAdapter
        }
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
        viewModel.dispose()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPostsReceived(event : ProgressEvent) {
        if(event.showProgress){
            feed_progress.visibility = View.VISIBLE
        } else {
            feed_progress.visibility = View.GONE
            feed_swipe_refresh.isRefreshing = false
        }
        if(viewModel.endOfQueue){
            // Show some thing here
        }
    }
}