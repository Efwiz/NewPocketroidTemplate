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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickDeleteFromApkBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickDeleteFromApkBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickDelApkEditPath;
    public final TextView brickDelApkEditPattern;
    public final BrickLayout brickDeleteFromApkLayout;
    private final LinearLayout rootView;

    private BrickDeleteFromApkBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickDelApkEditPath, TextView brickDelApkEditPattern, BrickLayout brickDeleteFromApkLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickDelApkEditPath = brickDelApkEditPath;
        this.brickDelApkEditPattern = brickDelApkEditPattern;
        this.brickDeleteFromApkLayout = brickDeleteFromApkLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickDeleteFromApkBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickDeleteFromApkBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_delete_from_apk, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickDeleteFromApkBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_del_apk_edit_path;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_del_apk_edit_path);
            if (textView != null) {
                i = R.id.brick_del_apk_edit_pattern;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_del_apk_edit_pattern);
                if (textView2 != null) {
                    i = R.id.brick_delete_from_apk_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_delete_from_apk_layout);
                    if (brickLayout != null) {
                        return new BrickDeleteFromApkBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
