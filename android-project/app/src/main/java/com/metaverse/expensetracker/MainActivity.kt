package com.metaverse.expensetracker

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true

        webView.webViewClient = WebViewClient()

        webView.loadUrl(
            "https://raw.githubusercontent.com/Metaverse0909/Expenses-Tracker/main/Paisa-Ledger-Complete-Sensory-UX-Haptics.html"
        )

        setContentView(webView)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
