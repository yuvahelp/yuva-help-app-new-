package com.yuvahelp.app.ui.screens

import android.content.Intent
import android.net.Uri
import android.text.Html
import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.yuvahelp.app.data.model.Post
import com.yuvahelp.app.ui.viewmodel.PostViewModel

@Composable
fun ArticleScreen(viewModel: PostViewModel, postId: Long) {
    val context = LocalContext.current
    val postState = remember { mutableStateOf<Post?>(null) }

    LaunchedEffect(postId) {
        postState.value = viewModel.postById(postId)
    }

    val post = postState.value ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (post.thumbnailUrl.isNotBlank()) {
            AsyncImage(model = post.thumbnailUrl, contentDescription = post.title)
        }
        Text(post.title, style = MaterialTheme.typography.headlineSmall)
        Text(post.date, style = MaterialTheme.typography.labelMedium)

        AndroidView(factory = { ctx ->
            TextView(ctx).apply {
                textSize = 16f
                text = Html.fromHtml(post.content, Html.FROM_HTML_MODE_COMPACT)
            }
        })

        Button(onClick = {
            val share = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, post.url)
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(share, "Share via"))
        }) {
            Text("Share Post")
        }

        Button(onClick = {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(post.url)))
        }) {
            Text("Open Original Article")
        }
    }
}
