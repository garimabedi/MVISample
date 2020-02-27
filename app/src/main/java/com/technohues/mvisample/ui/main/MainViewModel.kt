package com.technohues.mvisample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.technohues.mvisample.model.BlogPost
import com.technohues.mvisample.model.User
import com.technohues.mvisample.repository.Repository
import com.technohues.mvisample.ui.main.state.MainStateEvent
import com.technohues.mvisample.ui.main.state.MainViewState
import com.technohues.mvisample.util.AbsentLiveData
import com.technohues.mvisample.util.DataState

class MainViewModel : ViewModel() {
    var _viewState: MutableLiveData<MainViewState> = MutableLiveData()
    var _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations.switchMap(_stateEvent) { stateEvent ->
        println("DEBUG: ViewModel: StateEvent: ${stateEvent}")
        stateEvent?.let {
            handleStateEvent(stateEvent)
        }
    }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {

        when (stateEvent) {

            is MainStateEvent.GetBlogPostsEvent -> {
                return Repository.getBlogPosts()
            }

            is MainStateEvent.GetUserEvent -> {
                return Repository.getUser(stateEvent.userId)
            }

            is MainStateEvent.None -> {
                println("DEBUG: ViewModel: Detected NoneEvent...")
                return AbsentLiveData.create()
            }
        }
    }

    fun setBlogPostsData(blogPosts: List<BlogPost>) {
        val update = getCurrentViewStateOrNew()
        update.blogPosts = blogPosts
        _viewState.value = update
    }

    fun setUserData(user: User) {
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update

    }

    private fun getCurrentViewStateOrNew(): MainViewState {

        return viewState?.value.let {
            it
        } ?: MainViewState()
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }
}