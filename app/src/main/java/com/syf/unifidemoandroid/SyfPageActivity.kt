package com.syf.unifidemoandroid

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_syf_page)
        var myButton = findViewById<Button>(R.id.button_load)
//        myButton.setOnClickListener {
//            // make a toast on button click event
//            Toast.makeText(this, "Hi there! This is a Toast.", Toast.LENGTH_LONG).show()
//        }

        // Custom code..
        val syfWebView = findViewById<WebView>(R.id.syfWebView)
        val webSettings = syfWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.domStorageEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.supportMultipleWindows()
        syfWebView.loadUrl("file:///android_asset/Hello.html")

//        Log.v(TAG, "onCreate url=" + url);
//        syfWebView.loadUrl(url)
//        syfWebView.addJavascriptInterface(UnifiAndroidJavascriptIntf(this), "UnifiAndroidJSIntf")
        syfWebView.addJavascriptInterface(WebAppInterface(this), "Android")

        syfWebView.webViewClient = UnifiWebViewClient()
        syfWebView.webChromeClient = MyWebChromeClient()
//        WebView.setWebContentsDebuggingEnabled(false)
    }

    class WebAppInterface(private val mContext: Context) {

        /** Show a toast from the web page  */
        @JavascriptInterface
        fun showToast(toast: String) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
        }

        @JavascriptInterface
        fun calledFromJs(){
            Log.d("KEY", "js called from Kotlin")
        }
    }
    private inner class MyWebChromeClient : WebChromeClient() {
        override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
            Log.d("LogTag", message)
            result.confirm()
            return true
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
            val mapper = jacksonObjectMapper();
//            val strategyInfo = StrategyInfo("H");
//            val merchantInfo = MerchantInfo("N123456", "SYF ZETAIL Merchant", "ZETAIL VISA Rewards Card", "BC", "A600", "5348120820046126", "5348120820030393", "PI1000011702")
//            val applicantInfo = ApplicantInfo("pavankumar.padamati@syf.com", "950", 950)
//            val d2DModal = D2DModal("N",applicantInfo, merchantInfo, strategyInfo, "EMAIL_AND_MERCHANT", "https://qpdpone.syfpos.com/mppvore/d2d", "MPP")
            val tipFormModal = TipFormModal("421676PI53", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "1000", "", "", "", "", "","", "", "","", "", "","")
            val tipFormJson = mapper.writeValueAsString(tipFormModal)
            println("tipFormJson: $tipFormJson")

            Log.v(TAG, "onPageStarted url=" + url);
//            var mppCall = "var mpp = \"https://ppdpone.syfpos.com/mpp/mpp?partnerId=PI53421676&amount=800&syfToken=MPP16706334026627PI53421676&productCategoryNames=&flowType=PDP&cid=unifitest&adobe_mc=MCMID%3D11699604732310737292115892261680123387%7CMCORGID%3D22602B6956FAB4777F000101%2540AdobeOrg%7CTS%3D1670633403&_ga=2.172105827.1545651286.1670631858-1876618331.1669861855\";"
//            var jsonPayloadScript = "var jsonObj = {syfPartnerId:\"PI53421676\"," +
//                    "tokenId:\"PI5342167645385185df6e3851\"," +
//                    "encryptKey:\"\",modalType:\"\",childMid:\"\",childPcgc:\"\",childTransType:\"\",pcgc:\"\",partnerCode:\"\",clientToken:\"\",postbackid:\"d979e5b7-6382-4e4e-b269-aab027bbed58\",clientTransId:\"\",cardNumber:\"\",custFirstName:\"\",custLastName:\"\",expMonth:\"\",expYear:\"\",custZipCode:\"\",custAddress1:\"\",phoneNumb:\"\",appartment:\"\",emailAddr:\"\",custCity:\"\",upeProgramName:\"\",custState:\"\",transPromo1:\"\",iniPurAmt:\"\",mid:\"\",productCategoryNames:\"\",transAmount1:\"700\",transAmounts:\"\",initialAmount:\"\",envUrl:\"https://dpdpone.syfpos.com/mitservice/\",productAttributes:\"\",processInd:\"3\"}"
            //var mppJsonObject = "var mpp = {envUrl:\"https://ppdpone.syfpos.com/mpp/mpp?partnerId=PI53421676&amount=800&syfToken=MPP16706334026627PI53421676&productCategoryNames=&flowType=PDP&cid=unifitest&adobe_mc=MCMID%3D11699604732310737292115892261680123387%7CMCORGID%3D22602B6956FAB4777F000101%2540AdobeOrg%7CTS%3D1670633403&_ga=2.172105827.1545651286.1670631858-1876618331.1669861855\"}"
            var mppJsonObject = "var tipFormJson = $tipFormJson"
            view.evaluateJavascript(mppJsonObject, null)
            super.onPageStarted(view, url, favicon)
            val javaScript = "javascript:(function() {alert();})()"
            view.loadUrl(javaScript)
        }
        override fun onPageFinished(view: WebView, url: String) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url)
            Log.v(TAG, "onPageFinished url=" + url);
        }
        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            val errorMessage = "Got Error! $error"
            Log.v(TAG, "onReceivedError =" + errorMessage);
            super.onReceivedError(view, request, error)
        }
    }

    private inner class UnifiAndroidJavascriptIntf(private val context: Context) {
        private val TAG = "UnifiAndroidJavascriptI"
        @JavascriptInterface
        fun setStatusMessageFromJS(message: String) {
             Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            Log.v(TAG, "receiveMessage =" + message);
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}