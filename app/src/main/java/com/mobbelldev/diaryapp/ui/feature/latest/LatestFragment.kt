package com.mobbelldev.diaryapp.ui.feature.latest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mobbelldev.diaryapp.R
import com.mobbelldev.diaryapp.data.DiaryDatabase
import com.mobbelldev.diaryapp.data.DiaryEntity
import com.mobbelldev.diaryapp.databinding.FragmentLatestBinding

class LatestFragment : Fragment() {

    private var _binding: FragmentLatestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var db: DiaryDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLatestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initial database
        db = DiaryDatabase.getInstance(view.context)

        val latestDiary = db.diaryDao().getLatestDiaries()
        displayDiary(latestDiary)
    }

    private fun displayDiary(diaryEntity: DiaryEntity?) {
        if (diaryEntity != null) {
            binding.tvDate.text = diaryEntity.date
            binding.tvTitle.text = diaryEntity.title
            binding.tvDescription.text = diaryEntity.description
        } else {
            binding.tvTitle.text = ContextCompat.getString(requireContext(), R.string.text_no_diary)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}