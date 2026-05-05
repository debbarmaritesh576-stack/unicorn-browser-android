package com.unicorn.browser

import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.unicorn.browser.web.BrowserWebChromeClient
import com.unicorn.browser.web.BrowserWebViewClient

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var urlInput: EditText
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupWebView()
        setupListeners()

        loadUrl("https://www.google.com")
    }

    private fun initViews() {
        webView = findViewById(R.id.webView)
        urlInput = findViewById(R.id.urlInput)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setupWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadsImagesAutomatically = true
        }

        webView.webViewClient = BrowserWebViewClient(
            progressBar,
            urlInput
        )

        webView.webChromeClient = BrowserWebChromeClient(
            progressBar
        ) { title ->
            this.title = title
        }
    }

    private fun setupListeners() {
        val btnGo: Button = findViewById(R.id.btnGo)
        val btnBack: Button = findViewById(R.id.btnBack)
        val btnForward: Button = findViewById(R.id.btnForward)
        val btnHome: Button = findViewById(R.id.btnHome)

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

        urlInput.setOnEditorActionListener { _, _, _ ->
            loadUrl(urlInput.text.toString())
            true
        }
    }

    private fun loadUrl(input: String) {
        val finalUrl = when {
            input.startsWith("http://") || input.startsWith("https://") -> input
            input.contains(" ") -> "https://www.google.com/search?q=${Uri.encode(input)}"
            else -> "https://$input"
        }

        webView.loadUrl(finalUrl)
        urlInput.setText(finalUrl)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }
}