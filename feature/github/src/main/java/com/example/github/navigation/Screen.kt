package com.example.github.navigation

sealed class Screen(val route: String, val name: String) {
    object UserList: Screen(route = "user_list", name = "UserList")
    object UserDetails: Screen(route = "user_details", name = "UserDetails")
}