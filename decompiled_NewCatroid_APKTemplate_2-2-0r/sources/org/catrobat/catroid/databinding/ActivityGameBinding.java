package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivityGameBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivityGameBinding implements ViewBinding {
    public final WebView myWebView;
    private final RelativeLayout rootView;

    private ActivityGameBinding(RelativeLayout rootView, WebView myWebView) {
        this.rootView = rootView;
        this.myWebView = myWebView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static ActivityGameBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityGameBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_game, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityGameBinding bind(View rootView) {
        WebView webView = (WebView) ViewBindings.findChildViewById(rootView, R.id.myWebView);
        if (webView != null) {
            return new ActivityGameBinding((RelativeLayout) rootView, webView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(R.id.myWebView)));
    }
}
