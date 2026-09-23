package com.example.avatar.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Wc
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.avatar.model.User
import com.example.avatar.ui.components.InfoRow
import com.example.avatar.ui.theme.Cream
import com.example.avatar.ui.theme.InkSoft
import com.example.avatar.ui.theme.Line
import com.example.avatar.ui.theme.Terracotta
import com.example.avatar.ui.theme.TerracottaD

@Composable
fun ProfileScreen(
    user: User,
    onEditAvatar: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    Scaffold(containerColor = MaterialTheme.colorScheme.background) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // ─── Header dengan avatar & nama ───
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Terracotta, TerracottaD)
                        ),
                        shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
                    )
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar circle dengan inisial
                    Box(
                        modifier = Modifier
                            .size(88.dp)
                            .clip(CircleShape)
                            .background(Cream),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user.initials,
                            style = MaterialTheme.typography.headlineLarge,
                            color = TerracottaD,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(Modifier.height(14.dp))

                    Text(
                        text = user.fullName.ifBlank { "User" },
                        style = MaterialTheme.typography.headlineLarge,
                        color = Cream,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = "@${user.username.ifBlank { "username" }}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Cream.copy(alpha = 0.85f)
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = onEditAvatar,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Cream),
                        border = BorderStroke(1.dp, Cream.copy(alpha = 0.7f))
                    ) {
                        Text("Atur Avatar", fontWeight = FontWeight.Medium)
                    }
                }
            }

            // ─── Card Info ───
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
                ) {
                    InfoRow(Icons.Outlined.Person, "Nama Lengkap", user.fullName)
                    HorizontalDivider(color = Line)
                    InfoRow(Icons.Outlined.Person, "Username", user.username)
                    HorizontalDivider(color = Line)
                    InfoRow(Icons.Outlined.Email, "Email", user.email)
                    HorizontalDivider(color = Line)
                    InfoRow(Icons.Outlined.Phone, "No. Telepon", user.phone)
                    HorizontalDivider(color = Line)
                    InfoRow(Icons.Outlined.Cake, "Tanggal Lahir", user.birthDate)
                    HorizontalDivider(color = Line)
                    InfoRow(Icons.Outlined.Wc, "Jenis Kelamin", user.gender)
                }
            }

            Spacer(Modifier.height(28.dp))

            // ─── Tombol Logout ───
            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = TerracottaD
                ),
                border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(
                    brush = Brush.linearGradient(listOf(TerracottaD, Terracotta))
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Logout,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Logout",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Versi aplikasi 1.0.0",
                style = MaterialTheme.typography.labelSmall,
                color = InkSoft,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}
