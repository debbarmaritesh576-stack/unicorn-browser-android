package com.unicorn.browser.web

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.EditText

class BrowserWebViewClient(
    private val progressBar: ProgressBar,
    private val urlInput: EditText
) : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        progressBar.visibility = View.VISIBLE
        urlInput.setText(url ?: "")
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        progressBar.visibility = View.GONE
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return false
    }
}