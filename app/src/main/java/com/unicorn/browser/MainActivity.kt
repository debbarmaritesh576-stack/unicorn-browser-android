package com.unicorn.browser

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.unicorn.browser.tabs.TabsActivity
import com.unicorn.browser.tabs.TabsManager
import com.unicorn.browser.web.BrowserWebChromeClient
import com.unicorn.browser.web.BrowserWebViewClient

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var urlInput: EditText
    private lateinit var progressBar: ProgressBar

    private val REQ_TABS = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupWebView()
        setupListeners()

        // Initialize tabs
        TabsManager.initIfEmpty("https://www.google.com")

        TabsManager.getCurrent()?.let {
            loadUrl(it.url)
        }
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
        val btnTabs: Button = findViewById(R.id.btnTabs)

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

        btnTabs.setOnClickListener {
            startActivityForResult(
                Intent(this, TabsActivity::class.java),
                REQ_TABS
            )
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

        TabsManager.updateCurrentUrl(finalUrl)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_TABS && resultCode == Activity.RESULT_OK) {
            TabsManager.getCurrent()?.let {
                loadUrl(it.url)
            }
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }
}