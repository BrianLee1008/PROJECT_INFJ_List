package com.example.project_infj_list.model.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project_infj_list.model.DataRepository
import com.example.project_infj_list.viewmodel.TodoViewModel
import java.lang.IllegalArgumentException

class TodoViewModelFactory(private val application: Application)  : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
			return TodoViewModel(DataRepository(application)) as T
		}
		throw IllegalArgumentException("")
	}
}