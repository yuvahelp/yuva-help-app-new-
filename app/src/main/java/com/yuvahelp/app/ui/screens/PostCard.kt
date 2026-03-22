package com.yuvahelp.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yuvahelp.app.data.model.Post

@Composable
fun PostCard(post: Post, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (post.thumbnailUrl.isNotBlank()) {
                AsyncImage(
                    model = post.thumbnailUrl,
                    contentDescription = post.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(post.title, style = MaterialTheme.typography.titleMedium)
                Text(post.excerpt, style = MaterialTheme.typography.bodyMedium, maxLines = 3)
                Text(post.category, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
