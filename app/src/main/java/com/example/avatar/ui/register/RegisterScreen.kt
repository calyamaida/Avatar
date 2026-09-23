package com.example.avatar.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.example.avatar.model.User
import com.example.avatar.ui.components.AppTextField
import com.example.avatar.ui.theme.Ink
import com.example.avatar.ui.theme.InkSoft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onBack: () -> Unit = {},
    onSave: (User) -> Unit = {}
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            // Header
            Text(
                text = "Buat Akun",
                style = MaterialTheme.typography.headlineLarge,
                color = Ink
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Isi data di bawah untuk memulai.",
                style = MaterialTheme.typography.bodyMedium,
                color = InkSoft
            )

            Spacer(Modifier.height(28.dp))

            // Nama depan & belakang dalam satu baris
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(Modifier.weight(1f)) {
                    AppTextField(
                        label = "First Name",
                        value = firstName,
                        onValueChange = { firstName = it },
                        placeholder = "Budi"
                    )
                }
                Box(Modifier.weight(1f)) {
                    AppTextField(
                        label = "Last Name",
                        value = lastName,
                        onValueChange = { lastName = it },
                        placeholder = "Santoso"
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            AppTextField(
                label = "Username",
                value = username,
                onValueChange = {
                    username = it
                    errorMessage = null
                },
                placeholder = "budisan"
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                label = "Email",
                value = email,
                onValueChange = { email = it },
                placeholder = "budi@email.com",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                label = "Password",
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = null
                },
                placeholder = "Min. 8 karakter",
                visualTransformation = if (showPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailing = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Filled.VisibilityOff
                            else Icons.Filled.Visibility,
                            contentDescription = "Toggle password",
                            tint = InkSoft
                        )
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                label = "No. Telepon",
                value = phone,
                onValueChange = { phone = it },
                placeholder = "0812xxxxxxx",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                label = "Tanggal Lahir",
                value = birthDate,
                onValueChange = { birthDate = it },
                placeholder = "DD/MM/YYYY"
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                label = "Jenis Kelamin",
                value = gender,
                onValueChange = { gender = it },
                placeholder = "Laki-laki / Perempuan"
            )

            Spacer(Modifier.height(24.dp))

            errorMessage?.let { msg ->
                Text(
                    text = msg,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    if (username.isBlank() || password.isBlank()) {
                        errorMessage = "Username dan password wajib diisi"
                    } else {
                        errorMessage = null
                        onSave(
                            User(
                                firstName = firstName,
                                lastName = lastName,
                                username = username.trim(),
                                email = email.trim(),
                                password = password,
                                phone = phone,
                                birthDate = birthDate,
                                gender = gender
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    "Save",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Sudah punya akun? ", color = InkSoft, style = MaterialTheme.typography.bodyMedium)
                TextButton(onClick = onBack) {
                    Text("Login", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}
