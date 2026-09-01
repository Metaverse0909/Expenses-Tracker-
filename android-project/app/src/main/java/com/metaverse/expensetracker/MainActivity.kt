package com.metaverse.expensetracker

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : Activity() {

    private val splashDuration = 1500L
    private val notificationChannelId = "ledvix_alerts_v3"
    private val notificationPermissionRequestCode = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()
        requestNotificationPermission()

        showSplash()

        Handler(Looper.getMainLooper()).postDelayed({
            showApp()
        }, splashDuration)
    }

    private fun showSplash() {
        setContentView(R.layout.splash_screen)
    }

    private fun showApp() {
        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webView)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true

        webView.webViewClient = WebViewClient()

        webView.addJavascriptInterface(
            LedvixNotificationBridge(),
            "LedvixAndroid"
        )

        webView.loadUrl("file:///android_asset/index.html")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val soundUri: Uri =
                Settings.System.DEFAULT_NOTIFICATION_URI

            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(
                    AudioAttributes.CONTENT_TYPE_SONIFICATION
                )
                .build()

            val channel = NotificationChannel(
                notificationChannelId,
                "Ledvix Alerts",
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.description =
                "Ledvix expense and budget alerts"

            channel.setSound(
                soundUri,
                audioAttributes
            )

            channel.enableVibration(true)

            channel.vibrationPattern =
                longArrayOf(0, 250, 100, 250)

            channel.setShowBadge(true)

            val notificationManager =
                getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (
                checkSelfPermission(
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.POST_NOTIFICATIONS
                    ),
                    notificationPermissionRequestCode
                )
            }
        }
    }

    private fun showNotification(
        title: String,
        message: String
    ) {

        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val openAppIntent = Intent(
            this,
            MainActivity::class.java
        ).apply {
            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            this,
            notificationChannelId
        )
            .setSmallIcon(R.drawable.ledvix_logo)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVibrate(
                longArrayOf(0, 250, 100, 250)
            )
            .setSound(
                Settings.System.DEFAULT_NOTIFICATION_URI
            )
            .build()

        NotificationManagerCompat
            .from(this)
            .notify(
                System.currentTimeMillis().toInt(),
                notification
            )
    }

    private inner class LedvixNotificationBridge {

        @JavascriptInterface
        fun showNotification(
            title: String,
            message: String
        ) {
            runOnUiThread {
                this@MainActivity.showNotification(
                    title,
                    message
                )
            }
        }
    }

    override fun onBackPressed() {

        val webView = findViewById<WebView>(R.id.webView)

        if (
            webView != null &&
            webView.canGoBack()
        ) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
