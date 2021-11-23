package com.example.project_infj_list.todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager
import com.applikeysolutions.cosmocalendar.settings.appearance.ConnectedDayIconPosition
import com.example.project_infj_list.adapter.TodoListAdapter
import com.example.project_infj_list.databinding.FragmentTodoBinding
import com.example.project_infj_list.model.viewmodelfactory.MainViewModelFactory
import com.example.project_infj_list.model.viewmodelfactory.TodoViewModelFactory
import com.example.project_infj_list.todo.MissionActivity.Companion.SELECTED_DAYS
import com.example.project_infj_list.viewmodel.MainViewModel
import com.example.project_infj_list.viewmodel.TodoViewModel
import java.text.SimpleDateFormat
import java.util.*


class TodoFragment : Fragment(), OnDaySelectedListener {

	private lateinit var date: String
	private val sdf = SimpleDateFormat("yyyy-MM-dd")

	// adapterInstance
	private lateinit var todoListAdapter: TodoListAdapter

	private val mainViewModel by activityViewModels<MainViewModel> {
		MainViewModelFactory(
			requireActivity().application
		)
	}
	private val todoViewModel by activityViewModels<TodoViewModel> {
		TodoViewModelFactory(
			requireActivity().application
		)
	}

	private var _binding: FragmentTodoBinding? = null
	private val binding: FragmentTodoBinding
		get() = _binding!!


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		mainViewModel.replaceName(MainViewModel.FragmentToName.TODO)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentTodoBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		todoListAdapter = TodoListAdapter()
		setCalenderView()
		openMissionActivity()
		setRecyclerView()
		addIdea()
	}

	private fun setCalenderView() = with(binding) {
		calenderView.isShowDaysOfWeekTitle = false
		calenderView.calendarOrientation = OrientationHelper.HORIZONTAL
		calenderView.connectedDaySelectedIconRes = ConnectedDayIconPosition.TOP

		// 클릭 이벤트 구현
		calenderView.selectionManager = SingleSelectionManager(this@TodoFragment)
	}

	override fun onDaySelected() {

		val days = hashSetOf<Long>()
		val selectedDay: Date = binding.calenderView.selectedDays[0].calendar.time

		try {
			date = sdf.format(selectedDay)
//			val timeInMilliseconds: Long = date.time
//			days.add(timeInMilliseconds)
			Toast.makeText(context, date, Toast.LENGTH_SHORT).show()

		} catch (e: Exception) {
			Log.e("DateException", e.message!!)
		}
		// 이벤트가 있는 날짜에 마커 추가.
		/*val connectedDay = ConnectedDays(days,)
		binding.calenderView.addConnectedDays(connectedDay)*/

		getTodoList()
	}


	private fun openMissionActivity() = with(binding) {
		if (calenderView.selectedDates.size <= 0) {
			val curDate = Date().time
			date = sdf.format(curDate)
		}
		addMissionButton.setOnClickListener {
			val intent = Intent(activity, MissionActivity::class.java)
			intent.putExtra(SELECTED_DAYS, date)
			startActivity(intent)
		}
	}

	private fun setRecyclerView() = with(binding) {
		recyclerView.run {
			adapter = todoListAdapter
			layoutManager = LinearLayoutManager(activity)
			addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
		}
	}

	//co 옵저버 이상은 아니고 LiveData 문제인듯. insert 하고 다시 호출은 되는데 새로 저장한 값 제외하고 onCreate 할때 있었던 값만 it 에 들어옴.
	private fun getTodoList() {
		todoViewModel.getTodoListCurDate(date).observe(
			viewLifecycleOwner,
			{
				todoListAdapter.submitList(it)
			}
		)
	}

	// Todo 간단하게 키보드 위에 EditText만 올리는 방법이 없을까?
	private fun addIdea() = with(binding) {
		addIdeaButton.setOnClickListener {
		}
	}

	override fun onResume() {
		super.onResume()
		mainViewModel.replaceName(MainViewModel.FragmentToName.TODO)
	}


	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}


}