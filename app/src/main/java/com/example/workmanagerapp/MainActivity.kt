package com.example.workmanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import com.example.workmanagerapp.data.EmployeeDb
import com.example.workmanagerapp.databinding.ActivityMainBinding
import com.example.workmanagerapp.db.EmployeeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workRequest = OneTimeWorkRequestBuilder<MyCustomWorker>()
            .setInitialDelay(Duration.ofSeconds(10))
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                duration = Duration.ofSeconds(15)
            ).build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
        val db = EmployeeDatabase(this@MainActivity)


        db.getDao().getEmployee().observe(this){
            binding.tvData.text = it.toString()
        }



    }
}