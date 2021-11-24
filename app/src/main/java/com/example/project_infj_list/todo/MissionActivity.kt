package com.example.project_infj_list.todo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.project_infj_list.databinding.ActivityMissionBinding
import com.example.project_infj_list.model.viewmodelfactory.TodoViewModelFactory
import com.example.project_infj_list.viewmodel.TodoViewModel

class MissionActivity : AppCompatActivity() {

	private lateinit var date: String

	private lateinit var binding: ActivityMissionBinding
	private val todoViewModel by viewModels<TodoViewModel> { TodoViewModelFactory(application) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMissionBinding.inflate(layoutInflater)
		setContentView(binding.root)
		date = intent.getStringExtra(SELECTED_DAYS) ?: ""
		val position = intent.getIntExtra(POSITION_KEY, 0)

		setEditLiveDataObserver(position)
		saveTodo()
	}

	//co ViewModel에서 값 넘겨오는 거 에러... ViewModel에서 라이브데이터 불러오는 것에 문제가 있는 것 같음.
	// 포지션 값 받아서 해당 데이터 가져오는거 똑같은 로직인데 ViewModel에선 되고 여기선 안됨
	// ViewModel에서 데이터 받고 editTodoLiveData에다가 옮겨서 가져오는것도 여기서 null로 받아짐
	private fun setEditLiveDataObserver(position: Int) {
		if (todoViewModel.curTodoListLiveData.value?.get(position) == null) {
			Toast.makeText(this, "$position 번 으로 값 못받아옴", Toast.LENGTH_SHORT).show()
		}
	}

	private fun saveTodo() = with(binding) {
		saveButton.setOnClickListener {
			todoViewModel.insertTodo(
				// EntityInstance
				title = titleTextView.text.toString(),
				description = descriptionTextView.text.toString(),
				date = date
			)
			Log.d("roomTest", "${titleTextView.text}, ${descriptionTextView.text}, $date")
			finish()
		}
	}

	companion object {
		const val SELECTED_DAYS = "selectedDays"
		const val POSITION_KEY = "positionKey"
	}
}