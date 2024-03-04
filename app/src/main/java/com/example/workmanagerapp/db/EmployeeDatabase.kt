package com.example.workmanagerapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workmanagerapp.data.EmployeeDb

@Database(entities = [EmployeeDb::class], version = 1)
abstract class EmployeeDatabase:RoomDatabase() {

    abstract fun getDao():EmployeeDao

    companion object {

        @Volatile
        private var instance: EmployeeDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { it ->
                instance = it
            }
        }

        private fun createDatabase(context: Context): EmployeeDatabase = Room.databaseBuilder(
            context, EmployeeDatabase::class.java, "employee_database"
        ).build()

    }

}