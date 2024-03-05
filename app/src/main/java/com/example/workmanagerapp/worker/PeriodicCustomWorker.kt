package com.example.workmanagerapp.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanagerapp.R

class PeriodicCustomWorker(
    private val context:Context,
    private val workerParameters: WorkerParameters
):Worker(
    context,
    workerParameters
) {
    private val notificationChannelId = "work_manager_channel"
    private val notificationId = 1
    override fun doWork(): Result {
        return try {
            Log.d("MyWorkPeriodic","Work Done!")
            sendNotification("Work Done!")
            Result.success()
        } catch (e: Exception) {
            Log.e("MyWorkPeriodic", "Error in doWork: ${e.message}", e)
            Result.failure()
        }

    }

    private fun sendNotification(message: String) {
        createNotificationChannel()

        val builder = NotificationCompat.Builder(context, notificationChannelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("WorkManager Notification")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "WorkManager Channel"
            val descriptionText = "Channel for WorkManager notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(notificationChannelId, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
