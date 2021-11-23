package com.example.project_infj_list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.project_infj_list.adapter.ViewPager2Adapter
import com.example.project_infj_list.databinding.ActivityMainBinding
import com.example.project_infj_list.model.viewmodelfactory.MainViewModelFactory
import com.example.project_infj_list.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

	private lateinit var viewPager2Adapter: ViewPager2Adapter


	private val viewModel by viewModels<MainViewModel> {
		MainViewModelFactory(application = application)
	}

	private lateinit var binding: ActivityMainBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		viewPager2Adapter = ViewPager2Adapter(this)

		initViewPager2()
		setFragObserve()

		val curTime = Date().time
		val format = SimpleDateFormat("yyyy-MM-dd")
		binding.currentTimeTextView.text = format.format(curTime)

	}

	private fun initViewPager2() = with(binding) {
		viewPager2Adapter.fragmentList = viewModel.fragmentList

		viewPager2View.run {
			adapter = viewPager2Adapter
		}
	}


	// xo 5. LiveData 구독
	private fun setFragObserve() {
		viewModel.nameLiveData.observe(
			this, {
				val targetName =
					when (it) {
						MainViewModel.FragmentToName.MEMO -> "INFJ MEMO"
						MainViewModel.FragmentToName.TODO -> "INFJ TODO"
						MainViewModel.FragmentToName.D_DAY -> "INFJ D-Day"

					}
				replaceName(targetName)
			}
		)
	}

	// xo 6. Observer의 관찰대로 이벤트 로직 짜여지면 실제 UI 업데이트하는 로직
	private fun replaceName(name: String) = with(binding) {
		diaryMenuTextView.text = name

	}


}