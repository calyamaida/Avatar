package com.example.avatar

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.avatar.data.UserRepository
import com.example.avatar.model.User
import com.example.avatar.ui.avatar.AvatarScreen
import com.example.avatar.ui.login.LoginScreen
import com.example.avatar.ui.profile.ProfileScreen
import com.example.avatar.ui.register.RegisterScreen
import com.example.avatar.ui.theme.AvatarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userRepository = UserRepository(applicationContext)

        setContent {
            AvatarTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var loggedInUser by remember { mutableStateOf(userRepository.getLoggedInUser()) }
                    var page by remember {
                        mutableStateOf(if (loggedInUser != null) "profile" else "login")
                    }

                    when (page) {
                        "avatar" -> AvatarScreen(
                            onSave = { _, _, _, _ ->
                                page = "profile"
                            }
                        )

                        "login" -> LoginScreen(
                            onLogin = { usernameOrEmail, password ->
                                val user = userRepository.login(usernameOrEmail, password)
                                if (user != null) {
                                    userRepository.saveLoggedInUser(user)
                                    loggedInUser = user
                                    page = "profile"
                                    true
                                } else {
                                    false
                                }
                            },
                            onForgotPassword = { /* TODO */ },
                            onGoToRegister = { page = "register" }
                        )

                        "register" -> RegisterScreen(
                            onBack = { page = "login" },
                            onSave = { user ->
                                userRepository.registerUser(user)
                                userRepository.saveLoggedInUser(user)
                                loggedInUser = user
                                Toast.makeText(
                                    this@MainActivity,
                                    "Registrasi berhasil! Selamat datang, ${user.firstName.ifBlank { user.username }}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                page = "profile"
                            }
                        )

                        "profile" -> ProfileScreen(
                            user = loggedInUser ?: User(),
                            onEditAvatar = { page = "avatar" },
                            onLogout = {
                                userRepository.saveLoggedInUser(null)
                                loggedInUser = null
                                page = "login"
                            }
                        )
                    }
                }
            }
        }
    }
}
