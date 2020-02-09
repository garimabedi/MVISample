package com.technohues.mvisample.ui.main.state

import com.technohues.mvisample.model.BlogPost
import com.technohues.mvisample.model.User

data class MainViewState(

    var blogPosts: List<BlogPost>? = null,
    var user: User? = null
)