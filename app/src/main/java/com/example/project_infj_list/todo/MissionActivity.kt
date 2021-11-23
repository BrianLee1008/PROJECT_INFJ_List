package com.example.project_infj_list.todo

import android.os.Bundle
import android.util.Log
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

		setEditLiveDataObserver()
		saveTodo()
	}

	//co ViewModel에서 값 넘겨오는 거 에러
	private fun setEditLiveDataObserver() {
		todoViewModel.editTodoLiveData.observe(
			this, {
				binding.run {
					titleTextView.setText(it.title)
					descriptionTextView.setText(it.description)
				}
			}
		)
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
	}
}