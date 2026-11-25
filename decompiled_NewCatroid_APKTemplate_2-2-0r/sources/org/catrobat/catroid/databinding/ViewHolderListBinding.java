package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ViewHolderListBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ViewHolderListBinding implements ViewBinding {
    public final CheckBox checkbox;
    private final LinearLayout rootView;
    public final ImageButton settingsButton;
    public final Spinner spinner;
    public final TextView titleView;

    private ViewHolderListBinding(LinearLayout rootView, CheckBox checkbox, ImageButton settingsButton, Spinner spinner, TextView titleView) {
        this.rootView = rootView;
        this.checkbox = checkbox;
        this.settingsButton = settingsButton;
        this.spinner = spinner;
        this.titleView = titleView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ViewHolderListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ViewHolderListBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.view_holder_list, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ViewHolderListBinding bind(View rootView) {
        int i = R.id.checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.checkbox);
        if (checkBox != null) {
            i = R.id.settings_button;
            ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.settings_button);
            if (imageButton != null) {
                i = R.id.spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.spinner);
                if (spinner != null) {
                    i = R.id.title_view;
                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.title_view);
                    if (textView != null) {
                        return new ViewHolderListBinding((LinearLayout) rootView, checkBox, imageButton, spinner, textView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
