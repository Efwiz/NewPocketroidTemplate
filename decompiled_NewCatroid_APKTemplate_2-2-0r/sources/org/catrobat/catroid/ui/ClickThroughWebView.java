package org.catrobat.catroid.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ClickThroughWebView.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ClickThroughWebView extends WebView {
    private boolean isClickThrough;

    public ClickThroughWebView(Context context) {
        super(context);
        this.isClickThrough = true;
    }

    public ClickThroughWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isClickThrough = true;
    }

    public ClickThroughWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.isClickThrough = true;
    }

    public void setClickThrough(boolean isClickThrough) {
        this.isClickThrough = isClickThrough;
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (this.isClickThrough) {
            return false;
        }
        return super.onTouchEvent(event);
    }
}
