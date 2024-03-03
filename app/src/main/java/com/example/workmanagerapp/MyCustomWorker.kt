package com.example.workmanagerapp

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.concurrent.thread

class MyCustomWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
):Worker(context,workerParameters) {
    override fun doWork(): Result {
        for (i in 1..10){
            Thread.sleep(2000)
            Log.e("MyWork","Work Done Successfully!")
        }
        return Result.success()
    }
}