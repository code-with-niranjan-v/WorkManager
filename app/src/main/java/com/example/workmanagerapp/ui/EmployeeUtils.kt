package com.example.workmanagerapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.workmanagerapp.data.EmployeeDb
import com.example.workmanagerapp.databinding.EmployeeItemsBinding

class EmployeeViewHolder(private val binding: EmployeeItemsBinding):ViewHolder(binding.root){

    fun bindData(name:String){
        binding.tvName.text = name
    }

}

class EmployeeAdapter(private val listOfEmployees:List<EmployeeDb>):Adapter<EmployeeViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = EmployeeItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EmployeeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listOfEmployees.size
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bindData(listOfEmployees[position].employeeName)
    }

}