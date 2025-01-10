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
        val root: View = binding.root
        return root
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
//                val searchItem = menu.findItem(R.id.menu_search)
//                val searchView = searchItem.actionView as SearchView
//                searchView.queryHint = "Search for diary titles..."
//                searchView.
//                // Listener
//                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                    override fun onQueryTextChange(newText: String?): Boolean = false
//
//                    override fun onQueryTextSubmit(query: String?): Boolean {
//                        // Handle the Search
//                        Toast.makeText(
//                            requireActivity(),
//                            "Search title: $query",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        return true
//                    }
//                })
//
//                // Handle collapse events
//                searchView.setOnCloseListener {
//                    Toast.makeText(requireActivity(), "Search Closed", Toast.LENGTH_SHORT).show()
//                    false
//                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    // Set up search here
                    R.id.menu_search -> {

                        true
                    }

                    R.id.menu_sort -> {
                        // Showing a pop-up sorted by date
                        val popupMenu =
                            PopupMenu(
                                requireContext(),
                                requireActivity().findViewById(R.id.menu_sort)
                            )
                        popupMenu.setOnMenuItemClickListener {
                            when (it.itemId) {
                                // Ascending
                                R.id.menu_asc -> {
                                    Toast.makeText(
                                        view.context,
                                        "Latest",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    true
                                }

                                // Descending
                                R.id.menu_desc -> {
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

    // When the card is clicked, update the data.
    private fun updateData() {
        listAdapter.setOnClickListener(object : ListDiaryAdapter.OnClickListener {
            override fun onClick(position: Int, model: DiaryEntity) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                val dialogBinding =
                    CustomAlertDialogBinding.inflate(LayoutInflater.from(requireContext()))
                alertDialogBuilder.setView(dialogBinding.root)
                val dialog = alertDialogBuilder.create()

                // Date
                val calendar = Calendar.getInstance()
                val yyyy = calendar.get(Calendar.YEAR)
                val mm = calendar.get(Calendar.MONTH)
                val dd = calendar.get(Calendar.DAY_OF_MONTH)
                var getDate: String? = null
                val dateDialog = DatePickerDialog(
                    requireContext(),
                    { view, year, month, day ->
                        getDate = "${view.dayOfMonth}/${view.month + 1}/${view.year}"
                        dialogBinding.tvDate.text = getDate
                        dialogBinding.imbDate.setImageResource(R.drawable.filled_date_range_24)
                    },
                    yyyy, mm, dd
                )
                dialogBinding.imbDate.setOnClickListener {
                    dateDialog.show()
                }

                // SET UP POSITIVE BUTTON
                dialogBinding.btnPositive.apply {
                    icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.outline_update_24
                    )
                    text = ContextCompat.getString(requireContext(), R.string.text_update)

                    // Handle the positive click
                    setOnClickListener {
                        val titleText = dialogBinding.edtTitle.text.toString()
                        val descriptionText = dialogBinding.edtDescription.text.toString()

                        if (getDate != null && titleText.isNotEmpty() && descriptionText.isNotEmpty()) {
                            val diary = DiaryEntity(
                                id = model.id,
                                date = getDate.toString(),
                                title = titleText,
                                description = descriptionText
                            )
                            viewLifecycleOwner.lifecycleScope.launch {
                                db.diaryDao().update(
                                    diaryEntity = diary
                                )
                            }
                            Toast.makeText(
                                requireContext(),
                                "Updated is successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            dialog.dismiss()
                            getData()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Please fill in the date, the title, and the description.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                // SET UP NEGATIVE BUTTON
                dialogBinding.btnNegative.isVisible = false

                // SET UP DELETE BUTTON
                dialogBinding.btnDelete.apply {
                    isVisible = true
                    setOnClickListener {
                        viewLifecycleOwner.lifecycleScope.launch {
                            db.diaryDao().deleteDiary(model)
                        }
                        dialog.dismiss()
                        getData()
                    }
                }

                // SET UP CANCEL BUTTON
                dialogBinding.imbCancel.apply {
                    isVisible = true
                    setOnClickListener {
                        dialog.cancel()
                        Toast.makeText(
                            requireContext(),
                            "Your diary update was cancelled.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
            }

        })
    }

    // FAB ACTION
    private fun addData() {
        binding.fab.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(view?.context)
            val dialogBinding =
                CustomAlertDialogBinding.inflate(LayoutInflater.from(requireContext()))
            alertDialogBuilder.setView(dialogBinding.root)
            val dialog = alertDialogBuilder.create()

            // Date
            val calendar = Calendar.getInstance()
            val yyyy = calendar.get(Calendar.YEAR)
            val mm = calendar.get(Calendar.MONTH)
            val dd = calendar.get(Calendar.DAY_OF_MONTH)
            var getDate: String? = null
            val dateDialog = DatePickerDialog(
                requireContext(),
                { view, year, month, day ->
                    getDate = "${view.dayOfMonth}/${view.month + 1}/${view.year}"
                    dialogBinding.tvDate.text = getDate
                    Toast.makeText(
                        requireActivity(),
                        "${view.dayOfMonth}/${view.month + 1}/${view.year}",
                        Toast.LENGTH_SHORT
                    ).show()

                    dialogBinding.imbDate.setImageResource(R.drawable.filled_date_range_24)
                },
                yyyy, mm, dd
            )
            dialogBinding.imbDate.setOnClickListener {
                dateDialog.show()
            }

            // SET UP POSITIVE BUTTON
            dialogBinding.btnPositive.apply {
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.outline_save_24)
                text = ContextCompat.getString(requireContext(), R.string.text_save)

                // Handle the positive click
                setOnClickListener {
                    val titleText = dialogBinding.edtTitle.text.toString()
                    val descriptionText = dialogBinding.edtDescription.text.toString()

                    if (getDate != null && titleText.isNotEmpty() && descriptionText.isNotEmpty()) {
                        val diary = DiaryEntity(
                            date = getDate.toString(),
                            title = titleText,
                            description = descriptionText
                        )
                        viewLifecycleOwner.lifecycleScope.launch {
                            db.diaryDao().insertDiary(diaryEntity = diary)
                        }
                        Toast.makeText(
                            requireActivity(),
                            "Diary saved successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                        dialog.dismiss()
                        getData()
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "Please fill in the date, the title, and the description.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            // SET UP NEGATIVE BUTTON
            dialogBinding.btnNegative.apply {
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.outline_cancel_24)
                text = ContextCompat.getString(requireContext(), R.string.text_cancel)

                // Handle the negative click
                setOnClickListener {
                    dialog.cancel()

                    Toast.makeText(
                        requireActivity(),
                        "Cancel button is clicked!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
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