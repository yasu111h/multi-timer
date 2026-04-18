package com.multitimer.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.multitimer.app.ui.MainScreen
import com.multitimer.app.ui.theme.MultiTimerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        // スプラッシュを最初のフレーム描画後すぐに非表示（延長しない）
        splashScreen.setKeepOnScreenCondition { false }
        super.onCreate(savedInstanceState)
        // ナビゲーションバー・ステータスバーを透明（ダークアイコン）に設定
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )
        setContent {
            MultiTimerTheme {
                MainScreen()
            }
        }
    }
}
