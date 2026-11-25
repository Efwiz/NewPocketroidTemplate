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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickAddFileApkBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickAddFileApkBinding implements ViewBinding {
    public final TextView brickAddFileApkEditApk;
    public final TextView brickAddFileApkEditDest;
    public final TextView brickAddFileApkEditSrc;
    public final BrickLayout brickAddFileApkLayout;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickAddFileApkBinding(LinearLayout rootView, TextView brickAddFileApkEditApk, TextView brickAddFileApkEditDest, TextView brickAddFileApkEditSrc, BrickLayout brickAddFileApkLayout, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickAddFileApkEditApk = brickAddFileApkEditApk;
        this.brickAddFileApkEditDest = brickAddFileApkEditDest;
        this.brickAddFileApkEditSrc = brickAddFileApkEditSrc;
        this.brickAddFileApkLayout = brickAddFileApkLayout;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickAddFileApkBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickAddFileApkBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_add_file_apk, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickAddFileApkBinding bind(View rootView) {
        int i = R.id.brick_add_file_apk_edit_apk;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_add_file_apk_edit_apk);
        if (textView != null) {
            i = R.id.brick_add_file_apk_edit_dest;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_add_file_apk_edit_dest);
            if (textView2 != null) {
                i = R.id.brick_add_file_apk_edit_src;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_add_file_apk_edit_src);
                if (textView3 != null) {
                    i = R.id.brick_add_file_apk_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_add_file_apk_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_checkbox;
                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                        if (checkBox != null) {
                            return new BrickAddFileApkBinding((LinearLayout) rootView, textView, textView2, textView3, brickLayout, checkBox);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
