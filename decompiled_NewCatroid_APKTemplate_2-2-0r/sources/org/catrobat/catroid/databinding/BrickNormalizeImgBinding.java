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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickNormalizeImgBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickNormalizeImgBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickNormalizeImgB;
    public final TextView brickNormalizeImgFile;
    public final TextView brickNormalizeImgG;
    public final TextView brickNormalizeImgR;
    public final BrickLayout brickShowToastLayout;
    private final LinearLayout rootView;

    private BrickNormalizeImgBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickNormalizeImgB, TextView brickNormalizeImgFile, TextView brickNormalizeImgG, TextView brickNormalizeImgR, BrickLayout brickShowToastLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickNormalizeImgB = brickNormalizeImgB;
        this.brickNormalizeImgFile = brickNormalizeImgFile;
        this.brickNormalizeImgG = brickNormalizeImgG;
        this.brickNormalizeImgR = brickNormalizeImgR;
        this.brickShowToastLayout = brickShowToastLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickNormalizeImgBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickNormalizeImgBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_normalize_img, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickNormalizeImgBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_normalize_img_b;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_normalize_img_b);
            if (textView != null) {
                i = R.id.brick_normalize_img_file;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_normalize_img_file);
                if (textView2 != null) {
                    i = R.id.brick_normalize_img_g;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_normalize_img_g);
                    if (textView3 != null) {
                        i = R.id.brick_normalize_img_r;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_normalize_img_r);
                        if (textView4 != null) {
                            i = R.id.brick_show_toast_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_layout);
                            if (brickLayout != null) {
                                return new BrickNormalizeImgBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
