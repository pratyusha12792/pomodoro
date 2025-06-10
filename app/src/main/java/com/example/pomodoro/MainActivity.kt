package com.example.pomodoro
import TimerScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pomodoro.screen.ProfileScreen
import com.example.pomodoro.screen.StatsScreen
import com.example.pomodoro.ui.theme.PomodoroTheme
import com.example.pomodoro.viewModel.TimerViewModel

data class BottomNavItem(
    val title :String,
    val selectedIcon:ImageVector,
    val unselectedIcon:ImageVector
)
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val timerViewModel: TimerViewModel by viewModels()
        setContent {
            PomodoroTheme {
                var selectedIndex by rememberSaveable {
                    mutableStateOf(0)
                }
                val items = listOf(
                    BottomNavItem(
                        title="Timer",
                        selectedIcon = Icons.Filled.PlayArrow,
                        unselectedIcon = Icons.Outlined.PlayArrow
                    ),
                    BottomNavItem(
                        title="Progress",
                        selectedIcon = Icons.Filled.DateRange,
                        unselectedIcon = Icons.Outlined.DateRange
                    ),
                    BottomNavItem(
                        title="Setting",
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings
                    ),
                    
                )
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    when(selectedIndex){
                                        0->"Focus Time"
                                        1->"Tasks"
                                        2->"Settings"
                                        else->""
                                    }
                                )
                            }
                        )
                    },
                    bottomBar ={
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surface,
                            tonalElevation = 4.dp
                        ) {
                            val selectedColor = MaterialTheme.colorScheme.primary
                            val unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                            items.forEachIndexed{index, item ->
                                NavigationBarItem(
                                    selected =  selectedIndex==index,
                                    onClick = {
                                        selectedIndex=index
                                    },
                                    icon = {
                                        BadgedBox(
                                            badge={}
                                        ) {
                                            Icon(
                                                imageVector =if (index==selectedIndex){
                                                    item.selectedIcon
                                                }else{
                                                    item.unselectedIcon
                                                },
                                                contentDescription=item.title,
                                                tint = if (index == selectedIndex) selectedColor else unselectedColor
                                            )
                                        }
                                    },
                                    label={
                                        Text(text=item.title)
                                    },
                                    alwaysShowLabel = false
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                    {
                        when(selectedIndex){
                            0->TimerScreen(viewModel = timerViewModel)
                            1->StatsScreen()
                            2->ProfileScreen()
                        }
                    }
                }
            }
        }
    }
}