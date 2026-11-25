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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickRegexBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickRegexBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickRegexEditRegex;
    public final TextView brickRegexEditText;
    public final BrickLayout brickRegexLayout;
    public final Spinner regexUserlistSpinner;
    private final LinearLayout rootView;

    private BrickRegexBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickRegexEditRegex, TextView brickRegexEditText, BrickLayout brickRegexLayout, Spinner regexUserlistSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickRegexEditRegex = brickRegexEditRegex;
        this.brickRegexEditText = brickRegexEditText;
        this.brickRegexLayout = brickRegexLayout;
        this.regexUserlistSpinner = regexUserlistSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickRegexBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickRegexBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_regex, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickRegexBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_regex_edit_regex;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_regex_edit_regex);
            if (textView != null) {
                i = R.id.brick_regex_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_regex_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_regex_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_regex_layout);
                    if (brickLayout != null) {
                        i = R.id.regex_userlist_spinner;
                        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.regex_userlist_spinner);
                        if (spinner != null) {
                            return new BrickRegexBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, spinner);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
