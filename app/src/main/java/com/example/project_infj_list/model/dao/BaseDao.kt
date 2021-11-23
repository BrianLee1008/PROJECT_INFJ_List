package com.example.project_infj_list.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface BaseDao<T> {
	// TodoDao
	@Insert
	fun insertTodo(t: T)

	@Update
	fun updateTodo(t: T)

	@Delete
	fun deleteAllTodos(t: List<T>)


}