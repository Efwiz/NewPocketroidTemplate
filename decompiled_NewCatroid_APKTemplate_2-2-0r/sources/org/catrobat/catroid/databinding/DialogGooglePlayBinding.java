package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogGooglePlayBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogGooglePlayBinding implements ViewBinding {
    public final TextView dialogGooglePlayText;
    private final ScrollView rootView;

    private DialogGooglePlayBinding(ScrollView rootView, TextView dialogGooglePlayText) {
        this.rootView = rootView;
        this.dialogGooglePlayText = dialogGooglePlayText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogGooglePlayBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogGooglePlayBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_google_play, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogGooglePlayBinding bind(View rootView) {
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_google_play_text);
        if (textView != null) {
            return new DialogGooglePlayBinding((ScrollView) rootView, textView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(R.id.dialog_google_play_text)));
    }
}
