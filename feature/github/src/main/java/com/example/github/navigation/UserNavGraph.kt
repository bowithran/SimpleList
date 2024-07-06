package com.example.github.navigation

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.github.UserDetailsScreen
import com.example.github.UserListScreen
import com.example.github.UserViewModel
import com.example.model.state.UserDetailsUiState

@Composable
fun UserNavGraph(
    navController: NavHostController,
    viewModel: UserViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.UserList.route
    ) {
        composable(
            route = Screen.UserList.route
        ) {
            UserListScreen(navController)
        }

        composable(
            route = "${Screen.UserDetails.route}/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId")
            // Show user details screen

            Log.d("#####", "userId is : $userId")
            if (userId == null) {
                // Handle missing user by showing an error dialog
                UserDetailsScreen(userState = UserDetailsUiState.Error(Exception("UserId is null")))
            } else {
                // get user details information
                viewModel.getUserById(userId)
                UserDetailsScreen(userState = viewModel.userDetailsUiState.value)
            }
        }
    }
}