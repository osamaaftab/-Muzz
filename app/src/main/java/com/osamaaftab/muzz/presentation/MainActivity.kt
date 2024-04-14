package com.osamaaftab.muzz.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.osamaaftab.muzz.presentation.compose.MuzzApp
import com.osamaaftab.muzz.presentation.theme.MuzzTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuzzTheme {
                MuzzApp(this)
            }
        }
    }
}