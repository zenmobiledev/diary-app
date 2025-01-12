package com.mobbelldev.diaryapp.presentation.feature.settings

import com.mobbelldev.diaryapp.data.model.SettingItem

interface OnSettingItemClickListener {
    fun onSettingItemClick(settingItem: SettingItem)
}