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
import com.picpay.desafio.android.utils.shared_preferences.FormatResult
import com.picpay.desafio.android.utils.shared_preferences.SharedPreferenceUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

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
    fun checkNetwork(){
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
                    setUsers()

                }, {
                    _usersList.value = listOf()
                    _status.value = Status.ERROR
                    Log.e("Exception", it.message)
                })
            }
    }

    private fun setUsers(){
        val array = JSONArray()
        var obj: JSONObject

        userList.value?.forEach {
            obj = JSONObject()
            obj.put("id", it.id)
            obj.put("name", it.name)
            obj.put("img", it.img)
            obj.put("username", it.username)

            array.put(obj)
        }

        sharedPreferenceUtil.putString(
            SharedPreferenceUtil.USERS,
            array
        )
    }

    private fun getUsersShared() : List<User>? {
       val listUsers = FormatResult().formatResultUsers( sharedPreferenceUtil.getListUsers(
            SharedPreferenceUtil.USERS))
        return listUsers

    }

}