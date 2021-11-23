package com.example.project_infj_list.model.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project_infj_list.model.DataRepository
import com.example.project_infj_list.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val application: Application)  : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
			return MainViewModel(DataRepository(application)) as T
		}
		throw IllegalArgumentException("")
	}
}