package com.hcaptcha.sdk;

import android.content.Context;
import android.os.Looper;
import android.util.AttributeSet;
import android.webkit.WebView;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public class HCaptchaWebView extends WebView {
    public HCaptchaWebView(Context context) {
        super(context);
    }

    public boolean isDestroyed() {
        return getParent() == null;
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean onCheckIsTextEditor() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return super.onCheckIsTextEditor();
        }
        return false;
    }

    @Override // android.view.View
    public boolean performClick() {
        return false;
    }

    public HCaptchaWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HCaptchaWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public HCaptchaWebView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
