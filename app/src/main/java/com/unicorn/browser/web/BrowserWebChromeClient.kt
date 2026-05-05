package com.unicorn.browser.web

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar

class BrowserWebChromeClient(
    private val progressBar: ProgressBar,
    private val onTitle: (String) -> Unit
) : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        progressBar.progress = newProgress
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        onTitle(title ?: "Browser")
    }
}