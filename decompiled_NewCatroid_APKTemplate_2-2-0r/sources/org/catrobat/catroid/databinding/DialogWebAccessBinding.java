package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogWebAccessBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogWebAccessBinding implements ViewBinding {
    public final TextView requestUrl;
    public final TextView requestWarning;
    private final LinearLayout rootView;

    private DialogWebAccessBinding(LinearLayout rootView, TextView requestUrl, TextView requestWarning) {
        this.rootView = rootView;
        this.requestUrl = requestUrl;
        this.requestWarning = requestWarning;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogWebAccessBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogWebAccessBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_web_access, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogWebAccessBinding bind(View rootView) {
        int i = R.id.request_url;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.request_url);
        if (textView != null) {
            i = R.id.request_warning;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.request_warning);
            if (textView2 != null) {
                return new DialogWebAccessBinding((LinearLayout) rootView, textView, textView2);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
