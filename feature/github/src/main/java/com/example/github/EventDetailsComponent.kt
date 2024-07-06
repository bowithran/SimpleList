package com.example.github

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.model.Actor
import com.example.model.Repo
import com.example.model.UserEvent
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun EventDetailsComponent(event: UserEvent) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Event ID: ${event.id}", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Event Type: ${event.type}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                event.createdAt?.let {
                    Text(text = "Created At: ${formatDate(it)}", style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Actor Section
                event.actor?.let {
                    Text(text = "Actor Details:", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    ActorDetails(actor = it)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Repository Section
                event.repo?.let {
                    Text(text = "Repository Details:", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    RepositoryDetails(repo = it)
                }
            }
        }
    }
}

@Composable
fun ActorDetails(actor: Actor) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberAsyncImagePainter(actor.avatarUrl),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(MaterialTheme.shapes.small),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = "Login: ${actor.login}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "ID: ${actor.id}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun RepositoryDetails(repo: Repo) {
    Column {
        Text(text = "Name: ${repo.name}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "ID: ${repo.id}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "URL: ${repo.url}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun formatDate(dateTimeString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault())
    val date = inputFormat.parse(dateTimeString)
    return outputFormat.format(date)
}