package com.technohues.mvisample.ui.main.state

sealed class MainStateEvent {
    class GetBlogPostsEvent : MainStateEvent()

    class GetUserEvent(val userId: String) : MainStateEvent()

    class None : MainStateEvent()

}