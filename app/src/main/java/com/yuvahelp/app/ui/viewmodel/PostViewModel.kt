package com.yuvahelp.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yuvahelp.app.data.local.AppDatabase
import com.yuvahelp.app.data.model.Post
import com.yuvahelp.app.data.remote.ApiModule
import com.yuvahelp.app.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HomeUiState(
    val loading: Boolean = true,
    val allPosts: List<Post> = emptyList(),
    val searchQuery: String = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PostRepository(
        api = ApiModule.wordpressApi(),
        dao = AppDatabase.get(application).postDao()
    )

    private val refreshing = MutableStateFlow(false)
    private val searchQuery = MutableStateFlow("")

    val uiState: StateFlow<HomeUiState> = combine(
        repository.observeAll(),
        refreshing,
        searchQuery
    ) { posts, isRefreshing, query ->
        val filtered = if (query.isBlank()) posts else posts.filter {
            it.title.contains(query, ignoreCase = true) || it.excerpt.contains(query, ignoreCase = true)
        }
        HomeUiState(loading = isRefreshing && posts.isEmpty(), allPosts = filtered, searchQuery = query)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())

    init {
        refresh()
    }

    fun refresh() = viewModelScope.launch {
        refreshing.value = true
        runCatching { repository.refreshPosts() }
        refreshing.value = false
    }

    fun onSearchChange(query: String) {
        searchQuery.value = query
    }

    fun categoryPosts(category: String): StateFlow<List<Post>> {
        return repository.observeCategory(category)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    suspend fun postById(id: Long): Post? = repository.getPost(id)
}

class PostViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}
