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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickThreedAlignNormalBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickThreedAlignNormalBinding implements ViewBinding {
    public final TextView brickAlignId;
    public final TextView brickAlignNx;
    public final TextView brickAlignNy;
    public final TextView brickAlignNz;
    public final CheckBox brickCheckbox;
    public final BrickLayout brickLayout;
    private final LinearLayout rootView;

    private BrickThreedAlignNormalBinding(LinearLayout rootView, TextView brickAlignId, TextView brickAlignNx, TextView brickAlignNy, TextView brickAlignNz, CheckBox brickCheckbox, BrickLayout brickLayout) {
        this.rootView = rootView;
        this.brickAlignId = brickAlignId;
        this.brickAlignNx = brickAlignNx;
        this.brickAlignNy = brickAlignNy;
        this.brickAlignNz = brickAlignNz;
        this.brickCheckbox = brickCheckbox;
        this.brickLayout = brickLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickThreedAlignNormalBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickThreedAlignNormalBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_threed_align_normal, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickThreedAlignNormalBinding bind(View rootView) {
        int i = R.id.brick_align_id;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_align_id);
        if (textView != null) {
            i = R.id.brick_align_nx;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_align_nx);
            if (textView2 != null) {
                i = R.id.brick_align_ny;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_align_ny);
                if (textView3 != null) {
                    i = R.id.brick_align_nz;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_align_nz);
                    if (textView4 != null) {
                        i = R.id.brick_checkbox;
                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                        if (checkBox != null) {
                            i = R.id.brick_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_layout);
                            if (brickLayout != null) {
                                return new BrickThreedAlignNormalBinding((LinearLayout) rootView, textView, textView2, textView3, textView4, checkBox, brickLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
