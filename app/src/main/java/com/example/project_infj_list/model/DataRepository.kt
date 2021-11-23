package com.example.project_infj_list.model

import android.app.Application
import androidx.room.Room
import com.example.project_infj_list.model.database.TodoDatabase
import com.example.project_infj_list.model.entity.TodoEntity

class DataRepository(application: Application) {

	// DB Instance
	private val db =
		Room.databaseBuilder(application, TodoDatabase::class.java, TODO_DATABASE)
			.fallbackToDestructiveMigration().build()

	private val todoDao = db.todoDao()

	// TodoCRUD
	fun getAllTodoAsLiveData() = todoDao.getAllTodoListAsLiveData()

	fun insertTodo(todoEntity: TodoEntity) = todoDao.insertTodo(todoEntity)

	fun getAllTodos(): List<TodoEntity> = todoDao.getAllTodo()


	companion object {
		private const val TODO_DATABASE = "TodoDatabase"
	}
}