package com.example.project_infj_list.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.project_infj_list.model.dao.TodoDao
import com.example.project_infj_list.model.entity.TodoEntity

@Database(entities = [TodoEntity::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
	abstract fun todoDao(): TodoDao
}