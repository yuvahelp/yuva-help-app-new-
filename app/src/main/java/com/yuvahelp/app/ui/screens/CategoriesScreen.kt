package com.yuvahelp.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yuvahelp.app.ui.viewmodel.PostViewModel

@Composable
fun CategoriesScreen(viewModel: PostViewModel, onOpenPost: (Long) -> Unit) {
    val categories = listOf("Latest Jobs", "Results", "Admit Cards", "Government Schemes", "Education News")
    val selected = remember { mutableStateOf(categories.first()) }
    val posts by viewModel.categoryPosts(selected.value).collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Categories", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                AssistChip(
                    onClick = { selected.value = category },
                    label = { Text(category) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (selected.value == category) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(posts) { post -> PostCard(post = post) { onOpenPost(post.id) } }
        }
    }
}
