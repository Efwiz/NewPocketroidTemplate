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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickGenerateKeyBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickGenerateKeyBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickGenKeyEditAlias;
    public final TextView brickGenKeyEditFilename;
    public final TextView brickGenKeyEditName;
    public final TextView brickGenKeyEditPass;
    public final BrickLayout brickGenerateKeyLayout;
    private final LinearLayout rootView;

    private BrickGenerateKeyBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickGenKeyEditAlias, TextView brickGenKeyEditFilename, TextView brickGenKeyEditName, TextView brickGenKeyEditPass, BrickLayout brickGenerateKeyLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickGenKeyEditAlias = brickGenKeyEditAlias;
        this.brickGenKeyEditFilename = brickGenKeyEditFilename;
        this.brickGenKeyEditName = brickGenKeyEditName;
        this.brickGenKeyEditPass = brickGenKeyEditPass;
        this.brickGenerateKeyLayout = brickGenerateKeyLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickGenerateKeyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickGenerateKeyBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_generate_key, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickGenerateKeyBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_gen_key_edit_alias;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_gen_key_edit_alias);
            if (textView != null) {
                i = R.id.brick_gen_key_edit_filename;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_gen_key_edit_filename);
                if (textView2 != null) {
                    i = R.id.brick_gen_key_edit_name;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_gen_key_edit_name);
                    if (textView3 != null) {
                        i = R.id.brick_gen_key_edit_pass;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_gen_key_edit_pass);
                        if (textView4 != null) {
                            i = R.id.brick_generate_key_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_generate_key_layout);
                            if (brickLayout != null) {
                                return new BrickGenerateKeyBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
