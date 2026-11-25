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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickJavaLoadAndRunBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickJavaLoadAndRunBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickJavaLoadArgField;
    public final TextView brickJavaLoadClassField;
    public final BrickLayout brickJavaLoadLayout;
    public final TextView brickJavaLoadMethodField;
    public final TextView brickJavaLoadPathField;
    private final LinearLayout rootView;

    private BrickJavaLoadAndRunBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickJavaLoadArgField, TextView brickJavaLoadClassField, BrickLayout brickJavaLoadLayout, TextView brickJavaLoadMethodField, TextView brickJavaLoadPathField) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickJavaLoadArgField = brickJavaLoadArgField;
        this.brickJavaLoadClassField = brickJavaLoadClassField;
        this.brickJavaLoadLayout = brickJavaLoadLayout;
        this.brickJavaLoadMethodField = brickJavaLoadMethodField;
        this.brickJavaLoadPathField = brickJavaLoadPathField;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickJavaLoadAndRunBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickJavaLoadAndRunBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_java_load_and_run, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickJavaLoadAndRunBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_java_load_arg_field;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_java_load_arg_field);
            if (textView != null) {
                i = R.id.brick_java_load_class_field;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_java_load_class_field);
                if (textView2 != null) {
                    i = R.id.brick_java_load_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_java_load_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_java_load_method_field;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_java_load_method_field);
                        if (textView3 != null) {
                            i = R.id.brick_java_load_path_field;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_java_load_path_field);
                            if (textView4 != null) {
                                return new BrickJavaLoadAndRunBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
