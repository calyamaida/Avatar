package com.example.avatar.data

import android.content.Context
import com.example.avatar.model.User
import org.json.JSONArray
import org.json.JSONObject

class UserRepository(context: Context) {
    private val prefs = context.getSharedPreferences("avatar_prefs", Context.MODE_PRIVATE)

    fun registerUser(user: User): Boolean {
        val users = getUsers().toMutableList()
        val existingIndex = users.indexOfFirst {
            it.username.equals(user.username, ignoreCase = true) ||
                    (user.email.isNotBlank() && it.email.equals(user.email, ignoreCase = true))
        }
        if (existingIndex >= 0) {
            users[existingIndex] = user
        } else {
            users.add(user)
        }
        saveUsers(users)
        return true
    }

    fun getUsers(): List<User> {
        val jsonString = prefs.getString(KEY_USERS, "[]") ?: "[]"
        val users = mutableListOf<User>()
        try {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                users.add(jsonObjectToUser(jsonObject))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return users
    }

    fun login(usernameOrEmail: String, password: String): User? {
        val users = getUsers()
        return users.find {
            (it.username.equals(usernameOrEmail, ignoreCase = true) ||
                    it.email.equals(usernameOrEmail, ignoreCase = true)) &&
                    it.password == password
        }
    }

    fun saveLoggedInUser(user: User?) {
        if (user == null) {
            prefs.edit().remove(KEY_LOGGED_IN_USER).apply()
        } else {
            prefs.edit().putString(KEY_LOGGED_IN_USER, userToJsonObject(user).toString()).apply()
        }
    }

    fun getLoggedInUser(): User? {
        val jsonString = prefs.getString(KEY_LOGGED_IN_USER, null) ?: return null
        return try {
            jsonObjectToUser(JSONObject(jsonString))
        } catch (e: Exception) {
            null
        }
    }

    private fun saveUsers(users: List<User>) {
        val jsonArray = JSONArray()
        users.forEach { jsonArray.put(userToJsonObject(it)) }
        prefs.edit().putString(KEY_USERS, jsonArray.toString()).apply()
    }

    private fun userToJsonObject(user: User): JSONObject {
        return JSONObject().apply {
            put("firstName", user.firstName)
            put("lastName", user.lastName)
            put("username", user.username)
            put("email", user.email)
            put("password", user.password)
            put("phone", user.phone)
            put("birthDate", user.birthDate)
            put("gender", user.gender)
        }
    }

    private fun jsonObjectToUser(jsonObject: JSONObject): User {
        return User(
            firstName = jsonObject.optString("firstName", ""),
            lastName = jsonObject.optString("lastName", ""),
            username = jsonObject.optString("username", ""),
            email = jsonObject.optString("email", ""),
            password = jsonObject.optString("password", ""),
            phone = jsonObject.optString("phone", ""),
            birthDate = jsonObject.optString("birthDate", ""),
            gender = jsonObject.optString("gender", "")
        )
    }

    companion object {
        private const val KEY_USERS = "registered_users"
        private const val KEY_LOGGED_IN_USER = "logged_in_user"
    }
}
