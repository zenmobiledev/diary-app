package com.mobbelldev.diaryapp.presentation.feature.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobbelldev.diaryapp.R
import com.mobbelldev.diaryapp.data.model.SettingItem
import com.mobbelldev.diaryapp.databinding.FragmentSettingsBinding
import com.mobbelldev.diaryapp.presentation.adapter.SettingsAdapter

class SettingsFragment : Fragment(), OnSettingItemClickListener {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settingsItems = listOf(
            SettingItem(
                iconResId = R.drawable.outline_notifications_active_24,
                title = ContextCompat.getString(requireContext(), R.string.text_notification),
                hasSwitch = false
            ),
            SettingItem(
                iconResId = R.drawable.outline_dark_mode_24,
                title = ContextCompat.getString(
                    requireContext(),
                    R.string.text_dark_mode
                ),
                hasSwitch = true
            ),
            SettingItem(
                iconResId = R.drawable.outline_exit_to_app_24,
                title = ContextCompat.getString(requireContext(), R.string.text_exit),
                hasSwitch = false
            )
        )

        // Set adapter
        val settingsAdapter = SettingsAdapter(settingsItems, requireContext(), this)
        binding.rvSettings.adapter = settingsAdapter
        binding.rvSettings.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Set item click listener
    override fun onSettingItemClick(settingItem: SettingItem) {
        when (settingItem.title) {
            ContextCompat.getString(requireContext(), R.string.text_notification) -> {
                // Navigate to the notification reminder
                Toast.makeText(
                    requireContext(),
                    "Notification Reminder",
                    Toast.LENGTH_SHORT
                ).show()
            }

            ContextCompat.getString(requireContext(), R.string.text_dark_mode) -> {
                // Navigate to Dark Mode
                Toast.makeText(requireContext(), "Dark Mode", Toast.LENGTH_SHORT)
                    .show()
            }

            ContextCompat.getString(requireContext(), R.string.text_exit) -> {
                // Exit the app
                Toast.makeText(requireContext(), "Exiting app", Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }

            else -> {
                // Do nothing
            }
        }
    }
}