package com.example.avatar.ui.avatar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.avatar.R
import com.example.avatar.ui.theme.Ink
import com.example.avatar.ui.theme.InkSoft
import com.example.avatar.ui.theme.Line

@Composable
fun AvatarScreen(
    onSave: (showEyes: Boolean, showEyebrows: Boolean, showNose: Boolean, showLips: Boolean) -> Unit = { _, _, _, _ -> }
) {
    var showEyes by remember { mutableStateOf(true) }
    var showEyebrows by remember { mutableStateOf(true) }
    var showNose by remember { mutableStateOf(true) }
    var showLips by remember { mutableStateOf(true) }

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            // ─── Header ───
            Text(
                text = "Atur Avatar",
                style = MaterialTheme.typography.headlineLarge,
                color = Ink
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Pilih bagian wajah yang ingin ditampilkan.",
                style = MaterialTheme.typography.bodyMedium,
                color = InkSoft
            )

            Spacer(Modifier.height(24.dp))

            // ─── Preview Avatar ───
            AvatarPreview(
                showEyes = showEyes,
                showEyebrows = showEyebrows,
                showNose = showNose,
                showLips = showLips
            )

            Spacer(Modifier.height(28.dp))

            // ─── Toggle Checkbox ───
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(Modifier.padding(vertical = 4.dp)) {
                    FacePartToggle("Mata", showEyes) { showEyes = it }
                    Divider(color = Line, modifier = Modifier.padding(horizontal = 12.dp))
                    FacePartToggle("Alis", showEyebrows) { showEyebrows = it }
                    Divider(color = Line, modifier = Modifier.padding(horizontal = 12.dp))
                    FacePartToggle("Hidung", showNose) { showNose = it }
                    Divider(color = Line, modifier = Modifier.padding(horizontal = 12.dp))
                    FacePartToggle("Bibir", showLips) { showLips = it }
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { onSave(showEyes, showEyebrows, showNose, showLips) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    "Simpan Avatar",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────────
// Preview: semua komponen wajah di-stack pakai Box
// Background selalu dirender, komponen lain conditional.
// ─────────────────────────────────────────────────────────────
@Composable
private fun AvatarPreview(
    showEyes: Boolean,
    showEyebrows: Boolean,
    showNose: Boolean,
    showLips: Boolean
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .aspectRatio(0.6f)   // rasio portrait — sesuaikan dgn gambar
        ) {
            val w = maxWidth
            val h = maxHeight

            // 1. BACKGROUND — selalu tampil, tidak bisa di-toggle
            Image(
                painter = painterResource(R.drawable.avatar_background),
                contentDescription = "Avatar background",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )

            // 2. ALIS — paling belakang di antara fitur wajah
            if (showEyebrows) {
                Image(
                    painter = painterResource(R.drawable.avatar_eyebrows),
                    contentDescription = "Alis",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .offset(x = w * 0.225f, y = h * 0.34f)
                        .size(width = w * 0.55f, height = h * 0.06f)
                )
            }

            // 3. MATA
            if (showEyes) {
                Image(
                    painter = painterResource(R.drawable.avatar_eyes),
                    contentDescription = "Mata",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .offset(x = w * 0.25f, y = h * 0.41f)
                        .size(width = w * 0.50f, height = h * 0.10f)
                )
            }

            // 4. HIDUNG
            if (showNose) {
                Image(
                    painter = painterResource(R.drawable.avatar_nose),
                    contentDescription = "Hidung",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .offset(x = w * 0.44f, y = h * 0.51f)
                        .size(width = w * 0.12f, height = h * 0.07f)
                )
            }

            // 5. BIBIR — paling depan
            if (showLips) {
                Image(
                    painter = painterResource(R.drawable.avatar_lips),
                    contentDescription = "Bibir",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .offset(x = w * 0.38f, y = h * 0.60f)
                        .size(width = w * 0.24f, height = h * 0.08f)
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────
// Baris checkbox reusable
// ─────────────────────────────────────────────────────────────
@Composable
private fun FacePartToggle(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = InkSoft,
                checkmarkColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Ink,
            fontWeight = FontWeight.Medium
        )
    }
}