package com.picpay.desafio.android.utils.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray


open class SharedPreferenceUtil(private val context: Context) {

    fun getSP(): SharedPreferences = context.applicationContext.getSharedPreferences(
        PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )

    fun putString(key: String, users: JSONArray) {
        val editor = getSP().edit()
        editor.putString(key, users.toString())
        editor.apply()
    }

    fun getListUsers(key: String): JSONArray {
        return JSONArray(getSP().getString(key, ""))
    }

    companion object {
        const val PREFERENCE_NAME = "list_users"
        const val USERS = "users"
    }
}