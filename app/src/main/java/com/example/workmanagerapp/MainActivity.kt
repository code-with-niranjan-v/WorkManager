package com.example.workmanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import java.time.Duration

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val workRequest = OneTimeWorkRequestBuilder<MyCustomWorker>()
            .setInitialDelay(Duration.ofSeconds(10))
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                duration = Duration.ofSeconds(15)
            ).build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
}