package com.syf.unifidemoandroid

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class SyfPageActivity : AppCompatActivity() {
    private val TAG = "SyfPageActivity"
    //private val url = "https://ppdpone.syfpos.com/mit/?widgetload=y&amount=100&flowType=pdp&partnerId=PI53421676&calcLoadTime=N";
    // private val url = "https://ppdpone.syfpos.com/mpp/mpp?partnerId=PI53421676&amount=800&syfToken=MPP16706334026627PI53421676&productCategoryNames=&flowType=PDP&cid=unifitest&adobe_mc=MCMID%3D11699604732310737292115892261680123387%7CMCORGID%3D22602B6956FAB4777F000101%2540AdobeOrg%7CTS%3D1670633403&_ga=2.172105827.1545651286.1670631858-1876618331.1669861855"
    private val url = ""
    private lateinit var syfWebView:WebView
    private lateinit var closeButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_syf_page)

        // Custom code..
        syfWebView = findViewById<WebView>(R.id.syfWebView)
        val webSettings = syfWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.domStorageEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.supportMultipleWindows()
//        syfWebView.loadUrl("https://dpdpone.syfpos.com/mpp/native-android-dbuy.html")
        syfWebView.loadUrl("http://10.0.2.2:8000/static/native-app-non-unify-copy.html")
//        syfWebView.loadUrl("file:///android_asset/native-app-non-unify.html.html")
//        Log.v(TAG, "onCreate url=" + url);
        syfWebView.webViewClient = UnifiWebViewClient()
//        WebView.setWebContentsDebuggingEnabled(false)
        syfWebView.addJavascriptInterface(UnifiAndroidJavascriptIntf(this), "UnifiAndroidJSIntf")

        closeButton = findViewById<Button>(R.id.button_load)
        closeButton.setOnClickListener {
            this.closeModel();
        }
    }

    private inner class UnifiWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Log.v(TAG, "shouldOverrideUrlLoading url=" + url);
            return if (url != null && (url.startsWith("https://dpdpone.syfpos.com/cs/")
                        || url.startsWith("https://www.synchrony.com/")
                        || url.startsWith("https://qbuy.syf.com/"))) {
                view.context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(url))
                )
                true
            } else {
                false // open in-app
            }
        }
        override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
            // showProgress(true)
            view.clearCache(true)
            Log.v(TAG, "onPageStarted url=$url");
            super.onPageStarted(view, url, favicon)
        }
        override fun onPageFinished(view: WebView, url: String) {
            // TODO Auto-generated method stub

            super.onPageFinished(view, url)
            var jsonPayloadScript = "{processInd:\"3\"," + "tokenId:\"53481209400999711679317398592\",merchantID:\"5348120940099971\",childMid:\"\",clientTransId:\"\",custFirstName:\"\",custLastName:\"\",custZipCode:\"\",cardNumber:\"\",expMonth:\"\",expYear:\"\",iniPurAmt:\"\",custAddress1:\"\",custAddress2:\"\",phoneNumber:\"\",emailAddress:\"\",custCity:\"\",custState:\"\",upeProgramName:\"\",transPromo1:\"\",transAmount1:\"900\",transPromo2:\"\",transAmount2:\"\",transPromo3:\"\",transAmount3:\"\",mid:\"5348120940099971\",pcgc:\"\",defaultPromoCode:\"\"}"
            val mapper = jacksonObjectMapper();
            val dbuyTipFormModal = DBuyTipFormModal( "3", "53481209400999711678989522963", "5348120940099971", "","", "","","","","","","","","","","","","","","","900","","","","","5348120940099971","","")
            val tipFormJson = mapper.writeValueAsString(dbuyTipFormModal)
            println(tipFormJson)
            view.evaluateJavascript("javascript:loadJsonObjectAndroid($jsonPayloadScript)",null)
            val cookies = CookieManager.getInstance().getCookie(url)
            Log.d(TAG, "All the cookies in a string:$cookies")
            Log.v(TAG, "onPageFinished url=$url");
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            val errorMessage = "Got Error! $error"
            Log.v(TAG, "onReceivedError =$errorMessage");
            println("Got Error! $error")
            super.onReceivedError(view, request, error)
        }
    }

    private inner class UnifiAndroidJavascriptIntf(private val context: Context) {
        private val TAG = "UnifiAndroidJavascriptI"
        @JavascriptInterface
        fun setStatusMessageFromJS(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            Log.v(TAG, "receiveMessage =$message");
            if(message=="syf-close-modal"){
               closeModel();
            }

        }
    }

    override fun onBackPressed() {
        if(syfWebView.canGoBack()){
            syfWebView.goBack()
        }else{
            super.onBackPressed()
            this.closeModel();
        }
    }

     fun closeModel() {
        val intent = Intent(this, MainActivity::class.java) // if message is buttonclose
        this.startActivity(intent)
//        syfWebView.destroy();
    }
}
