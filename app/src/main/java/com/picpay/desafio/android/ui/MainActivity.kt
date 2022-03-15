package com.picpay.desafio.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.ui.adapter.UserListAdapter
import com.picpay.desafio.android.ui.viewModel.MainViewModel
import com.picpay.desafio.android.utils.NetworkUtils
import com.picpay.desafio.android.utils.shared_preferences.FormatResult
import com.picpay.desafio.android.utils.shared_preferences.SharedPreferenceUtil
import org.json.JSONArray

import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by inject()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val listUsers = SharedPreferenceUtil(applicationContext).getListUsers(
            SharedPreferenceUtil.USERS
        )
        outState.putString(LIST_USERS, listUsers.toString())

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val list = JSONArray(savedInstanceState.getString(LIST_USERS))
        viewModel.setList(FormatResult().formatResultUsers(list))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)

        initView()

        if (savedInstanceState ==null){
            viewModel.checkNetwork()
        }
    }
    private fun initView(){
        viewModel.userList.observeForever{
            binding.recyclerUsers.adapter = UserListAdapter(it)
            binding.recyclerUsers.layoutManager = LinearLayoutManager(this)
        }

        if (baseContext.applicationContext !=null){
            viewModel.hasNetwork.value = NetworkUtils(applicationContext).hasInternet()
        }
    }

    companion object {
        const val LIST_USERS = "list"
    }

}
