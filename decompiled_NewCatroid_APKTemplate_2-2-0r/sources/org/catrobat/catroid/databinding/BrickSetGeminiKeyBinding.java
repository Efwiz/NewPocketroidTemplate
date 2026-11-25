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
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetGeminiKeyBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetGeminiKeyBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetGeminiKeyEditText;
    public final TextView brickSetGeminiKeyLabel;
    public final BrickLayout brickSetGeminiKeyLayout;
    private final LinearLayout rootView;

    private BrickSetGeminiKeyBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetGeminiKeyEditText, TextView brickSetGeminiKeyLabel, BrickLayout brickSetGeminiKeyLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetGeminiKeyEditText = brickSetGeminiKeyEditText;
        this.brickSetGeminiKeyLabel = brickSetGeminiKeyLabel;
        this.brickSetGeminiKeyLayout = brickSetGeminiKeyLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetGeminiKeyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetGeminiKeyBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_gemini_key, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetGeminiKeyBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_gemini_key_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_gemini_key_edit_text);
            if (textView != null) {
                i = R.id.brick_set_gemini_key_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_gemini_key_label);
                if (textView2 != null) {
                    i = R.id.brick_set_gemini_key_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_gemini_key_layout);
                    if (brickLayout != null) {
                        return new BrickSetGeminiKeyBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
