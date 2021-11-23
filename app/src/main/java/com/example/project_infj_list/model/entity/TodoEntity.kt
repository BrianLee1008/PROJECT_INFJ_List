package com.example.project_infj_list.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoEntity")
data class TodoEntity(
	@PrimaryKey(autoGenerate = true) var uid: Int = 0,
	@ColumnInfo(name = "title") var title: String = "",
	@ColumnInfo(name = "description") var description: String = "",
	@ColumnInfo(name = "date") var date: String = ""
)