package com.example.project_infj_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project_infj_list.databinding.ItemTodoListBinding
import com.example.project_infj_list.viewmodel.CurDateTodoList

class TodoListAdapter(
	private val onClickListener: (position: Int) -> Unit,
	private val onLongCLickListener: OnItemClickListener
) :
	ListAdapter<CurDateTodoList, TodoListAdapter.TodoViewHolder>(diffUtil) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder =
		TodoViewHolder(
			ItemTodoListBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			),
			onClickListener,
			onLongCLickListener
		)

	override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
		holder.bindViews(currentList[position])
	}

	interface OnItemClickListener {
		fun setOnLongClickListener(position: Int)
	}

	inner class TodoViewHolder(
		private val binding: ItemTodoListBinding,
		private val onClickListener: (position: Int) -> Unit,
		private val onLongCLickListener: OnItemClickListener
	) :
		RecyclerView.ViewHolder(binding.root) {

		fun bindViews(curDateTodoList: CurDateTodoList) = with(binding) {
			titleTextView.text = curDateTodoList.title

			root.setOnClickListener {
				if (adapterPosition != RecyclerView.NO_POSITION) {
					onClickListener(adapterPosition)
				}
			}
			deleteButton.setOnLongClickListener {
				if (adapterPosition != RecyclerView.NO_POSITION) {
					onLongCLickListener.setOnLongClickListener(adapterPosition)
				}
				return@setOnLongClickListener true
			}
		}

	}

	companion object {
		private val diffUtil = object : DiffUtil.ItemCallback<CurDateTodoList>() {
			override fun areItemsTheSame(
				oldItem: CurDateTodoList,
				newItem: CurDateTodoList
			): Boolean =
				oldItem.uid == newItem.uid

			override fun areContentsTheSame(
				oldItem: CurDateTodoList,
				newItem: CurDateTodoList
			): Boolean {
				return oldItem.title == newItem.title &&
						oldItem.description == newItem.description &&
						oldItem.date == newItem.date
			}
		}
	}

}