package com.technohues.mvisample.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.technohues.mvisample.R
import com.technohues.mvisample.ui.main.state.MainStateEvent

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val TAG = "MainFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->

            // Handle Data
            dataState.data?.blogPosts?.let { blogPosts ->
                viewModel.setBlogPostsData(blogPosts)
            }

            dataState.data?.user?.let { user ->
                viewModel.setUserData(user)
            }

            // Handle Error
            dataState.message?.let {

            }

            // Handle Loading
            dataState.isLoading.let {

            }
        })

        viewModel.viewState.observe(this, Observer { viewState ->

            viewState.blogPosts?.let {
                println("Setting blogs to RecyclerView: ${it}")
            }

            viewState.user?.let {
                println("DEBUG: Setting user data: ${it}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_get_blogs -> triggerGetBlogPostsEvent()

            R.id.action_get_user -> triggerGetUserEvent()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetBlogPostsEvent() {
        viewModel.setStateEvent(MainStateEvent.GetBlogPostsEvent())
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(MainStateEvent.GetUserEvent("1"))
    }


}