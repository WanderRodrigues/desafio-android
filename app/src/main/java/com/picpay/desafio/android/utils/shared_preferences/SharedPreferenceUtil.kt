package com.picpay.desafio.android.utils.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.JsonArray
import org.json.JSONArray


open class SharedPreferenceUtil(private val context: Context) {

    fun getSP(): SharedPreferences = context.applicationContext.getSharedPreferences(
        PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )

    fun putString(key: String, users: JSONArray) {
        try {
            val editor = getSP().edit()
            editor.putString(
                key,
                users.toString()
            ).apply()
        }catch (ex: Exception){
            Log.e("Excepition Put User", ex.message)
        }
    }

    fun getListUsers(key: String): JSONArray{
        var listUser = JSONArray()
        try {
            listUser = JSONArray(getSP().getString(key, ""))
        }catch (ex: Exception){
            Log.e("Exception List User", ex.message)
        }

        return listUser
    }

    companion object {
        const val PREFERENCE_NAME = "list_users"
        const val USERS = "users"
    }
}