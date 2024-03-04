package com.example.repeatsound

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exercise03.Event
import com.example.repeatsound.ScreensName.Companion.aboutScreenName
import com.example.repeatsound.ScreensName.Companion.gameScreenName
import com.example.repeatsound.ScreensName.Companion.menuScreenName
import com.example.repeatsound.screens.AboutScreen
import com.example.repeatsound.screens.GameScreen
import com.example.repeatsound.screens.MenuScreen
import com.example.repeatsound.ui.theme.Exercise03Theme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: ScreenViewModel
    private val sharedPreferencesKey = "record"
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        viewModel = ScreenViewModel(this)
        viewModel.sendEvent(
            Event.ChangeRecord(
                sharedPreferences.getInt(sharedPreferencesKey, 0)
            )
        )

        setContent {
            val navController = rememberNavController()
            Exercise03Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = menuScreenName
                    )
                    {
                        composable(menuScreenName) {
                            MenuScreen {
                                navController.navigate(it)
                            }
                        }
                        composable(gameScreenName) {
                            GameScreen(mv = viewModel,
                                changeRecord = { record ->
                                    sharedPreferences.edit()
                                        .putInt(sharedPreferencesKey, record).apply()
                                },
                                onClickNav = { navController.navigate(menuScreenName) }
                            )
                        }
                        composable(aboutScreenName) {
                            val record = viewModel.state.collectAsState().value.record
                            AboutScreen(record = record,
                                onClickNav = { navController.navigate(menuScreenName) })
                        }
                    }
                }
            }
        }
    }
}

class ScreensName {
    companion object {
        const val menuScreenName = "menuScreen"
        const val gameScreenName = "gameScreen"
        const val aboutScreenName = "aboutScreen"
    }
}

