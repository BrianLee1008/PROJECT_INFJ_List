package com.example.project_infj_list.todo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.project_infj_list.databinding.ActivityMissionBinding
import com.example.project_infj_list.model.entity.TodoEntity
import com.example.project_infj_list.model.viewmodelfactory.TodoViewModelFactory
import com.example.project_infj_list.viewmodel.TodoViewModel

class MissionActivity : AppCompatActivity() {

	private lateinit var date: String

	private lateinit var binding: ActivityMissionBinding
	private val viewModel by viewModels<TodoViewModel> { TodoViewModelFactory(application) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMissionBinding.inflate(layoutInflater)
		setContentView(binding.root)
		date = intent.getStringExtra(SELECTED_DAYS) ?: ""

		saveTodo()
	}

	private fun saveTodo() = with(binding) {
		saveButton.setOnClickListener {
			viewModel.insertTodo(
				// EntityInstance
					title = titleTextView.text.toString(),
					description = descriptionTextView.text.toString(),
					date = date

			)
			Log.d("roomTest","${titleTextView.text}, ${descriptionTextView.text}, $date")
			finish()
		}
	}

	companion object {
		const val SELECTED_DAYS = "selectedDays"
	}
}