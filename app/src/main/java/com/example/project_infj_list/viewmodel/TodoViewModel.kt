package com.example.project_infj_list.viewmodel

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.*
import com.applikeysolutions.cosmocalendar.settings.lists.connected_days.ConnectedDays
import com.example.project_infj_list.model.DataRepository
import com.example.project_infj_list.model.entity.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

//repoInstance
class TodoViewModel(private val repository: DataRepository) : ViewModel() {

	private var _curTodoListLiveData = MutableLiveData<List<CurDateTodoList>>()
	private val curTodoListLiveData: LiveData<List<CurDateTodoList>>
		get() = _curTodoListLiveData

	fun getTodoListCurDate(date: String): LiveData<List<CurDateTodoList>> =
		repository.getAllTodoAsLiveData().map { list ->
			val todoListCurDate = mutableListOf<CurDateTodoList>()
			list.forEach {
				if (date == it.date) {
					todoListCurDate.add(
						CurDateTodoList(it.uid, it.title, it.description, it.date)
					)
				}
			}
			_curTodoListLiveData.value = todoListCurDate
			todoListCurDate
		}

	private var _editTodoLiveData = MutableLiveData<CurDateTodoList?>()
	val editTodoLiveData: LiveData<CurDateTodoList?>
		get() = _editTodoLiveData


	fun editTodo(position: Int) {
		val todoItem = curTodoListLiveData.value?.get(position) ?: throw Exception("데이터 없음")
		_editTodoLiveData.value = todoItem

		//co 값 잘 들어가는데? 왜 MissionActivity에서는 안들어가지는거야
		val title = editTodoLiveData.value?.title
		val description = editTodoLiveData.value?.description
		val date = editTodoLiveData.value?.date
		Log.d("editTodoLiveData", "title : $title,  description : $description, date : $date")
	}


	fun insertTodo(title: String, description: String, date: String) =
		viewModelScope.launch(Dispatchers.IO) {
			val todoEntity = TodoEntity(title = title, description = description, date = date)
			repository.insertTodo(todoEntity)
		}

	fun deleteTodo(position: Int) =
		viewModelScope.launch(Dispatchers.IO) {
			val todo = curTodoListLiveData.value?.get(position) ?: throw Exception("데이터 없음")
			repository.deleteTodo(
				TodoEntity(todo.uid, todo.title, todo.description, todo.date)
			)
		}

	private fun takeCurDateAtDB(): Set<String> {
		val todoList = mutableSetOf<String>()
		viewModelScope.launch(Dispatchers.IO) {
			if (repository.getAllTodos().isNotEmpty()) {
				repository.getAllTodos().forEach {
					todoList.add(it.date)
				}
			} else {
				return@launch
			}
		}
		return todoList
	}

	fun checkMarker(sdf: SimpleDateFormat): ConnectedDays = with(Dispatchers.IO) {
		val days = mutableSetOf<Long>()
		takeCurDateAtDB().forEach {
			val date: Long = sdf.parse(it).time
			days.add(date)
		}
		val textColor: Int = Color.WHITE
		val selectedTextColor: Int = Color.WHITE
		val disabledTextColor: Int = Color.WHITE
		return ConnectedDays(days, textColor, selectedTextColor, disabledTextColor)
	}


}

data class CurDateTodoList(
	var uid: Int = 0,
	var title: String = "",
	var description: String = "",
	var date: String = ""
)