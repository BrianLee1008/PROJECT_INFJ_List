package com.example.project_infj_list.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(fragmentActivity : FragmentActivity) :  FragmentStateAdapter(fragmentActivity){

	var fragmentList = listOf<Fragment>()

	override fun getItemCount(): Int =
		fragmentList.size

	override fun createFragment(position: Int): Fragment =
		fragmentList[position]


}