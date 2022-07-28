package com.example.a220727_motasemesaefan_nycschools.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a220727_motasemesaefan_nycschools.data.model.SchoolItem
import com.example.a220727_motasemesaefan_nycschools.data.util.UIState
import com.example.a220727_motasemesaefan_nycschools.repository.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolViewModel @Inject constructor(
    private val repository: SchoolRepository
): ViewModel() {

    private val _schoolLiveData: MutableLiveData<UIState> = MutableLiveData()
    val schoolLiveData: LiveData<UIState> get() = _schoolLiveData

    private val _scoreLiveData: MutableLiveData<UIState> = MutableLiveData(UIState.Loading)
    val scoreLiveData: LiveData<UIState> get() = _scoreLiveData

    var currentSchool: SchoolItem? = null

    init {
        getSchools()
    }

    private fun getSchools() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getSchools().collect { state ->
                _schoolLiveData.postValue(state)
            }
        }
    }

    fun getScore(dbn: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getScore(dbn).collect { state ->
                _scoreLiveData.postValue(state)
            }
        }
    }

    fun setSchool(schoolItem: SchoolItem?) {
        currentSchool = schoolItem
        _scoreLiveData.value = UIState.Loading
    }
}