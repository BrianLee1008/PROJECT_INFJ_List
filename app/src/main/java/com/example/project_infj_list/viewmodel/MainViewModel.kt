package com.example.project_infj_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project_infj_list.d_day.DDayFragment
import com.example.project_infj_list.memo.MemoFragment
import com.example.project_infj_list.model.DataRepository
import com.example.project_infj_list.todo.TodoFragment

//repoInstance
class MainViewModel(private val repository: DataRepository) : ViewModel() {

	enum class FragmentToName {
		MEMO, TODO, D_DAY
	}

	private val _nameLiveData = MutableLiveData(FragmentToName.MEMO)
	val nameLiveData: LiveData<FragmentToName>
		get() = _nameLiveData


	fun replaceName(fragmentToName: FragmentToName){
		_nameLiveData.value = fragmentToName
	}

	val fragmentList = listOf(MemoFragment(), DDayFragment(),TodoFragment())
}