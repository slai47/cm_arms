package com.slai.cmarms

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.slai.cmarms.adapters.FeedAdapter
import com.slai.cmarms.listeners.EndlessRecyclerViewScrollListener
import com.slai.cmarms.model.Post
import com.slai.cmarms.model.ProgressEvent
import com.slai.cmarms.viewmodel.CmarmsViewModel
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class FeedFragment : Fragment() {

    val viewModel by lazy { ViewModelProviders.of(activity!!).get(CmarmsViewModel::class.java) }

    lateinit var feedAdapter : FeedAdapter
    lateinit var manager : GridLayoutManager

    var endOfQueue : Snackbar? = null

    /**
     * The observer for posts loading in order send them to the adapter and turn off progress.
     *
     */
    var observer = Observer<MutableList<Post>> {
        // Update UI
        Log.d(FeedFragment::class.java.simpleName, "Live Data updated size = ${it.size}")

        // find the difference and only add new ones.
        val firstTime = feedAdapter.posts.isEmpty()
        if(firstTime)
            runLayoutAnimation(feed_recycler)
        feedAdapter.addPosts(it)
        if(firstTime)
            feed_recycler.scheduleLayoutAnimation()

        if(it.size != 0)
            EventBus.getDefault().post(ProgressEvent(false))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)

        setupAdapter()

        viewModel.getLivePosts().observe(this, observer)

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
        val ctx = context!!
        if(feed_recycler.adapter != null){
            val prefs = PrefUtils.getLayoutStyle(ctx)
            val resId = when(prefs){
                PrefUtils.LayoutStyle.STANDARD -> R.layout.list_feed_regular
                PrefUtils.LayoutStyle.LARGE -> R.layout.list_feed_large
                PrefUtils.LayoutStyle.SMALL -> R.layout.list_feed_small
                PrefUtils.LayoutStyle.TEXT -> R.layout.list_feed_text
            }
            if(resId != feedAdapter.resId){
                feedAdapter.resId = resId
                feedAdapter.cardWrapped = ctx
                    .getSharedPreferences(ctx.packageName + "_preferences", Context.MODE_PRIVATE)
                    .getBoolean("card_wrapped", false)
                runLayoutAnimation(feed_recycler)
                feedAdapter.notifyDataSetChanged()
                feed_recycler.scheduleLayoutAnimation()
            }
        } else {
            manager = GridLayoutManager(ctx, resources.getInteger(R.integer.column_count))

            feedAdapter = FeedAdapter(context!!)

            val prefs = PrefUtils.getLayoutStyle(context!!)
            feedAdapter.resId = when(prefs){
                PrefUtils.LayoutStyle.STANDARD -> R.layout.list_feed_regular
                PrefUtils.LayoutStyle.LARGE -> R.layout.list_feed_large
                PrefUtils.LayoutStyle.SMALL -> R.layout.list_feed_small
                PrefUtils.LayoutStyle.TEXT -> R.layout.list_feed_text
            }

            val cardWrapped = ctx
                .getSharedPreferences(ctx.packageName + "_preferences", Context.MODE_PRIVATE)
                .getBoolean("card_wrapped", false)

            val divider = DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL)
            divider.setDrawable(ContextCompat.getDrawable(ctx, R.drawable.shape_divider)!!)

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

                if(!cardWrapped)
                    addItemDecoration(divider)

                addOnScrollListener(listener)

                adapter = feedAdapter
            }
        }
    }

    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_anim_fall_in)

        recyclerView.layoutAnimation = controller
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
        viewModel.getLivePosts().removeObserver(observer)
        viewModel.dispose()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onProgressEvent(event : ProgressEvent) {
        if(event.showProgress){
            feed_progress.visibility = View.VISIBLE
        } else {
            feed_progress.visibility = View.GONE
            feed_swipe_refresh.isRefreshing = false
        }
        if(viewModel.endOfQueue){
            // Show some thing here
            endOfQueue = Snackbar.make(container, "End of Queue", Snackbar.LENGTH_INDEFINITE).setAction("Dismiss"){
                endOfQueue?.dismiss()
                endOfQueue = null
            }
            endOfQueue?.show()
        }
    }
}