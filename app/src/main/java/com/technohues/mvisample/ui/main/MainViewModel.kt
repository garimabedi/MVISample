package com.technohues.mvisample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.technohues.mvisample.model.BlogPost
import com.technohues.mvisample.model.User
import com.technohues.mvisample.ui.main.state.MainStateEvent
import com.technohues.mvisample.ui.main.state.MainViewState
import com.technohues.mvisample.util.AbsentLiveData

class MainViewModel : ViewModel() {
    var _viewState: MutableLiveData<MainViewState> = MutableLiveData()
    var _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<MainViewState> = Transformations.switchMap(_stateEvent) { stateEvent ->
        println("DEBUG: ViewModel: StateEvent: ${stateEvent}")
        stateEvent?.let {
            handleStateEvent(stateEvent)
        }
    }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<MainViewState> {

        when (stateEvent) {

            is MainStateEvent.GetBlogPostsEvent -> {
                return object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        val blogList: ArrayList<BlogPost> = ArrayList()
                        blogList.add(
                            BlogPost(
                                pk = 0,
                                title = "Vancouver PNE 2019",
                                body = "Here is Jess and I at the Vancouver PNE. We ate a lot of food.",
                                image = "https://cdn.open-api.xyz/open-api-static/static-blog-images/image8.jpg"
                            )
                        )
                        blogList.add(
                            BlogPost(
                                pk = 1,
                                title = "Ready for a Walk",
                                body = "Here I am at the park with my dogs Kiba and Maizy. Maizy is the smaller one and Kiba is the larger one.",
                                image = "https://cdn.open-api.xyz/open-api-static/static-blog-images/image2.jpg"
                            )
                        )

                        value = MainViewState(blogPosts = blogList)
                    }
                }
            }

            is MainStateEvent.GetUserEvent -> {

                return object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        val user = User(
                            email = "mitch@tabian.ca",
                            username = "mitch",
                            image = "https://cdn.open-api.xyz/open-api-static/static-random-images/logo_1080_1080.png"
                        )

                        value = MainViewState(user = user)
                    }
                }
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