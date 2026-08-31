package com.metaverse.expensetracker

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

val webView = findViewById<WebView>(R.id.webView)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true

        webView.webViewClient = WebViewClient()

        webView.loadUrl(
            "https://raw.githubusercontent.com/Metaverse0909/Expenses-Tracker/main/Paisa-Ledger-Complete-Sensory-UX-Haptics.html"
        )

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
