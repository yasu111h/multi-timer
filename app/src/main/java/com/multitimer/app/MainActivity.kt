package com.multitimer.app

import android.os.Bundle
import android.os.SystemClock
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
        // super.onCreate()より前に計測開始することで、Hilt初期化時間も含めて1秒を計測
        val splashStartTime = SystemClock.elapsedRealtime()
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            // 起動開始から1秒未満の間だけスプラッシュを表示
            // ただしHilt初期化がそれ以上かかる場合は初期化完了まで待つ（短縮不可）
            SystemClock.elapsedRealtime() - splashStartTime < 1000
        }
        super.onCreate(savedInstanceState)
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
