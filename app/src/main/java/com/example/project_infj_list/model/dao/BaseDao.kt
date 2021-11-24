package com.example.project_infj_list.model.dao

import androidx.room.*

@Dao
interface BaseDao<T> {
	// TodoDao
	@Insert
	fun insertTodo(t: T)

	@Update
	fun updateTodo(t: T)

	@Delete
	fun deleteTodo(t: T)

	@Delete
	fun deleteAllTodos(t: List<T>)


}