package com.picpay.desafio.android.utils.shared_preferences

import com.picpay.desafio.android.data.model.User
import org.json.JSONArray
import org.json.JSONObject

class FormatResult {
    fun formatResultUsers(arrayUser: JSONArray): List<User>{
        val users: MutableList<User> = ArrayList()
        var obj: JSONObject

        for (i in 0 until arrayUser.length()) {
            obj = arrayUser.getJSONObject(i)
            users.add(
                User(
                    obj.getString("img"),
                    obj.getString("name"),
                    obj.getString("id").toInt(),
                    obj.getString("username")
                )
            )
        }
        return users
    }
}