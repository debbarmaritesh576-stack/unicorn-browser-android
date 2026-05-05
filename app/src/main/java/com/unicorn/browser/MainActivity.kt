package com.unicorn.browser

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var urlInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        urlInput = findViewById(R.id.urlInput)

        val btnGo: Button = findViewById(R.id.btnGo)
        val btnBack: Button = findViewById(R.id.btnBack)
        val btnForward: Button = findViewById(R.id.btnForward)
        val btnHome: Button = findViewById(R.id.btnHome)

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        loadUrl("https://www.google.com")

        btnGo.setOnClickListener {
            loadUrl(urlInput.text.toString())
        }

        btnBack.setOnClickListener {
            if (webView.canGoBack()) webView.goBack()
        }

        btnForward.setOnClickListener {
            if (webView.canGoForward()) webView.goForward()
        }

        btnHome.setOnClickListener {
            loadUrl("https://www.google.com")
        }
    }

    private fun loadUrl(input: String) {
        val url = if (input.startsWith("http")) input else "https://$input"
        webView.loadUrl(url)
        urlInput.setText(url)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }
}