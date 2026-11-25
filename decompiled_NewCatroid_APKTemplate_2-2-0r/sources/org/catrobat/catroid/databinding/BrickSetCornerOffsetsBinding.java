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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetCornerOffsetsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetCornerOffsetsBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetCornerOffsetsBlx;
    public final TextView brickSetCornerOffsetsBly;
    public final TextView brickSetCornerOffsetsBrx;
    public final TextView brickSetCornerOffsetsBry;
    public final BrickLayout brickSetCornerOffsetsLayout;
    public final TextView brickSetCornerOffsetsTlx;
    public final TextView brickSetCornerOffsetsTly;
    public final TextView brickSetCornerOffsetsTrx;
    public final TextView brickSetCornerOffsetsTry;
    private final LinearLayout rootView;

    private BrickSetCornerOffsetsBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetCornerOffsetsBlx, TextView brickSetCornerOffsetsBly, TextView brickSetCornerOffsetsBrx, TextView brickSetCornerOffsetsBry, BrickLayout brickSetCornerOffsetsLayout, TextView brickSetCornerOffsetsTlx, TextView brickSetCornerOffsetsTly, TextView brickSetCornerOffsetsTrx, TextView brickSetCornerOffsetsTry) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetCornerOffsetsBlx = brickSetCornerOffsetsBlx;
        this.brickSetCornerOffsetsBly = brickSetCornerOffsetsBly;
        this.brickSetCornerOffsetsBrx = brickSetCornerOffsetsBrx;
        this.brickSetCornerOffsetsBry = brickSetCornerOffsetsBry;
        this.brickSetCornerOffsetsLayout = brickSetCornerOffsetsLayout;
        this.brickSetCornerOffsetsTlx = brickSetCornerOffsetsTlx;
        this.brickSetCornerOffsetsTly = brickSetCornerOffsetsTly;
        this.brickSetCornerOffsetsTrx = brickSetCornerOffsetsTrx;
        this.brickSetCornerOffsetsTry = brickSetCornerOffsetsTry;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetCornerOffsetsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetCornerOffsetsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_corner_offsets, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetCornerOffsetsBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_corner_offsets_blx;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_corner_offsets_blx);
            if (textView != null) {
                i = R.id.brick_set_corner_offsets_bly;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_corner_offsets_bly);
                if (textView2 != null) {
                    i = R.id.brick_set_corner_offsets_brx;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_corner_offsets_brx);
                    if (textView3 != null) {
                        i = R.id.brick_set_corner_offsets_bry;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_corner_offsets_bry);
                        if (textView4 != null) {
                            i = R.id.brick_set_corner_offsets_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_corner_offsets_layout);
                            if (brickLayout != null) {
                                i = R.id.brick_set_corner_offsets_tlx;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_corner_offsets_tlx);
                                if (textView5 != null) {
                                    i = R.id.brick_set_corner_offsets_tly;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_corner_offsets_tly);
                                    if (textView6 != null) {
                                        i = R.id.brick_set_corner_offsets_trx;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_corner_offsets_trx);
                                        if (textView7 != null) {
                                            i = R.id.brick_set_corner_offsets_try;
                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_corner_offsets_try);
                                            if (textView8 != null) {
                                                return new BrickSetCornerOffsetsBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout, textView5, textView6, textView7, textView8);
                                            }
                                        }
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
