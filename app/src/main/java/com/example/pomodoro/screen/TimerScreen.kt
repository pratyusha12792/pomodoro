import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.pomodoro.viewModel.TimerViewModel
import com.example.pomodoro.R // Make sure this import is present
import com.example.pomodoro.screen.TimePickerDialog

//@Composable
//fun TimerScreen(
//    viewModel: TimerViewModel,
//    onEditClick: () -> Unit = {}
//) {
//    val timeLeft by viewModel.timeLeft.collectAsState()
//    val isRunning by viewModel.isRunning.collectAsState()
//    val roundsCompleted by viewModel.roundsCompleted.collectAsState()
//
//    // Choose animation based on timer state
//    val lottieRes = if (isRunning && timeLeft > 0) {
//        R.raw.work // work.json for focus/work animation
//    } else {
//        R.raw.break_anim // break_anim.json for break/paused animation
//    }
//
//    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieRes))
//    val lottieAnimatable = rememberLottieAnimatable()
//    LaunchedEffect(composition, lottieRes) {
//        lottieAnimatable.animate(
//            composition = composition,
//            iterations = LottieConstants.IterateForever
//        )
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp)
//    ) {
//        // Edit icon (top right)
//        IconButton(
//            onClick = onEditClick,
//            modifier = Modifier.align(Alignment.TopEnd)
//        ) {
//            Icon(Icons.Default.Edit, contentDescription = "Edit Timer")
//        }
//
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = "Focus and aim for your goals!",
//                style = MaterialTheme.typography.titleMedium,
//                color = MaterialTheme.colorScheme.primary
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            // Flip clock timer (simple version)
//            val hours = timeLeft / 3600
//            val minutes = (timeLeft % 3600) / 60
//            val seconds = timeLeft % 60
//
//            Text(
//                text = String.format("%02d:%02d:%02d", hours, minutes, seconds),
//                style = MaterialTheme.typography.headlineLarge,
//                modifier = Modifier.padding(top = 24.dp)
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//            // Pause/Play/Reset/Stop logic
//            Row(horizontalArrangement = Arrangement.Center) {
//                if (isRunning) {
//                    Button(onClick = { viewModel.toggleTimer() }) { Text("Pause") }
//                } else {
//                    Button(onClick = { viewModel.toggleTimer() }) { Text("Start") }
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Button(onClick = { viewModel.resetTimer() }) { Text("Reset") }
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Button(onClick = { viewModel.stopTimer() }) { Text("Stop") }
//                }
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            // Lottie Animation (centered, fills lower half)
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f),
//                contentAlignment = Alignment.Center
//            ) {
//                LottieAnimation(
//                    composition = composition,
//                    progress = { lottieAnimatable.progress },
//                    modifier = Modifier.size(220.dp)
//                )
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = "Completed: $roundsCompleted session${if (roundsCompleted == 1) "" else "s"}",
//                style = MaterialTheme.typography.bodyMedium,
//                color = MaterialTheme.colorScheme.onSurfaceVariant
//            )
//        }
//    }
//}
@Composable
fun TimerScreen(
    viewModel: TimerViewModel,
    onEditClick: () -> Unit = {}
) {
    val timeLeft by viewModel.timeLeft.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val roundsCompleted by viewModel.roundsCompleted.collectAsState()

    val lottieRes = if (isRunning && timeLeft > 0) R.raw.work else R.raw.break_anim
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieRes))
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(composition, lottieRes) {
        lottieAnimatable.animate(composition, iterations = LottieConstants.IterateForever)
    }

    var showEditDialog by remember { mutableStateOf(false) }
    var showControls by remember { mutableStateOf(false) }

    val hours = timeLeft / 3600
    val minutes = (timeLeft % 3600) / 60
    val seconds = timeLeft % 60

    if (showEditDialog) {
        TimePickerDialog(
            initialMinutes = minutes,
            initialSeconds = seconds,
            onDismiss = { showEditDialog = false },
            onTimeSelected = { selectedMinutes, selectedSeconds ->
                val newTimeInSeconds = selectedMinutes * 60 + selectedSeconds
                viewModel.updateTimer(newTimeInSeconds)
                showEditDialog = false
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        IconButton(onClick = { showEditDialog = true }, modifier = Modifier.align(Alignment.TopEnd)) {
            Icon(Icons.Default.Edit, contentDescription = "Edit")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Flip Clock
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = String.format("%02d:%02d:%02d", hours, minutes, seconds),
                style = MaterialTheme.typography.displayLarge
            )

            // Button Group
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (!showControls) {
                    Button(onClick = { showControls = true }) {
                        Text("Pause")
                    }
                } else {
                    Row {
                        Button(onClick = { viewModel.toggleTimer(); showControls = false }) {
                            Text("Play")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { viewModel.resetTimer(); showControls = false }) {
                            Text("Reset")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { viewModel.stopTimer(); showControls = false }) {
                            Text("Stop")
                        }
                    }
                }
            }

            // Totoro Animation
            LottieAnimation(
                composition = composition,
                progress = { lottieAnimatable.progress },
                modifier = Modifier.size(240.dp)
            )

            Text(
                text = "Completed: $roundsCompleted session${if (roundsCompleted == 1) "" else "s"}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


