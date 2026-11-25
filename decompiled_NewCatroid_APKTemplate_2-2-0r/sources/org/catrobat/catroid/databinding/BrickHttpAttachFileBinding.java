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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickHttpAttachFileBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickHttpAttachFileBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickHttpAttachFileIdField;
    public final BrickLayout brickHttpAttachFileLayout;
    public final TextView brickHttpAttachFileMimeField;
    public final TextView brickHttpAttachFileNameField;
    public final TextView brickHttpAttachFileParameterField;
    private final LinearLayout rootView;

    private BrickHttpAttachFileBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickHttpAttachFileIdField, BrickLayout brickHttpAttachFileLayout, TextView brickHttpAttachFileMimeField, TextView brickHttpAttachFileNameField, TextView brickHttpAttachFileParameterField) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickHttpAttachFileIdField = brickHttpAttachFileIdField;
        this.brickHttpAttachFileLayout = brickHttpAttachFileLayout;
        this.brickHttpAttachFileMimeField = brickHttpAttachFileMimeField;
        this.brickHttpAttachFileNameField = brickHttpAttachFileNameField;
        this.brickHttpAttachFileParameterField = brickHttpAttachFileParameterField;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickHttpAttachFileBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickHttpAttachFileBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_http_attach_file, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickHttpAttachFileBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_http_attach_file_id_field;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_attach_file_id_field);
            if (textView != null) {
                i = R.id.brick_http_attach_file_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_http_attach_file_layout);
                if (brickLayout != null) {
                    i = R.id.brick_http_attach_file_mime_field;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_attach_file_mime_field);
                    if (textView2 != null) {
                        i = R.id.brick_http_attach_file_name_field;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_attach_file_name_field);
                        if (textView3 != null) {
                            i = R.id.brick_http_attach_file_parameter_field;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_attach_file_parameter_field);
                            if (textView4 != null) {
                                return new BrickHttpAttachFileBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2, textView3, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
