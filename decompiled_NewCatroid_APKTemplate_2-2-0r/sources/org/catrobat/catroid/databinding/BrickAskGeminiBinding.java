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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickAskGeminiBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickAskGeminiBinding implements ViewBinding {
    public final TextView brickAskGeminiEditUrl;
    public final BrickLayout brickAskGeminiLayout;
    public final Spinner brickAskGeminiSpinner;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickAskGeminiBinding(LinearLayout rootView, TextView brickAskGeminiEditUrl, BrickLayout brickAskGeminiLayout, Spinner brickAskGeminiSpinner, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickAskGeminiEditUrl = brickAskGeminiEditUrl;
        this.brickAskGeminiLayout = brickAskGeminiLayout;
        this.brickAskGeminiSpinner = brickAskGeminiSpinner;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickAskGeminiBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickAskGeminiBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_ask_gemini, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickAskGeminiBinding bind(View rootView) {
        int i = R.id.brick_ask_gemini_edit_url;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ask_gemini_edit_url);
        if (textView != null) {
            i = R.id.brick_ask_gemini_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_ask_gemini_layout);
            if (brickLayout != null) {
                i = R.id.brick_ask_gemini_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_ask_gemini_spinner);
                if (spinner != null) {
                    i = R.id.brick_checkbox;
                    CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                    if (checkBox != null) {
                        return new BrickAskGeminiBinding((LinearLayout) rootView, textView, brickLayout, spinner, checkBox);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
