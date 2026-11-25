package org.catrobat.paintroid.web;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.catrobat.paintroid.R;

/* JADX INFO: loaded from: classes14.dex */
public class MediaGalleryWebViewClient extends WebViewClient {
    private WebClientCallback callback;
    private ProgressDialog webViewLoadingDialog;

    public interface WebClientCallback {
        void finish();
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        return false;
    }

    public MediaGalleryWebViewClient(WebClientCallback webClientCallback) {
        this.callback = webClientCallback;
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        if (this.webViewLoadingDialog == null && !str.matches("https://share.catrob.at/pocketcode/")) {
            ProgressDialog progressDialog = new ProgressDialog(webView.getContext(), R.style.WebViewLoadingCircle);
            this.webViewLoadingDialog = progressDialog;
            progressDialog.setCancelable(true);
            this.webViewLoadingDialog.setCanceledOnTouchOutside(false);
            this.webViewLoadingDialog.setProgressStyle(android.R.style.Widget.ProgressBar.Small);
            this.webViewLoadingDialog.show();
            return;
        }
        this.callback.finish();
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        ProgressDialog progressDialog = this.webViewLoadingDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.webViewLoadingDialog = null;
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, int i, String str, String str2) {
        this.callback.finish();
    }
}
