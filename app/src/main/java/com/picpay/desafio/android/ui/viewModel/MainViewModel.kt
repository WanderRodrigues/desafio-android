package com.picpay.desafio.android.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.GetUsersUseCase
import com.picpay.desafio.android.extensions.read
import com.picpay.desafio.android.utils.Status
import com.picpay.desafio.android.utils.shared_preferences.FormatInserts
import com.picpay.desafio.android.utils.shared_preferences.FormatResults
import com.picpay.desafio.android.utils.shared_preferences.SharedPreferenceUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    var sharedPreferenceUtil: SharedPreferenceUtil
) : ViewModel(){
    private val _usersList = MutableLiveData<List<User>>()
    val userList : LiveData<List<User>>get() = _usersList

    private var _status = MutableLiveData<Status>()
    val status: LiveData<Status> get() = _status

    var hasNetwork =  MutableLiveData<Boolean>()


    fun setList(users: List<User>){
        _usersList.value = users
    }

    //Verificando se tem internet
    fun checkNetwork(){
        //Se tiver carrega os dados através da api, se não pega do cache
        if (hasNetwork.value!!){
            loadUsersByApi()
        }else{
            _usersList.value = getUsersShared()
        }
    }

    fun loadUsersByApi() = viewModelScope.launch {
        _status.value = Status.LOADING

            withContext(Dispatchers.IO) {
                getUsersUseCase()
            }.let {
                it.read({ users->
                    _status.value = Status.SUCCESS
                    _usersList.value = users
                    setUsersShared()

                }, {
                    _usersList.value = listOf()
                    _status.value = Status.ERROR
                    Log.e("Exception", it.message)
                })
            }
    }

    private fun setUsersShared() = viewModelScope.launch {

        sharedPreferenceUtil.putString(
            SharedPreferenceUtil.USERS,
            FormatInserts().setUserSP(userList.value)
        )
    }

    //Recuperando lista de user do sharedPreferences
    private fun getUsersShared() : List<User>? {
        _status.value = Status.LOADING
        val listUsers = FormatResults().formatResultUsers(
            sharedPreferenceUtil.getListUsers(
                SharedPreferenceUtil.USERS
            )
        )
        if (listUsers.isNotEmpty()) {
            _status.value = Status.SUCCESS
        }else{
            _status.value = Status.ERROR
        }
        return listUsers
    }

}