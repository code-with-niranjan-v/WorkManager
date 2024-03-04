package com.example.workmanagerapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workmanagerapp.databinding.ActivityMainBinding
import com.example.workmanagerapp.db.EmployeeDatabase
import com.example.workmanagerapp.worker.MyCustomWorker
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
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



    }
}