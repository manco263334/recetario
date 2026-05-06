package com.dmm.recetario.ui.auth.register

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun RegisterScreen (
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    RegisterContent (
        uiState = viewModel.uiState,
        onRegister = viewModel::register,
        onRetry = viewModel::resetToIdle,
        onNavigateToHome = onNavigateToHome,
        onNavigateToLogin = onNavigateToLogin
    )
}

@Composable
fun RegisterContent (
    uiState: RegisterUiState,
    onRegister: (String, String, String, String?, String?) -> Unit,
    onRetry: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    LaunchedEffect(uiState) {
        if (uiState is RegisterUiState.Success) {
            onNavigateToHome()
        }
    }

    when (uiState) {
        is RegisterUiState.Loading -> CircularProgressIndicator()
        is RegisterUiState.Error -> {
            RegisterError (message = uiState.message, onRetry = onRetry)
        }
        else -> {
            RegisterForm (
                onRegister = onRegister,
                onNavigateToLogin = onNavigateToLogin
            )
        }
    }
}

@Composable
private fun RegisterError (
    message: String,
    onRetry: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "rebote")
    val translateY by infiniteTransition.animateFloat (
        initialValue = 0f,
        targetValue = -20f,
        animationSpec = infiniteRepeatable (
            animation = tween(500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "y"
    )

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon (
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .graphicsLayer(translationY = translateY), // Aplicamos el rebote
            tint = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text (
            text = message,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRetry) {
            Text("Intentar de nuevo")
        }
    }
}

@Composable
private fun RegisterForm (
    onRegister: (String, String, String, String?, String?) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()), // Por si son muchos campos
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text (
            text = "Crea tu cuenta",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campos Obligatorios
        OutlinedTextField (
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre completo *") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField (
            value = email,
            onValueChange = { email = it },
            label = { Text("Email *") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField (
            value = password,
            onValueChange = { password = it },
            label = { Text("Password *") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        // Campos Opcionales
        OutlinedTextField (
            value = phone, 
            onValueChange = { phone = it }, 
            label = { Text("Teléfono (Opcional)") }, 
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField (
            value = username, 
            onValueChange = { username = it }, 
            label = { Text("Apodo (Opcional)") }, 
            modifier = Modifier.fillMaxWidth(), 
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button (
            modifier = Modifier.fillMaxWidth(),
            onClick = { onRegister(name, email, password, phone.ifBlank { null }, username.ifBlank { null }) },
            enabled = name.isNotBlank() && email.isNotBlank() && password.isNotBlank()
        ) {
            Text("Registrarme de One")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton (
            modifier = Modifier.fillMaxWidth(),
            onClick = onNavigateToLogin
        ) {
            Text("Ya tengo una cuenta")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterContent (
        uiState = RegisterUiState.Idle,
        onRegister = { _, _, _, _, _ -> },
        onRetry = {},
        onNavigateToHome = {},
        onNavigateToLogin = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun RegisterErrorPreview() {
    RegisterError (
        message = "Algo salió mal",
        onRetry = {}
    )
}