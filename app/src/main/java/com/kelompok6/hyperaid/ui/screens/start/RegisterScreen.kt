package com.kelompok6.hyperaid.ui.screens.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.kelompok6.hyperaid.R

@Composable
fun RegisterScreen(navController: NavHostController, viewModel: AuthViewModel = viewModel()) {
    val scrollState = rememberScrollState()
    val state = viewModel.registerState
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state) {
        if (state is RegisterState.Error) {
            snackbarHostState.showSnackbar(state.message)
            viewModel.registerState = RegisterState.Idle
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 50.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.hyperaid),
                contentDescription = "Hyperaid logo",
                modifier = Modifier.widthIn(max = 175.dp)
            )

            Text(
                text = "Register to your account and manage your health right away!",
                modifier = Modifier.padding(20.dp, 10.dp),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )

            RegisterField(navController, viewModel, snackbarHostState)
            OrDivider()
            LoginOAuth(navController)

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Label("Have an account?")
                Text(
                    text = "Login",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.clickable {
                        navController.navigate("Login") {
                            popUpTo("Register") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }

        if (state is RegisterState.Loading) {
            LoadingFullscreen()
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Composable
fun RegisterField(navController: NavController, viewModel: AuthViewModel, snackbarHostState: SnackbarHostState) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Label("Full Name") },
            shape = RoundedCornerShape(25f),
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Label("Email") },
            shape = RoundedCornerShape(25f),
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Label("Password") },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(25f),
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Label("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(25f),
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (password != confirmPassword) {
                    viewModel.showAuthError("Passwords do not match")
                } else {
                    viewModel.register(fullName, email, password)
                }
            },
            shape = RoundedCornerShape(25f),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2B2B2B),
                contentColor = Color(0xFFF6C9CB)
            ),
        ) {
            Label("REGISTER")
        }

        if (viewModel.registerState is RegisterState.Success) {
            LaunchedEffect(Unit) {
                navController.navigate("Home") {
                    popUpTo("Register") { inclusive = true }
                }
            }
        }
    }
}
