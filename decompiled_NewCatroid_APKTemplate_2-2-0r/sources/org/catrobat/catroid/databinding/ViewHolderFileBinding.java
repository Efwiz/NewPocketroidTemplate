package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ViewHolderFileBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ViewHolderFileBinding implements ViewBinding {
    public final CheckBox checkbox;
    private final LinearLayout rootView;
    public final TextView subtitleView;
    public final TextView titleView;

    private ViewHolderFileBinding(LinearLayout rootView, CheckBox checkbox, TextView subtitleView, TextView titleView) {
        this.rootView = rootView;
        this.checkbox = checkbox;
        this.subtitleView = subtitleView;
        this.titleView = titleView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ViewHolderFileBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ViewHolderFileBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.view_holder_file, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ViewHolderFileBinding bind(View rootView) {
        int i = R.id.checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.checkbox);
        if (checkBox != null) {
            i = R.id.subtitle_view;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.subtitle_view);
            if (textView != null) {
                i = R.id.title_view;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.title_view);
                if (textView2 != null) {
                    return new ViewHolderFileBinding((LinearLayout) rootView, checkBox, textView, textView2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
