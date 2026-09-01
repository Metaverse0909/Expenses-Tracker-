package com.metaverse.expensetracker

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : Activity() {

    private val splashDuration = 1500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        webView.loadUrl("file:///android_asset/index.html")
    }

    override fun onBackPressed() {
        val webView = findViewById<WebView>(R.id.webView)

        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
