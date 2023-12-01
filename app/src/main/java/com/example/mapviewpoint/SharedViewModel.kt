package com.example.mapviewpoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapviewpoint.network.RequestResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class SharedViewModel @Inject constructor(): ViewModel() {

    private val _selectedDate = MutableLiveData<Long>()
    val selectedDate: LiveData<Long> = _selectedDate

    fun setSelectedDate(date: Long) {
        _selectedDate.value = date
    }

}