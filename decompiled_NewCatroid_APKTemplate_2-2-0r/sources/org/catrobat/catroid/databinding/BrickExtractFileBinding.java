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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickExtractFileBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickExtractFileBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickExtractEditApk;
    public final TextView brickExtractEditDest;
    public final TextView brickExtractEditInner;
    public final BrickLayout brickExtractFileLayout;
    private final LinearLayout rootView;

    private BrickExtractFileBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickExtractEditApk, TextView brickExtractEditDest, TextView brickExtractEditInner, BrickLayout brickExtractFileLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickExtractEditApk = brickExtractEditApk;
        this.brickExtractEditDest = brickExtractEditDest;
        this.brickExtractEditInner = brickExtractEditInner;
        this.brickExtractFileLayout = brickExtractFileLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickExtractFileBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickExtractFileBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_extract_file, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickExtractFileBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_extract_edit_apk;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_extract_edit_apk);
            if (textView != null) {
                i = R.id.brick_extract_edit_dest;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_extract_edit_dest);
                if (textView2 != null) {
                    i = R.id.brick_extract_edit_inner;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_extract_edit_inner);
                    if (textView3 != null) {
                        i = R.id.brick_extract_file_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_extract_file_layout);
                        if (brickLayout != null) {
                            return new BrickExtractFileBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
