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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickHttpBodyBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickHttpBodyBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickHttpBodyContentTypeField;
    public final TextView brickHttpBodyIdField;
    public final BrickLayout brickHttpBodyLayout;
    public final TextView brickHttpBodyTextField;
    private final LinearLayout rootView;

    private BrickHttpBodyBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickHttpBodyContentTypeField, TextView brickHttpBodyIdField, BrickLayout brickHttpBodyLayout, TextView brickHttpBodyTextField) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickHttpBodyContentTypeField = brickHttpBodyContentTypeField;
        this.brickHttpBodyIdField = brickHttpBodyIdField;
        this.brickHttpBodyLayout = brickHttpBodyLayout;
        this.brickHttpBodyTextField = brickHttpBodyTextField;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickHttpBodyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickHttpBodyBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_http_body, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickHttpBodyBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_http_body_content_type_field;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_body_content_type_field);
            if (textView != null) {
                i = R.id.brick_http_body_id_field;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_body_id_field);
                if (textView2 != null) {
                    i = R.id.brick_http_body_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_http_body_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_http_body_text_field;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_body_text_field);
                        if (textView3 != null) {
                            return new BrickHttpBodyBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
