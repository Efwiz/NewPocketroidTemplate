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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSignApkBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSignApkBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSignApkEditAlias;
    public final TextView brickSignApkEditIn;
    public final TextView brickSignApkEditKey;
    public final TextView brickSignApkEditOut;
    public final TextView brickSignApkEditPass;
    public final BrickLayout brickSignApkLayout;
    private final LinearLayout rootView;

    private BrickSignApkBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSignApkEditAlias, TextView brickSignApkEditIn, TextView brickSignApkEditKey, TextView brickSignApkEditOut, TextView brickSignApkEditPass, BrickLayout brickSignApkLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSignApkEditAlias = brickSignApkEditAlias;
        this.brickSignApkEditIn = brickSignApkEditIn;
        this.brickSignApkEditKey = brickSignApkEditKey;
        this.brickSignApkEditOut = brickSignApkEditOut;
        this.brickSignApkEditPass = brickSignApkEditPass;
        this.brickSignApkLayout = brickSignApkLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSignApkBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSignApkBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_sign_apk, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSignApkBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_sign_apk_edit_alias;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_sign_apk_edit_alias);
            if (textView != null) {
                i = R.id.brick_sign_apk_edit_in;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_sign_apk_edit_in);
                if (textView2 != null) {
                    i = R.id.brick_sign_apk_edit_key;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_sign_apk_edit_key);
                    if (textView3 != null) {
                        i = R.id.brick_sign_apk_edit_out;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_sign_apk_edit_out);
                        if (textView4 != null) {
                            i = R.id.brick_sign_apk_edit_pass;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_sign_apk_edit_pass);
                            if (textView5 != null) {
                                i = R.id.brick_sign_apk_layout;
                                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_sign_apk_layout);
                                if (brickLayout != null) {
                                    return new BrickSignApkBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, textView5, brickLayout);
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
