package com.example.github

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.github.navigation.Screen
import com.example.model.User
import com.example.model.state.UserUiState
import kotlin.random.Random

@Composable
fun UserListScreen(
    navController: NavController,
    viewModel: UserViewModel = hiltViewModel()
) {
    var isLoading by remember { mutableStateOf(false) }
    var users by remember { mutableStateOf(emptyList<User>()) }
    var error by remember { mutableStateOf("") }

    fun getUsers() {
        viewModel.getUsers {
            isLoading = false
            when(val state = viewModel.userUiState.value) {
                is UserUiState.Success -> {
                    users = state.users
                }
                is UserUiState.Error -> {
                    // Handle error
                    Log.e("UserListScreen", "getUsers: ${state.error}")
                    error = state.error.message.toString()
                }
                is UserUiState.Empty -> {
                    // Handle empty state
                }
            }
        }
    }

    LaunchedEffect(true) {
        isLoading = true
        getUsers()
    }

    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Center
            ) {
                CircularProgressIndicator()
            }
        }
        error.isNotEmpty() -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                // Handle error
                Text(text = "Error occurred: $error")
                // Retry
                Button(onClick = {
                    isLoading = true
                    getUsers()
                }) {
                    Text("Retry")
                }
            }
        }
        users.isEmpty() -> {
            // Handle empty state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                Text(text = "No users found")
                // Retry
                Button(onClick = {
                    isLoading = true
                    getUsers()
                }) {
                    Text("Retry")
                }
            }
        }
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 0.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                items(users) {
                    UserListItem(user = it, onItemClick = {
                        // Handle item click
                        navController.navigate(route = "${Screen.UserDetails.route}/${it.id}")
                    })
                }
            }
        }
    }
}

@Composable
fun UserListItem(
    user: User,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClick() },
        elevation = CardDefaults.cardElevation(
            10.dp
        )
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(user.avatarUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .align(Alignment.TopStart),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.align(Alignment.TopEnd),
                text = "Login: ${user.login}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                modifier = Modifier.align(Alignment.BottomEnd),
                text = "ID: ${user.id}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}