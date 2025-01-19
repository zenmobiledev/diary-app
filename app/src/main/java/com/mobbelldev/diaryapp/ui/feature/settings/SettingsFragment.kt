package com.mobbelldev.diaryapp.ui.feature.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobbelldev.diaryapp.R
import com.mobbelldev.diaryapp.data.SettingItem
import com.mobbelldev.diaryapp.databinding.FragmentSettingsBinding
import com.mobbelldev.diaryapp.ui.feature.settings.adapter.SettingsAdapter

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

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

        sharedPreferences = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
        setupRecyclerView(sharedPreferences)
    }

    private fun setupRecyclerView(sharedPreferences: SharedPreferences) {
        // Create setting items
        val settingsItems = listOf(
            SettingItem(
                iconResId = R.drawable.outline_notifications_active_24,
                title = ContextCompat.getString(requireContext(), R.string.text_notification),
                hasSwitch = true
            ),
            SettingItem(
                iconResId = R.drawable.outline_exit_to_app_24,
                title = ContextCompat.getString(requireContext(), R.string.text_exit),
                hasSwitch = false
            )
        )

        // Set up recycler view
        val settingsAdapter = SettingsAdapter(settingsItems, sharedPreferences)
        with(binding.rvSettings) {
            adapter = settingsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}