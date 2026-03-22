package com.yuvahelp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.yuvahelp.app.ui.navigation.YuvaHelpApp
import com.yuvahelp.app.worker.SyncWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enqueueBackgroundSync()
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    YuvaHelpApp()
                }
            }
        }
    }

    private fun enqueueBackgroundSync() {
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(1, TimeUnit.HOURS).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            SyncWorker.UNIQUE_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            syncRequest
        )
    }
}
