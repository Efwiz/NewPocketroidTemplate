package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickClearUserlistBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickClearUserlistBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickClearUserlistLayout;
    public final TextView clearUserlistLabel;
    public final Spinner clearUserlistSpinner;
    private final LinearLayout rootView;

    private BrickClearUserlistBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickClearUserlistLayout, TextView clearUserlistLabel, Spinner clearUserlistSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickClearUserlistLayout = brickClearUserlistLayout;
        this.clearUserlistLabel = clearUserlistLabel;
        this.clearUserlistSpinner = clearUserlistSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickClearUserlistBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickClearUserlistBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_clear_userlist, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickClearUserlistBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_clear_userlist_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_clear_userlist_layout);
            if (brickLayout != null) {
                i = R.id.clear_userlist_label;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.clear_userlist_label);
                if (textView != null) {
                    i = R.id.clear_userlist_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.clear_userlist_spinner);
                    if (spinner != null) {
                        return new BrickClearUserlistBinding((LinearLayout) rootView, checkBox, brickLayout, textView, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
