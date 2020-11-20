package com.elacqua.findmyrouteapp.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.findmyrouteapp.data.local.LocalRepository
import com.elacqua.findmyrouteapp.data.local.entity.User
import com.elacqua.findmyrouteapp.util.Utility.md5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel @ViewModelInject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    private val _registerStatus = MutableLiveData<Boolean>()
    val registerStatus: LiveData<Boolean> = _registerStatus

    fun addUser(username: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val hashedPassword = password.md5
            val user = User(username, hashedPassword)
            val rowId = localRepository.addUser(user)
            val invalidRowId = -1L
            if (rowId == invalidRowId){
                _registerStatus.postValue(false)
            } else {
                _registerStatus.postValue(true)
            }
        }
    }

    fun getUser(username: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val hashedPassword = password.md5
            val user = localRepository.getUser(username, hashedPassword)
            if (user == null){
                _loginStatus.postValue(false)
            } else {
                localRepository.username = username
                _loginStatus.postValue(true)
            }
        }
    }
}