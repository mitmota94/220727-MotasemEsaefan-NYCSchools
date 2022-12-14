package com.example.a220727_motasemesaefan_nycschools.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a220727_motasemesaefan_nycschools.R
import com.example.a220727_motasemesaefan_nycschools.data.model.SchoolItem
import com.example.a220727_motasemesaefan_nycschools.data.util.UIState
import com.example.a220727_motasemesaefan_nycschools.databinding.FragmentSchoolListBinding
import com.example.a220727_motasemesaefan_nycschools.presentation.adapter.SchoolAdapter

class SchoolListFragment : SharedViewModel() {
    private var _binding : FragmentSchoolListBinding? = null
    private val binding : FragmentSchoolListBinding get() = _binding!!
    private lateinit var schoolAdapter : SchoolAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSchoolListBinding.inflate(layoutInflater)

        configureObserver()
        return  binding.root
    }

    private fun configureObserver() {
        viewModel.schoolLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Success<*> -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvSchoolErrorLoadingText.visibility = View.GONE
                        schoolAdapter = SchoolAdapter(setSchool = ::setSchool )
                        schoolAdapter.setSchoolsList(state.response as List<SchoolItem>)
                        rvSchoolList.adapter = schoolAdapter
                    }
                }
                is UIState.Error -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvSchoolErrorLoadingText.text = state.exception.message
                    }
                }
                else -> {}
            }
        }
    }
    private fun setSchool(schoolItem: SchoolItem) {
        viewModel.setSchool(schoolItem)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView,DetailFragment())
            .addToBackStack(null)
            .commit()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}