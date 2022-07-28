package com.example.a220727_motasemesaefan_nycschools.presentation.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.a220727_motasemesaefan_nycschools.presentation.viewmodel.SchoolViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class SharedViewModel : Fragment() {
    protected  val viewModel : SchoolViewModel by activityViewModels()
}