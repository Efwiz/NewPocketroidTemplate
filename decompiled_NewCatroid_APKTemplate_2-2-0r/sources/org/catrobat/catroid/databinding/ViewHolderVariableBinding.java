package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ViewHolderVariableBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ViewHolderVariableBinding implements ViewBinding {
    public final CheckBox checkbox;
    private final LinearLayout rootView;
    public final ImageButton settingsButton;
    public final TextView titleView;
    public final TextView valueView;

    private ViewHolderVariableBinding(LinearLayout rootView, CheckBox checkbox, ImageButton settingsButton, TextView titleView, TextView valueView) {
        this.rootView = rootView;
        this.checkbox = checkbox;
        this.settingsButton = settingsButton;
        this.titleView = titleView;
        this.valueView = valueView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ViewHolderVariableBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ViewHolderVariableBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.view_holder_variable, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ViewHolderVariableBinding bind(View rootView) {
        int i = R.id.checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.checkbox);
        if (checkBox != null) {
            i = R.id.settings_button;
            ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.settings_button);
            if (imageButton != null) {
                i = R.id.title_view;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.title_view);
                if (textView != null) {
                    i = R.id.value_view;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.value_view);
                    if (textView2 != null) {
                        return new ViewHolderVariableBinding((LinearLayout) rootView, checkBox, imageButton, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
