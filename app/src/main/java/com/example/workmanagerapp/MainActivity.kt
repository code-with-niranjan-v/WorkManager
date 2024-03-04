package com.example.workmanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import com.example.workmanagerapp.data.EmployeeDb
import com.example.workmanagerapp.databinding.ActivityMainBinding
import com.example.workmanagerapp.db.EmployeeDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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