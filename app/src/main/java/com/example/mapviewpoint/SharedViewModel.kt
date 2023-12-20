package com.example.mapviewpoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapviewpoint.model.GpsCoordinates
import com.example.mapviewpoint.network.RequestResult
import com.example.mapviewpoint.usecase.UserLogOutUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private val userLogOutUseCase: UserLogOutUseCase
): ViewModel() {

    private val _selectedDate = MutableLiveData<Long>()
    val selectedDate: LiveData<Long> = _selectedDate

    fun setSelectedDate(date: Long) {
        _selectedDate.value = date
    }

    private val _logOutState = MutableLiveData<RequestResult<Unit>>()
    val getLogOutState: LiveData<RequestResult<Unit>> = _logOutState

    fun setLogOutState(state: RequestResult<Unit>) {
        _logOutState.value = state
    }

    fun userLogOut(){
        viewModelScope.launch {
            val res = userLogOutUseCase.userLogout()
            setLogOutState(res)
        }
    }

}