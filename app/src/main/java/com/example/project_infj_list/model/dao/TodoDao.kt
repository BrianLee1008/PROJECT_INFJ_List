package com.example.project_infj_list.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.project_infj_list.model.entity.TodoEntity

@Dao
interface TodoDao : BaseDao<TodoEntity> {
	@Query("SELECT * FROM todoEntity ORDER BY uid ASC")
	fun getAllTodoListAsLiveData(): LiveData<List<TodoEntity>>

	@Query("SELECT * FROM todoEntity ORDER BY uid ASC")
	fun getAllTodo(): List<TodoEntity>

	@Query("SELECT * FROM todoEntity where title like :title")
	fun searchTodo(title: String): LiveData<List<TodoEntity>>
}