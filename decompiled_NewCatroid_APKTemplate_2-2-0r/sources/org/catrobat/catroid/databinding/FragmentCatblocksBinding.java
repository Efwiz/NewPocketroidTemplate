package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentCatblocksBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentCatblocksBinding implements ViewBinding {
    public final WebView catblocksWebView;
    public final LinearLayout fragmentCatblocks;
    private final LinearLayout rootView;

    private FragmentCatblocksBinding(LinearLayout rootView, WebView catblocksWebView, LinearLayout fragmentCatblocks) {
        this.rootView = rootView;
        this.catblocksWebView = catblocksWebView;
        this.fragmentCatblocks = fragmentCatblocks;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentCatblocksBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentCatblocksBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_catblocks, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentCatblocksBinding bind(View rootView) {
        WebView webView = (WebView) ViewBindings.findChildViewById(rootView, R.id.catblocksWebView);
        if (webView != null) {
            LinearLayout linearLayout = (LinearLayout) rootView;
            return new FragmentCatblocksBinding(linearLayout, webView, linearLayout);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(R.id.catblocksWebView)));
    }
}
