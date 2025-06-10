package com.example.pomodoro.screen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.Modifier


@Composable
fun TimePickerDialog(
    initialMinutes: Int,
    initialSeconds: Int,
    onDismiss: () -> Unit,
    onTimeSelected: (Int, Int) -> Unit
) {
    var minutes by remember { mutableStateOf(initialMinutes.toString()) }
    var seconds by remember { mutableStateOf(initialSeconds.toString()) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = MaterialTheme.shapes.medium, tonalElevation = 8.dp) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Set Timer", style = MaterialTheme.typography.headlineSmall)

                OutlinedTextField(
                    value = minutes,
                    onValueChange = { if (it.all(Char::isDigit)) minutes = it },
                    label = { Text("Minutes") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = seconds,
                    onValueChange = { if (it.all(Char::isDigit)) seconds = it },
                    label = { Text("Seconds") },
                    singleLine = true
                )

                Spacer(Modifier.height(16.dp))
                Row {
                    Button(onClick = {
                        onTimeSelected(minutes.toIntOrNull() ?: 0, seconds.toIntOrNull() ?: 0)
                    }) {
                        Text("OK")
                    }
                    Spacer(Modifier.width(8.dp))
                    OutlinedButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}

