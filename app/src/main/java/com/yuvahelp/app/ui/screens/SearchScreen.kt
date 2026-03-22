package com.yuvahelp.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yuvahelp.app.ui.viewmodel.PostViewModel

@Composable
fun SearchScreen(viewModel: PostViewModel, onOpenPost: (Long) -> Unit) {
    val state by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Search Articles", style = MaterialTheme.typography.headlineSmall)
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = viewModel::onSearchChange,
                label = { Text("Search jobs, results, admit cards...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }

        items(state.allPosts) { post ->
            PostCard(post = post) { onOpenPost(post.id) }
        }
    }
}
