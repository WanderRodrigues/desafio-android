package com.picpay.desafio.android.utils.shared_preferences

import com.picpay.desafio.android.data.model.User
import org.json.JSONArray
import org.json.JSONObject

class FormatInserts{

    fun setUserSP(listUser: List<User>?): JSONArray{

        val array = JSONArray()

        listUser?.forEach {
            var obj = JSONObject()
            obj.put("id", it.id)
            obj.put("name", it.name)
            obj.put("img", it.img)
            obj.put("username", it.username)

            array.put(obj)
        }

        return  array
    }
}