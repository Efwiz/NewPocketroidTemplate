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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickHttpCreateBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickHttpCreateBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickHttpCreateIdField;
    public final BrickLayout brickHttpCreateLayout;
    public final TextView brickHttpCreateMethodField;
    public final TextView brickHttpCreateUrlField;
    private final LinearLayout rootView;

    private BrickHttpCreateBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickHttpCreateIdField, BrickLayout brickHttpCreateLayout, TextView brickHttpCreateMethodField, TextView brickHttpCreateUrlField) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickHttpCreateIdField = brickHttpCreateIdField;
        this.brickHttpCreateLayout = brickHttpCreateLayout;
        this.brickHttpCreateMethodField = brickHttpCreateMethodField;
        this.brickHttpCreateUrlField = brickHttpCreateUrlField;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickHttpCreateBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickHttpCreateBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_http_create, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickHttpCreateBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_http_create_id_field;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_create_id_field);
            if (textView != null) {
                i = R.id.brick_http_create_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_http_create_layout);
                if (brickLayout != null) {
                    i = R.id.brick_http_create_method_field;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_create_method_field);
                    if (textView2 != null) {
                        i = R.id.brick_http_create_url_field;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_create_url_field);
                        if (textView3 != null) {
                            return new BrickHttpCreateBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
