package com.example.project_infj_list.memo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_infj_list.adapter.TodoListAdapter
import com.example.project_infj_list.databinding.FragmentMemoBinding
import com.example.project_infj_list.model.viewmodelfactory.MainViewModelFactory
import com.example.project_infj_list.model.viewmodelfactory.TodoViewModelFactory
import com.example.project_infj_list.viewmodel.MainViewModel
import com.example.project_infj_list.viewmodel.TodoViewModel

class MemoFragment : Fragment() {

	private val mainViewModel by activityViewModels<MainViewModel> {
		MainViewModelFactory(
			requireActivity().application
		)
	}

	private var _binding: FragmentMemoBinding? = null
	private val binding: FragmentMemoBinding
		get() = _binding!!


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		mainViewModel.replaceName(MainViewModel.FragmentToName.MEMO)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentMemoBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
	}


	override fun onResume() {
		super.onResume()
		mainViewModel.replaceName(MainViewModel.FragmentToName.MEMO)
	}


	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}