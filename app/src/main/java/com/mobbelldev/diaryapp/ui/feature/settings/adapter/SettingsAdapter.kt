package com.mobbelldev.diaryapp.ui.feature.settings.adapter

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mobbelldev.diaryapp.data.SettingItem
import com.mobbelldev.diaryapp.databinding.ListSettingsItemBinding
import com.mobbelldev.diaryapp.ui.feature.settings.OnSettingItemClickListener
import com.mobbelldev.diaryapp.ui.feature.settings.notification.NotificationReminderReceiver

class SettingsAdapter(
    private val settingsList: List<SettingItem>,
    private val sharedPreferences: SharedPreferences,
    private val onItemClickListener: OnSettingItemClickListener,
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {
    inner class SettingsViewHolder(private val binding: ListSettingsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SettingItem) {
            // Set icon dan title
            binding.itemIcon.setImageResource(item.iconResId)
            binding.itemText.text = item.title
            binding.itemSwitch.isVisible = item.hasSwitch

            // Set switch listener
            binding.itemSwitch.isChecked = sharedPreferences.getBoolean(item.title, false)
            binding.itemSwitch.setOnCheckedChangeListener { _, isChecked ->
                sharedPreferences.edit().putBoolean(item.title, isChecked).apply()
                // with a switch
                item.isSwitchOn = isChecked
                if (item.isSwitchOn) {
                    onItemClickListener.onSettingItemClick(item)
                    println("Adapter: TRUE")
                } else {
                    Toast.makeText(
                        itemView.context,
                        "Please turn on the switch to enable this feature",
                        Toast.LENGTH_SHORT
                    ).show()
                    NotificationReminderReceiver().cancelAlarm(itemView.context)
                    println("Adapter: FALSE")
                }
            }

            // Without a switch
            if (!item.hasSwitch) {
                itemView.setOnClickListener {
                    onItemClickListener.onSettingItemClick(item)
                }
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

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) =
        holder.bind(settingsList[position])
}