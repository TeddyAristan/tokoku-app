package com.example.tokoku

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.tokoku.model.FormViewModel
import com.example.tokoku.ui.theme.TokoKuTheme
import com.example.tokoku.utils.FormDatabase
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle


class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            FormDatabase::class.java,
            "forms.db"
        ).build()
    }

    private val viewModel by viewModels<FormViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(FormViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return FormViewModel(db.dao) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000)
        installSplashScreen()

        setContent {
            TokoKuTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    MainScreenPreview(state = state, onEvent= viewModel::onEvent)
                }
            }
        }
    }
}
