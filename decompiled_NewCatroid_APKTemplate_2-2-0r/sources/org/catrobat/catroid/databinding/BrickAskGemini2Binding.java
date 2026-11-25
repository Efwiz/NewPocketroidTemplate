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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickAskGemini2Binding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickAskGemini2Binding implements ViewBinding {
    public final TextView brickAskGemini2EditModel;
    public final TextView brickAskGemini2EditUrl;
    public final BrickLayout brickAskGemini2Layout;
    public final Spinner brickAskGemini2Spinner;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickAskGemini2Binding(LinearLayout rootView, TextView brickAskGemini2EditModel, TextView brickAskGemini2EditUrl, BrickLayout brickAskGemini2Layout, Spinner brickAskGemini2Spinner, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickAskGemini2EditModel = brickAskGemini2EditModel;
        this.brickAskGemini2EditUrl = brickAskGemini2EditUrl;
        this.brickAskGemini2Layout = brickAskGemini2Layout;
        this.brickAskGemini2Spinner = brickAskGemini2Spinner;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickAskGemini2Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickAskGemini2Binding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_ask_gemini2, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickAskGemini2Binding bind(View rootView) {
        int i = R.id.brick_ask_gemini2_edit_model;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ask_gemini2_edit_model);
        if (textView != null) {
            i = R.id.brick_ask_gemini2_edit_url;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ask_gemini2_edit_url);
            if (textView2 != null) {
                i = R.id.brick_ask_gemini2_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_ask_gemini2_layout);
                if (brickLayout != null) {
                    i = R.id.brick_ask_gemini2_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_ask_gemini2_spinner);
                    if (spinner != null) {
                        i = R.id.brick_checkbox;
                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                        if (checkBox != null) {
                            return new BrickAskGemini2Binding((LinearLayout) rootView, textView, textView2, brickLayout, spinner, checkBox);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
