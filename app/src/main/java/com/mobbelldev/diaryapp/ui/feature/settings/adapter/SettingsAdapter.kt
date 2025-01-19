package com.mobbelldev.diaryapp.ui.feature.settings.adapter

import android.app.Activity
import android.app.TimePickerDialog
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mobbelldev.diaryapp.R
import com.mobbelldev.diaryapp.data.SettingItem
import com.mobbelldev.diaryapp.databinding.ListSettingsItemBinding
import com.mobbelldev.diaryapp.ui.feature.settings.notification.NotificationReminderReceiver
import java.util.Calendar

class SettingsAdapter(
    private val settingsList: List<SettingItem>,
    private val sharedPreferences: SharedPreferences,
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {
    inner class SettingsViewHolder(private val binding: ListSettingsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = itemView.context
        fun bind(item: SettingItem) {
            with(binding) {
                val alarmTime = sharedPreferences.getString("ALARM_TIME", null)
                val formatedDate =
                    "${context.getString(R.string.text_notification)}: $alarmTime"
                // Set icon dan title
                itemIcon.setImageResource(item.iconResId)
                itemText.text = item.title

                if (item.title == context.getString(R.string.text_notification)) {
                    // If there's a saved alarm time, update the text
                    alarmTime?.let {
                        itemText.text = formatedDate
                    }
                }

                with(itemSwitch) {
                    isVisible = item.hasSwitch

                    // Set switch listener
                    isChecked = sharedPreferences.getBoolean(item.title, false)
                    setOnCheckedChangeListener { _, isChecked ->
                        sharedPreferences.edit().putBoolean(item.title, isChecked).apply()
                        // with a switch
                        if (isChecked) {
                            setAlarmTime()
                        } else {
                            binding.itemText.text = item.title
                            cancelAlarm()
                        }
                    }
                }
            }

            // Without a switch
            if (!item.hasSwitch) {
                itemView.setOnClickListener {
                    (context as Activity).finish()
                    Toast.makeText(context, "Exit App", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun setAlarmTime() {
            val calendar = Calendar.getInstance()
            val h = calendar.get(Calendar.HOUR_OF_DAY)
            val m = calendar.get(Calendar.MINUTE)
            val getTimePickerDialog = TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    // Set the alarm
                    val formattedTime =
                        "${context.getString(R.string.text_notification)}: $hourOfDay:$minute"
                    NotificationReminderReceiver().setAlarm(
                        context,
                        hourOfDay,
                        minute
                    )

                    // Save the selected time in SharedPreferences
                    sharedPreferences.edit()
                        .putString("ALARM_TIME", "$hourOfDay:$minute")
                        .apply()

                    // Update itemText directly in the adapter
                    binding.itemText.text = formattedTime

                    // Show toast notification
                    Toast.makeText(
                        context,
                        "Alarm set at $hourOfDay:$minute",
                        Toast.LENGTH_LONG
                    ).show()
                },
                h,
                m,
                true
            )
            getTimePickerDialog.show()
        }

        private fun cancelAlarm() {
            Toast.makeText(
                context,
                "Please turn on the switch to enable getting notifications",
                Toast.LENGTH_SHORT
            ).show()
            NotificationReminderReceiver().cancelAlarm(context)
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