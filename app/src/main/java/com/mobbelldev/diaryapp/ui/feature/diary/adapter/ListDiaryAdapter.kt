package com.mobbelldev.diaryapp.ui.feature.diary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobbelldev.diaryapp.data.DiaryEntity
import com.mobbelldev.diaryapp.databinding.ItemListBinding
import com.mobbelldev.diaryapp.utils.convertDate

class ListDiaryAdapter : RecyclerView.Adapter<ListDiaryAdapter.ListDiaryViewHolder>() {

    // Interface for the click listener
    interface OnClickListener {
        fun onClick(position: Int, model: DiaryEntity)
    }

    private val listDiary = mutableListOf<DiaryEntity>()

    private var onClickListener: OnClickListener? = null

    class ListDiaryViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDiaryViewHolder =
        ListDiaryViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = listDiary.size

    override fun onBindViewHolder(holder: ListDiaryViewHolder, position: Int) {
        val data = listDiary[position]
        with(holder.binding) {
            tvDate.text = data.date.convertDate()
            tvTitle.text = data.title
            tvDescription.text = data.description
        }

        // Set click listener for the item view
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, data)
        }
    }

    // Set up the data
    fun setData(list: List<DiaryEntity>) {
        listDiary.clear()
        listDiary.addAll(list)
        notifyDataSetChanged()
    }

    // Set the click listener for the adapter
    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }
}