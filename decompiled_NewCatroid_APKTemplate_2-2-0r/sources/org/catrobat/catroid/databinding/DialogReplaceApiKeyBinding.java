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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogReplaceApiKeyBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogReplaceApiKeyBinding implements ViewBinding {
    public final TextView replaceApiKey;
    public final TextView replaceApiKeyWarning;
    private final LinearLayout rootView;

    private DialogReplaceApiKeyBinding(LinearLayout rootView, TextView replaceApiKey, TextView replaceApiKeyWarning) {
        this.rootView = rootView;
        this.replaceApiKey = replaceApiKey;
        this.replaceApiKeyWarning = replaceApiKeyWarning;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogReplaceApiKeyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogReplaceApiKeyBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_replace_api_key, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogReplaceApiKeyBinding bind(View rootView) {
        int i = R.id.replace_api_key;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.replace_api_key);
        if (textView != null) {
            i = R.id.replace_api_key_warning;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.replace_api_key_warning);
            if (textView2 != null) {
                return new DialogReplaceApiKeyBinding((LinearLayout) rootView, textView, textView2);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
