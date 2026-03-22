package com.yuvahelp.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yuvahelp.app.ui.viewmodel.PostViewModel

@Composable
fun LatestUpdatesScreen(viewModel: PostViewModel, onOpenPost: (Long) -> Unit) {
    val state by viewModel.uiState.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Latest Updates", style = MaterialTheme.typography.headlineSmall)
        }
        items(state.allPosts.take(20)) { post ->
            PostCard(post = post) { onOpenPost(post.id) }
        }
    }
}
