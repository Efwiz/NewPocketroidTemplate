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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickWebRequestBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickWebRequestBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickWebRequestEditText;
    public final BrickLayout brickWebRequestLayout;
    private final LinearLayout rootView;
    public final Spinner webRequestSpinner;

    private BrickWebRequestBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickWebRequestEditText, BrickLayout brickWebRequestLayout, Spinner webRequestSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickWebRequestEditText = brickWebRequestEditText;
        this.brickWebRequestLayout = brickWebRequestLayout;
        this.webRequestSpinner = webRequestSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickWebRequestBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickWebRequestBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_web_request, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickWebRequestBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_web_request_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_web_request_edit_text);
            if (textView != null) {
                i = R.id.brick_web_request_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_web_request_layout);
                if (brickLayout != null) {
                    i = R.id.web_request_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.web_request_spinner);
                    if (spinner != null) {
                        return new BrickWebRequestBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
