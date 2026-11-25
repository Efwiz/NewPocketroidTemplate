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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickResizeImgBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickResizeImgBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickResizeImgFile;
    public final TextView brickResizeImgX;
    public final TextView brickResizeImgY;
    public final BrickLayout brickShowToastLayout;
    private final LinearLayout rootView;

    private BrickResizeImgBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickResizeImgFile, TextView brickResizeImgX, TextView brickResizeImgY, BrickLayout brickShowToastLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickResizeImgFile = brickResizeImgFile;
        this.brickResizeImgX = brickResizeImgX;
        this.brickResizeImgY = brickResizeImgY;
        this.brickShowToastLayout = brickShowToastLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickResizeImgBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickResizeImgBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_resize_img, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickResizeImgBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_resize_img_file;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_resize_img_file);
            if (textView != null) {
                i = R.id.brick_resize_img_x;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_resize_img_x);
                if (textView2 != null) {
                    i = R.id.brick_resize_img_y;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_resize_img_y);
                    if (textView3 != null) {
                        i = R.id.brick_show_toast_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_layout);
                        if (brickLayout != null) {
                            return new BrickResizeImgBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
