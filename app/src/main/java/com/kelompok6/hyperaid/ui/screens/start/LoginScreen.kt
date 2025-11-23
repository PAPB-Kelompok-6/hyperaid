package com.kelompok6.hyperaid.ui.screens.start

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.kelompok6.hyperaid.R
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController, viewModel: AuthViewModel = viewModel()) {
    val scrollState = rememberScrollState()
    val loginState = viewModel.loginState
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Error) {
            snackbarHostState.showSnackbar(loginState.message)
            viewModel.loginState = LoginState.Idle
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 50.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.hyperaid),
                contentDescription = "Hyperaid logo",
                modifier = Modifier.widthIn(max = 175.dp)
            )

            Text(
                text = "Welcome Back!",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "Log in and manage your health anytime, anywhere!",
                modifier = Modifier
                    .padding(20.dp, 10.dp),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )

            LoginField(navController, viewModel)
            OrDivider()
            LoginOAuth(navController, viewModel)

            Spacer(modifier = Modifier.padding(0.dp, 35.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Label("Don't have an account?")
                Text(
                    modifier = Modifier.clickable() {
                        navController.navigate("Register") {
                            popUpTo("Login") { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    text = "Register",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        if (loginState is LoginState.Loading) {
            LoadingFullscreen()
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun LoginField(navController: NavController, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Checkbox(
                    modifier = Modifier.size(20.dp),
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it },
                )
                Label("Remember Me", Modifier.padding(10.dp, 0.dp))
            }

            Label("Forgot your password?", Modifier, TextAlign.Right)
        }

        Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2B2B2B),
                contentColor = Color(0xFFF6C9CB)
            ),
            shape = RoundedCornerShape(25f),
            onClick = {
                viewModel.login(email, password)
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Label("LOGIN")
        }

        if (viewModel.loginState is LoginState.Success) {
            LaunchedEffect(Unit) {
                navController.navigate("Home") {
                    popUpTo("Login") { inclusive = true }
                }
            }
        }
    }
}

@Composable
fun LoginOAuth(navController: NavController, viewModel: AuthViewModel) {
    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        val context = LocalContext.current

        val credentialManager = CredentialManager.create(context)
        val coroutineScope = rememberCoroutineScope()

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.DarkGray
            ),
            border = BorderStroke(1.dp, Color.DarkGray),
            shape = RoundedCornerShape(25f),
            onClick = {
                coroutineScope.launch {
                    try {
                        val googleIdOption =
                            GetSignInWithGoogleOption.Builder(context.getString(R.string.default_web_client_id))
                                .build()

                        val request = GetCredentialRequest.Builder()
                            .addCredentialOption(googleIdOption)
                            .build()

                        val result = credentialManager.getCredential(
                            context = context,
                            request = request
                        )

                        val credential = result.credential
                        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                            val googleIdTokenCredential =
                                GoogleIdTokenCredential.createFrom(credential.data)
                            val firebaseCredential = GoogleAuthProvider.getCredential(
                                googleIdTokenCredential.idToken,
                                null
                            )

                            viewModel.loginWithGoogle(firebaseCredential);
                        }
                    } catch (e: Exception) {
                        Log.e("Login", "Google Sign-In failed", e)
                        viewModel.showAuthError("Failed to login using Google OAuth")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_social_google),
                    contentDescription = "Google icon",
                    modifier = Modifier.size(20.dp)
                )
                Label("Login with Google")
            }
        }

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.DarkGray
            ),
            border = BorderStroke(1.dp, Color.DarkGray),
            shape = RoundedCornerShape(25f),
            onClick = {
                // navController.navigate("Home")
            },
            modifier = Modifier.fillMaxWidth(),

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_social_facebook),
                    contentDescription = "Facebook icon",
                    modifier = Modifier.size(20.dp)
                )
                Label("Login with Facebook")
            }
        }

        if (viewModel.oAuthState is OAuthState.Success) {
            LaunchedEffect(Unit) {
                navController.navigate("Home") {
                    popUpTo("Login") { inclusive = true }
                }
            }
        }
    }
}

@Composable
fun OrDivider() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )
        Text(
            text = "Or",
            modifier = Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        HorizontalDivider(
            modifier = Modifier
                .weight(1f),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )
    }
}

@Composable
fun Label(text: String, modifier: Modifier = Modifier, textAlign: TextAlign = TextAlign.Left) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.Normal,
        textAlign = textAlign
    )
}

@Composable
fun LoadingFullscreen() {
    val alpha by animateFloatAsState(targetValue = 1f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = alpha)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(70.dp),
            strokeWidth = 5.dp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
