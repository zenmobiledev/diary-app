package com.mobbelldev.diaryapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mobbelldev.diaryapp.data.model.SettingItem
import com.mobbelldev.diaryapp.databinding.ListSettingsItemBinding
import com.mobbelldev.diaryapp.presentation.feature.settings.OnSettingItemClickListener

class SettingsAdapter(
    private val settingsList: List<SettingItem>,
    private val context: Context,
    private val onItemClickListener: OnSettingItemClickListener,
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {
    inner class SettingsViewHolder(val binding: ListSettingsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SettingItem) {
            // Set icon dan title
            binding.itemIcon.setImageResource(item.iconResId)
            binding.itemText.text = item.title

            // Handle switch visibility
            if (item.hasSwitch) {
                binding.itemSwitch.visibility = View.VISIBLE
                binding.itemSwitch.setOnCheckedChangeListener { _, isChecked ->
                    Toast.makeText(
                        context,
                        "${item.title} is ${if (isChecked) "enabled" else "disabled"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                binding.itemSwitch.visibility = View.GONE
            }

            // Set onClickListener for the item
            itemView.setOnClickListener {
                onItemClickListener.onSettingItemClick(item) // Trigger item click
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder =
        SettingsViewHolder(
            ListSettingsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = settingsList.size

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val item = settingsList[position]
        holder.bind(item)
    }
}