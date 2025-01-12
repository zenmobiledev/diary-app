package com.mobbelldev.diaryapp.presentation.feature.latest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobbelldev.diaryapp.data.local.database.DiaryDatabase
import com.mobbelldev.diaryapp.databinding.FragmentLatestBinding
import com.mobbelldev.diaryapp.presentation.adapter.ListDiaryAdapter

class LatestFragment : Fragment() {

    private var _binding: FragmentLatestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var db: DiaryDatabase
    private val listAdapter = ListDiaryAdapter()

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

        // Initial recycler view
        with(binding.rvDiaryLatest) {
            layoutManager = LinearLayoutManager(view.context)
            adapter = listAdapter
        }

        val data = db.diaryDao().getLatestDiaries()
        listAdapter.setData(data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}