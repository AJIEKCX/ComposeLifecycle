package ru.alexpanov.composelifecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.alexpanov.composelifecycle.ui.theme.ComposeLifecycleTheme
import ru.alexpanov.composelifecycle.ui.theme.LocalIcons

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ComposeLifecycleTheme {
                ProvideWindowInsets {
                    val systemUiController = rememberSystemUiController()
                    val useDarkIcons = MaterialTheme.colors.isLight
                    SideEffect {
                        systemUiController.setSystemBarsColor(
                            color = Color.Transparent,
                            darkIcons = useDarkIcons
                        )
                    }
                    MyScreen()
                }
            }
        }
    }
}

@Composable
fun MyScreen() {
    CounterHolder(factory = { Counter() }) { counter ->
        val count by counter.state.collectAsState()
        MyHomePage(count, onClick = counter::increment)
    }
}

@Composable
fun CounterHolder(
    factory: () -> Counter,
    content: @Composable (Counter) -> Unit
) {
    val counter = remember { factory.invoke() }
    content(counter)
    DisposableEffect(Unit) {
        onDispose {
            counter.dispose()
        }
    }
}


@Composable
fun MyHomePage(count: Int, onClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Counter") },
                modifier = Modifier.statusBarsPadding()
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onClick) {
                // Won't changed in night theme if using resources with night or other qualifier
                // Icon(painterResource(R.drawable.ic_user), contentDescription = null)
                Icon(LocalIcons.current.user, contentDescription = null)
            }
        }
    ) {
        BoxWithConstraints {
            if (maxWidth > 400.dp) {
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(stringResource(R.string.counter_title))
                    Spacer(Modifier.width(16.dp))
                    Text(count.toString(), style = MaterialTheme.typography.h4)
                }
            } else {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(stringResource(R.string.counter_title))
                    Text(count.toString(), style = MaterialTheme.typography.h4)
                }
            }
        }
    }
}