package com.example.workmanagerapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import com.example.workmanagerapp.databinding.ActivityMainBinding
import com.example.workmanagerapp.db.EmployeeDatabase
import com.example.workmanagerapp.worker.MyCustomWorker
import com.example.workmanagerapp.worker.PeriodicCustomWorker
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var db:EmployeeDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.btnWorker.setOnClickListener {
           val workRequest = OneTimeWorkRequestBuilder<MyCustomWorker>()
               .setInitialDelay(Duration.ofSeconds(10))
               .setBackoffCriteria(
                   BackoffPolicy.LINEAR,
                   duration = Duration.ofSeconds(15)
               ).build()
           WorkManager.getInstance(applicationContext).enqueue(workRequest)
       }



        db.getDao().getEmployee().observe(this){
            if (!it.isNullOrEmpty()){
                binding.tvData.visibility = View.GONE
                binding.rvEmployee.visibility = View.VISIBLE
                val adapter = EmployeeAdapter(it)
                binding.rvEmployee.layoutManager = LinearLayoutManager(this)
                binding.rvEmployee.adapter = adapter
            }
        }
        val periodicWorkRequest = PeriodicWorkRequestBuilder<PeriodicCustomWorker>(
            repeatInterval = 30,
            repeatIntervalTimeUnit = TimeUnit.MINUTES,
            flexTimeInterval = 10,
            flexTimeIntervalUnit = TimeUnit.MINUTES
        )
            .setInitialDelay(Duration.ofSeconds(5))
            .setBackoffCriteria(
            BackoffPolicy.LINEAR,
            duration = Duration.ofSeconds(15)
        ).build()
        binding.btnPeriodicWorker.setOnClickListener {
            if (binding.btnPeriodicWorker.text.toString().equals("Cancel Work")){
                WorkManager.getInstance(applicationContext).cancelUniqueWork("MyPeriodicWork")
                binding.btnPeriodicWorker.text = "Start Periodic Worker"

            }else{
                WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork("MyPeriodicWork",ExistingPeriodicWorkPolicy.KEEP,periodicWorkRequest,)

            }




        }

        WorkManager.getInstance(applicationContext).getWorkInfosForUniqueWorkLiveData("MyPeriodicWork").observe(this){ workInfos ->
            val isEnqueued = workInfos.isNotEmpty() && workInfos.all { it.state != androidx.work.WorkInfo.State.CANCELLED }
            if (isEnqueued){

                binding.btnPeriodicWorker.text = "Cancel Work"

            }
        }



    }
}