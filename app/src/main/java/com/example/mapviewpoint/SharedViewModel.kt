package com.example.mapviewpoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SharedViewModel @Inject constructor(): ViewModel() {

    private val _selectedDate = MutableLiveData<Long>()
    val selectedDate: LiveData<Long> = _selectedDate

    fun setSelectedDate(date: Long) {
        _selectedDate.value = date
    }

    private val _exitClicked = MutableLiveData<Boolean>()
    val getExitClicked: LiveData<Boolean> = _exitClicked

    fun setIconClicked(click: Boolean) {
        _exitClicked.value = click
    }


}