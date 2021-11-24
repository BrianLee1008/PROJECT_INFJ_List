package com.example.project_infj_list.todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager
import com.applikeysolutions.cosmocalendar.settings.appearance.ConnectedDayIconPosition
import com.example.project_infj_list.R
import com.example.project_infj_list.adapter.TodoListAdapter
import com.example.project_infj_list.databinding.FragmentTodoBinding
import com.example.project_infj_list.model.viewmodelfactory.MainViewModelFactory
import com.example.project_infj_list.model.viewmodelfactory.TodoViewModelFactory
import com.example.project_infj_list.todo.MissionActivity.Companion.SELECTED_DAYS
import com.example.project_infj_list.viewmodel.MainViewModel
import com.example.project_infj_list.viewmodel.TodoViewModel
import java.text.SimpleDateFormat
import java.util.*

/*
* Todo
*  메모 업데이트 - Update 메모 할때 LiveData 업데이트 구체적 에러 이유 찾았으니 해결해야함
*  캘린더 이벤트 마커 - 디버깅모드에선 되는데 일반 빌드에선 안됨. 대체 무슨일인고
*  디자인 개선 - 로티 적용, 캘린더 디자인, 갤린더 크기
*  인스턴스 힐트 적용 - 공부
* */


class TodoFragment : Fragment(), OnDaySelectedListener, TodoListAdapter.OnItemClickListener {

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
		todoListAdapter = TodoListAdapter(
			{ position ->
				todoViewModel.editTodo(position = position)
				val intent = Intent(activity, MissionActivity::class.java)
				startActivity(intent)
			},
			this
		)
		setCalenderView()
		openMissionActivity()
		setRecyclerView()
		checkEventMarker()
	}

	private fun setCalenderView() = with(binding) {
		calenderView.run {
			calendarOrientation = OrientationHelper.HORIZONTAL
			selectionManager = SingleSelectionManager(this@TodoFragment)
		}
	}

	override fun onDaySelected() = with(binding) {
		val selectedDay: Date = binding.calenderView.selectedDays[0].calendar.time
		try {
			date = sdf.format(selectedDay)
		} catch (e: Exception) {
			Log.e("DateException", e.message!!)
		}

		getTodoList()
	}

	// co 디버깅 모드에선 되고 일반 빌드모드에선 안됨.. 잉?
	// 이벤트가 있는 날짜에 마커 추가.
	private fun checkEventMarker() = with(binding) {
		val connectedDays = todoViewModel.checkMarker(sdf)
		calenderView.run {
			addConnectedDays(connectedDays)
			connectedDayIconRes = R.drawable.ic_circle
			connectedDayIconPosition = ConnectedDayIconPosition.BOTTOM
		}
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

	private fun getTodoList() {
		todoViewModel.getTodoListCurDate(date).observe(
			viewLifecycleOwner,
			{
				todoListAdapter.submitList(it)
			}
		)
	}

	override fun setOnLongClickListener(position: Int) {
		todoViewModel.deleteTodo(position)
	}


	override fun onResume() {
		super.onResume()
		mainViewModel.replaceName(MainViewModel.FragmentToName.TODO)
		if (binding.fab.isOpened) {
			binding.fab.close(true)
		}
	}


	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}


}