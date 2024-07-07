package com.example.github

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.model.User
import com.example.model.UserEvent
import com.example.model.state.UserDetailsUiState
import com.example.model.state.UserEventsUiState

@Composable
fun UserDetailsScreen(
    userState: UserDetailsUiState,
    viewModel: UserViewModel = hiltViewModel(),
) {
    var errorDetail by remember { mutableStateOf("") }
    when (userState) {
        is UserDetailsUiState.Success -> {
            val user = userState.user
            // Handle success
            DetailsView(user = user, viewModel)
        }

        is UserDetailsUiState.Error -> {
            // Handle error
            errorDetail = userState.getErrorMessage()
        }

        is UserDetailsUiState.NotFound -> {
            // Handle not found
            errorDetail = userState.getErrorMessage()
        }

        else -> {
            // nothing
        }
    }

    if (errorDetail.isNotEmpty()) {
        Text(text = errorDetail, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun DetailsView(
    user: User,
    viewModel: UserViewModel,
) {
    var events by remember { mutableStateOf<List<UserEvent>?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background),
    ) {
        Image(
            painter = rememberAsyncImagePainter(user.avatarUrl),
            contentDescription = null,
            modifier =
                Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Login: ${user.login}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Text(
            text = "ID: ${user.id}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Text(
            text = "Node ID: ${user.nodeId}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Text(
            text = "Type: ${user.type}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Text(
            text = "Site Admin: ${user.siteAdmin}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            user.receivedEventsUrl?.let { url ->
                ClickableText(
                    text =
                        AnnotatedString(
                            "Received Events URL: $url",
                            spanStyle = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline),
                        ),
                    onClick = {
                        isLoading = true
                        viewModel.getUserEvents(user.login) {
                            isLoading = false
                        }
                        viewModel.userEventsUiState.value.let {
                            when (it) {
                                is UserEventsUiState.Success -> {
                                    events = it.user
                                }
                                is UserEventsUiState.Error -> {
                                    // todo Handle error
                                }
                                is UserEventsUiState.NotFound -> {
                                    // todo Handle not found
                                }
                                else -> {
                                    // nothing
                                    // todo Handle empty
                                }
                            }
                        }
                    },
                    modifier = Modifier.padding(8.dp),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading -> {
                    Log.d("#####", "loading")
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
                !events.isNullOrEmpty() -> {
                    Log.d("#####", "DetailsView: ${events!!.size}")
                    LazyColumn(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .background(Color.LightGray)
                                .padding(8.dp),
                    ) {
                        items(events!!) { event ->
                            EventDetailsComponent(event = event)
                        }
                    }
                }
                else -> {
                    Log.d("#####", "others")
                    // nothing
                }
            }
        }
    }
}
