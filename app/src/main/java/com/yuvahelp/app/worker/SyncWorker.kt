package com.yuvahelp.app.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yuvahelp.app.data.local.AppDatabase
import com.yuvahelp.app.data.remote.ApiModule
import com.yuvahelp.app.repository.PostRepository

class SyncWorker(
    private val appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val repository = PostRepository(
            api = ApiModule.wordpressApi(),
            dao = AppDatabase.get(appContext).postDao()
        )

        return runCatching {
            val posts = repository.refreshPosts()
            posts.firstOrNull()?.let { latest ->
                if (canNotify()) {
                    showNotification(latest.title)
                }
            }
        }.fold(onSuccess = { Result.success() }, onFailure = { Result.retry() })
    }

    private fun canNotify(): Boolean {
        return android.os.Build.VERSION.SDK_INT < 33 ||
            ContextCompat.checkSelfPermission(appContext, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    }

    private fun showNotification(title: String) {
        val channelId = "yuva-updates"
        val manager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, "Yuva Help Updates", NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(appContext, channelId)
            .setSmallIcon(android.R.drawable.ic_popup_sync)
            .setContentTitle("New update on Yuva Help")
            .setContentText(title)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        manager.notify(101, notification)
    }

    companion object {
        const val UNIQUE_NAME = "yuva_help_background_sync"
    }
}
