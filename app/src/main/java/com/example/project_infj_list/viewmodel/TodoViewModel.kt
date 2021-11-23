package com.example.project_infj_list.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.project_infj_list.model.DataRepository
import com.example.project_infj_list.model.entity.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException

//repoInstance
class TodoViewModel(private val repository: DataRepository) : ViewModel() {

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
			todoListCurDate
		}

	fun insertTodo(title: String, description: String, date: String) =
		viewModelScope.launch(Dispatchers.IO) {
			val todoEntity = TodoEntity(title = title, description = description, date = date)
			repository.insertTodo(todoEntity)
		}

	private var _editTodoLiveData = MutableLiveData<CurDateTodoList>()
	val editTodoLiveData: LiveData<CurDateTodoList>
		get() = _editTodoLiveData

	//co getTodoListCurDate()말고 다른 로직 짜야할 듯. date값 null 말고 실제 데이터 들어오는 것 확인했음
	//	내 생각엔 로직 안에서 ...아닌데.. getAllTodoAsLiveData()에 문제가 있나?
	//	어차피 date나 position이나 둘다 문자열 스트링일 뿐이고 getTodoListCurDate()는 새로 호출하는 거잖아?
	//  근데 그안에선 라이브데이터가 보고있는 값과 비교해서 일치하는 값 다시 리턴하는 로직밖에 없는데... 아무 값도 배출하지 않고 null이 나온다는거는 비교할게 없다는 건가?
	//  라이브데이터를 하나 더 만들고 그거랑 비교해야되나?
	fun editTodo(position: Int, date: String) {
		val toto = date
		Log.d("testtest",toto)
		try {
			val item = getTodoListCurDate(toto).value?.get(position)
			_editTodoLiveData.value = item!!
		} catch (e : NullPointerException){
			e.printStackTrace()
			return
		}
	}
}

data class CurDateTodoList(
	var uid: Int = 0,
	var title: String = "",
	var description: String = "",
	var date: String = ""
)