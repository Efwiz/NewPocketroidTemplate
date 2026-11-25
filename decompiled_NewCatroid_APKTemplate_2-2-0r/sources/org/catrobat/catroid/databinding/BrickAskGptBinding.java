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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickAskGptBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickAskGptBinding implements ViewBinding {
    public final TextView brickAskGptEditPrompt;
    public final TextView brickAskGptEditSystem;
    public final BrickLayout brickAskGptLayout;
    public final Spinner brickAskGptSpinner;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickAskGptBinding(LinearLayout rootView, TextView brickAskGptEditPrompt, TextView brickAskGptEditSystem, BrickLayout brickAskGptLayout, Spinner brickAskGptSpinner, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickAskGptEditPrompt = brickAskGptEditPrompt;
        this.brickAskGptEditSystem = brickAskGptEditSystem;
        this.brickAskGptLayout = brickAskGptLayout;
        this.brickAskGptSpinner = brickAskGptSpinner;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickAskGptBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickAskGptBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_ask_gpt, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickAskGptBinding bind(View rootView) {
        int i = R.id.brick_ask_gpt_edit_prompt;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ask_gpt_edit_prompt);
        if (textView != null) {
            i = R.id.brick_ask_gpt_edit_system;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ask_gpt_edit_system);
            if (textView2 != null) {
                i = R.id.brick_ask_gpt_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_ask_gpt_layout);
                if (brickLayout != null) {
                    i = R.id.brick_ask_gpt_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_ask_gpt_spinner);
                    if (spinner != null) {
                        i = R.id.brick_checkbox;
                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                        if (checkBox != null) {
                            return new BrickAskGptBinding((LinearLayout) rootView, textView, textView2, brickLayout, spinner, checkBox);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
