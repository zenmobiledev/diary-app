package com.mobbelldev.diaryapp.presentation.feature.diary

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobbelldev.diaryapp.R
import com.mobbelldev.diaryapp.data.local.database.DiaryDatabase
import com.mobbelldev.diaryapp.data.local.entity.DiaryEntity
import com.mobbelldev.diaryapp.databinding.CustomAlertDialogBinding
import com.mobbelldev.diaryapp.databinding.FragmentDiaryBinding
import com.mobbelldev.diaryapp.presentation.adapter.ListDiaryAdapter
import kotlinx.coroutines.launch
import java.util.Calendar

class DiaryFragment : Fragment() {

    private var _binding: FragmentDiaryBinding? = null

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
        _binding = FragmentDiaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initial database
        db = DiaryDatabase.getInstance(view.context)

        // The menu is displayed on the toolbar
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_bar_menu, menu)

                // Set up the search icon
                val searchItem = menu.findItem(R.id.menu_search)
                val searchView =
                    searchItem?.actionView as SearchView
                setupSearchListener(searchView)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_sort -> {
                        // Showing a pop-up sorted by date
                        val popupMenu =
                            PopupMenu(
                                requireContext(),
                                requireActivity().findViewById(R.id.menu_sort)
                            )
                        popupMenu.setOnMenuItemClickListener {
                            when (it.itemId) {
                                // Latest
                                R.id.menu_asc -> {
                                    viewLifecycleOwner.lifecycleScope.launch {
                                        val data = db.diaryDao().getDiaryByDesc()
                                        listAdapter.setData(data)
                                    }
                                    Toast.makeText(
                                        view.context,
                                        "Latest",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    true
                                }

                                // Oldest
                                R.id.menu_desc -> {
                                    viewLifecycleOwner.lifecycleScope.launch {
                                        val data = db.diaryDao().getDiaryByAsc()
                                        listAdapter.setData(data)
                                    }
                                    Toast.makeText(
                                        view.context,
                                        "Oldest",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    true
                                }

                                else -> false
                            }
                        }
                        popupMenu.inflate(R.menu.sort_menu)
                        popupMenu.show()
                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Initial recycler view
        with(binding.rvDiary) {
            layoutManager = LinearLayoutManager(view.context)
            adapter = listAdapter
        }

        // Get data
        getData()

        // Add data
        addData()

        // Update data
        updateData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Set up Search for diary titles
    private fun setupSearchListener(searchView: SearchView) {
        searchView.queryHint = "Search in diaries"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                viewLifecycleOwner.lifecycleScope.launch {
                    val data = if (!query.isNullOrEmpty()) {
                        db.diaryDao().setSearchDiary("%$query%")
                    } else {
                        db.diaryDao().getAllDiaries()
                    }
                    listAdapter.setData(data)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return if (query != null) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        val data = db.diaryDao().setSearchDiary("%$query%")
                        listAdapter.setData(data)
                    }
                    true
                } else {
                    false
                }
            }
        })
    }

    private fun addData() {
        binding.fab.setOnClickListener {
            showDiaryDialog { newDiary ->
                viewLifecycleOwner.lifecycleScope.launch {
                    db.diaryDao().insertDiary(newDiary)
                    getData()
                }
            }
        }
    }

    private fun updateData() {
        listAdapter.setOnClickListener(object : ListDiaryAdapter.OnClickListener {
            override fun onClick(position: Int, model: DiaryEntity) {
                showDiaryDialog(model) { updatedDiary ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        db.diaryDao().update(updatedDiary)
                        getData()
                    }
                }
            }
        })
    }

    private fun showDiaryDialog(
        diary: DiaryEntity? = null,
        onPositive: (DiaryEntity) -> Unit,
    ) {
        val dialogBinding = CustomAlertDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setView(dialogBinding.root)
        val dialog = alertDialogBuilder.create()

        // If it's edit mode, fill in the initial data
        diary?.let {
            dialogBinding.tvDate.text = it.date
            dialogBinding.edtTitle.setText(it.title)
            dialogBinding.edtDescription.setText(it.description)
        }

        // Date
        val calendar = Calendar.getInstance()
        val yyyy = calendar.get(Calendar.YEAR)
        val mm = calendar.get(Calendar.MONTH)
        val dd = calendar.get(Calendar.DAY_OF_MONTH)
        var getDate: String? = null

        // Date Picker Dialog
        val dateDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                getDate = "$day/${month + 1}/$year"
                dialogBinding.tvDate.text = getDate
            },
            yyyy, mm, dd
        )

        // Calendar Button
        dialogBinding.tvDate.setOnClickListener {
            dateDialog.show()
        }
        dialogBinding.ivDate.setOnClickListener {
            dateDialog.show()
        }


        // Positive Button
        dialogBinding.btnPositive.apply {
            icon = ContextCompat.getDrawable(requireContext(), R.drawable.outline_save_24)
            text =
                if (diary == null) resources.getString(R.string.text_save) else getString(R.string.text_update)

            setOnClickListener {
                val titleText = dialogBinding.edtTitle.text.toString()
                val descriptionText = dialogBinding.edtDescription.text.toString()

                if (getDate != null && titleText.isNotEmpty() && descriptionText.isNotEmpty()) {
                    val updatedDiary = DiaryEntity(
                        id = diary?.id ?: 0,
                        date = getDate.toString(),
                        title = titleText,
                        description = descriptionText
                    )
                    onPositive(updatedDiary)
                    dialog.dismiss()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please fill in the date, title, and description.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Negative Button
        dialogBinding.btnNegative.apply {
            icon = ContextCompat.getDrawable(requireContext(), R.drawable.outline_cancel_24)
            text = resources.getString(R.string.text_cancel)
            setOnClickListener {
                dialog.dismiss()
            }
        }

        // Delete Button (only visible in edit mode)
        dialogBinding.btnDelete.apply {
            isVisible = diary != null
            setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    db.diaryDao().deleteDiary(diary!!)
                    Toast.makeText(
                        requireContext(),
                        "Diary deleted successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                    getData()
                }
            }
        }

        // Cancel button showing when in edit mode
        dialogBinding.imbCancel.apply {
            isVisible = diary != null
            setOnClickListener {
                dialog.cancel()
                Toast.makeText(
                    requireContext(),
                    "Your diary update was cancelled.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    // Get all diaries from the database
    private fun getData() {
        val dataDiary = db.diaryDao().getAllDiaries()

        // Check if there are any diaries
        if (dataDiary.isNotEmpty()) {
            // If there are diaries, display them in the RecyclerView
            listAdapter.setData(
                list = dataDiary
            )
            binding.ivDiary.isVisible = false
            binding.tvDiary.isVisible = false
            binding.rvDiary.isVisible = true
        } else {
            // If there are no diaries, display the empty state message
            binding.ivDiary.isVisible = true
            binding.tvDiary.isVisible = true
            binding.rvDiary.isVisible = false
        }
    }
}