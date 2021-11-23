package com.example.project_infj_list.d_day

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.project_infj_list.R
import com.example.project_infj_list.model.viewmodelfactory.MainViewModelFactory
import com.example.project_infj_list.viewmodel.MainViewModel


class DDayFragment : Fragment() {

	private val viewModel by activityViewModels<MainViewModel> { MainViewModelFactory(requireActivity().application) }



	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.replaceName(MainViewModel.FragmentToName.D_DAY)

	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_d_day, container, false)
	}

	override fun onResume() {
		super.onResume()
		viewModel.replaceName(MainViewModel.FragmentToName.D_DAY)
		Log.d("lifecycleTest","onResume")
	}



	override fun onDestroyView() {
		super.onDestroyView()
	}
}