package com.example.project_infj_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.project_infj_list.model.DataRepository
import com.example.project_infj_list.model.entity.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//repoInstance
class TodoViewModel(private val repository: DataRepository) : ViewModel() {

	fun getTodoListCurDate(date: String) : LiveData<List<CurDateTodoList>> =
		repository.getAllTodoAsLiveData().map { list ->
			val todoListCurDate = mutableListOf<CurDateTodoList>()
			list.forEach {
				if (date == it.date) {
					todoListCurDate.add(
						CurDateTodoList(it.uid, it.title, it.description, it.date)
					)
				}
			}
			todoListCurDate
		}

	fun insertTodo(title: String, description: String, date: String) =
		viewModelScope.launch(Dispatchers.IO) {
			val todoEntity = TodoEntity(title = title, description = description, date = date)
			repository.insertTodo(todoEntity)
		}
}

data class CurDateTodoList(
	var uid: Int = 0,
	var title: String = "",
	var description: String = "",
	var date: String = ""
)