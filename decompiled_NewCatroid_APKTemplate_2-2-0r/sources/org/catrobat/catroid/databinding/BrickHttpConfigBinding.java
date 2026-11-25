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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickHttpConfigBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickHttpConfigBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickHttpConfigIdField;
    public final TextView brickHttpConfigKeyField;
    public final LinearLayout brickHttpConfigKeyLayout;
    public final BrickLayout brickHttpConfigLayout;
    public final Spinner brickHttpConfigTypeSpinner;
    public final TextView brickHttpConfigValueField;
    public final LinearLayout brickHttpConfigValueLayout;
    private final LinearLayout rootView;

    private BrickHttpConfigBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickHttpConfigIdField, TextView brickHttpConfigKeyField, LinearLayout brickHttpConfigKeyLayout, BrickLayout brickHttpConfigLayout, Spinner brickHttpConfigTypeSpinner, TextView brickHttpConfigValueField, LinearLayout brickHttpConfigValueLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickHttpConfigIdField = brickHttpConfigIdField;
        this.brickHttpConfigKeyField = brickHttpConfigKeyField;
        this.brickHttpConfigKeyLayout = brickHttpConfigKeyLayout;
        this.brickHttpConfigLayout = brickHttpConfigLayout;
        this.brickHttpConfigTypeSpinner = brickHttpConfigTypeSpinner;
        this.brickHttpConfigValueField = brickHttpConfigValueField;
        this.brickHttpConfigValueLayout = brickHttpConfigValueLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickHttpConfigBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickHttpConfigBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_http_config, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickHttpConfigBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_http_config_id_field;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_config_id_field);
            if (textView != null) {
                i = R.id.brick_http_config_key_field;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_config_key_field);
                if (textView2 != null) {
                    i = R.id.brick_http_config_key_layout;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.brick_http_config_key_layout);
                    if (linearLayout != null) {
                        i = R.id.brick_http_config_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_http_config_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_http_config_type_spinner;
                            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_http_config_type_spinner);
                            if (spinner != null) {
                                i = R.id.brick_http_config_value_field;
                                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_config_value_field);
                                if (textView3 != null) {
                                    i = R.id.brick_http_config_value_layout;
                                    LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.brick_http_config_value_layout);
                                    if (linearLayout2 != null) {
                                        return new BrickHttpConfigBinding((LinearLayout) rootView, checkBox, textView, textView2, linearLayout, brickLayout, spinner, textView3, linearLayout2);
                                    }
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
