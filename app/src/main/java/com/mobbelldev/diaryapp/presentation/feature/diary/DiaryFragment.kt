package com.mobbelldev.diaryapp.presentation.feature.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.mobbelldev.diaryapp.R
import com.mobbelldev.diaryapp.databinding.FragmentDiaryBinding

class DiaryFragment : Fragment() {

    private var _binding: FragmentDiaryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
//        val diaryViewModel =
//            ViewModelProvider(this)[DiaryViewModel::class.java]

        _binding = FragmentDiaryBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        val textView: TextView = binding.textDiary
//        diaryViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // The menu is displayed on the toolbar..
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_bar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_search -> {
                        Toast.makeText(requireActivity(), "Search is clicked!", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }

                    R.id.menu_sort -> {
                        Toast.makeText(requireActivity(), "Sort is clicked!", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // FAB
        binding.fab.setOnClickListener {
            Toast.makeText(requireActivity(), "Showing alert dialog!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}