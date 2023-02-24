package com.syf.unifidemoandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get reference to button
        val buttonClick = findViewById(R.id.button_merchant_page) as Button
        // set on-click listener
        buttonClick.setOnClickListener {
            val intent = Intent(this, MerchantPageActivity::class.java)
            startActivity(intent)
        }

        // get reference to button
        val syfButtonClick = findViewById(R.id.button_syf) as Button
        // set on-click listener
        syfButtonClick.setOnClickListener {
//            val intent = Intent(this, SyfPageActivity::class.java)
            val intent = Intent(this, SyfPageActivity::class.java)
            startActivity(intent)
            var webView = WebView(this)
        }
    }
}
        //Create WebView At Run Time
//        webView.settings.javaScriptEnabled = true
//        webView.loadUrl("file:///android_asset/Hello.html")
//        setContentView(webView)
//
//        webView.addJavascriptInterface(WebAppInterface(this, webView), "NativeJavascriptInterface") // To call methods in Android from using js in the html, NativeJavascriptInterface.showToast, NativeJavascriptInterface.getAndroidVersion etc
//        val webSettings = webView.getSettings()
//        webSettings.setJavaScriptEnabled(true)
//        webView.setWebViewClient(MyWebViewClient())
//        webView.setWebChromeClient(MyWebChromeClient())
//
//    }
//
//    private inner class MyWebViewClient : WebViewClient() {
//        override fun onPageFinished(view: WebView, url: String) {
//            //Calling a javascript function in html page
//            view.loadUrl("javascript:alert(showVersion('called by Android'))")
//        }
//    }
//
//    private inner class MyWebChromeClient : WebChromeClient() {
//        override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
//            Log.d("LogTag", message)
//            result.confirm()
//            return true
//        }
//    }
//}