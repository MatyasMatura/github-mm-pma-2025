package com.example.cvikoapp13jetpackcompose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

data class TodoItem(
    val title: String,
    val isDone: Boolean = false
)
@Composable
fun ToDoScreen(modifier: Modifier = Modifier) {
    /*Text("TO-DO SCREEN",
        modifier = modifier
    )*/

    // Stav pro textové pole
    var text by remember { mutableStateOf("") }

    // Stav seznamu úkolů
    //val tasks = remember { mutableStateListOf<String>() }
    val tasks = remember { mutableStateListOf<TodoItem>() }

    // Stav pro confirm dialog
    var showDeleteDialog by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf<TodoItem?>(null) }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        // ----- Řádek s TextField + tlačítkem -----
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Nový úkol") },
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        tasks.add(TodoItem(title = text))
                        text = ""  // vymazat pole
                    }
                }
            ) {
                Text("+")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))


        // ----- Seznam úkolů -----
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val index = tasks.indexOf(task)
                            tasks[index] = task.copy(isDone = !task.isDone)
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (task.isDone) Color(0xFFC8E6C9) else Color(0xFFFFCDD2)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = task.title,
                            textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None
                        )

                        IconButton(onClick = {
                            taskToDelete = task
                            showDeleteDialog = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Smazat úkol"
                            )
                        }
                    }
                }
            }
        }
    }

    // Confirm dialog
    if (showDeleteDialog && taskToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                taskToDelete = null
            },
            title = { Text("Potvrzení") },
            text = { Text("Opravdu chcete smazat tento úkol?") },
            confirmButton = {
                TextButton(onClick = {
                    taskToDelete?.let { tasks.remove(it) }
                    showDeleteDialog = false
                    taskToDelete = null
                }) {
                    Text("Ano")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    taskToDelete = null
                }) {
                    Text("Ne")
                }
            }
        )
    }
}