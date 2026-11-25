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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCreateBufferBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCreateBufferBinding implements ViewBinding {
    public final TextView brickBufferHeight;
    public final TextView brickBufferName;
    public final TextView brickBufferWidth;
    public final CheckBox brickCheckbox;
    public final BrickLayout brickCreateBufferLayout;
    private final LinearLayout rootView;

    private BrickCreateBufferBinding(LinearLayout rootView, TextView brickBufferHeight, TextView brickBufferName, TextView brickBufferWidth, CheckBox brickCheckbox, BrickLayout brickCreateBufferLayout) {
        this.rootView = rootView;
        this.brickBufferHeight = brickBufferHeight;
        this.brickBufferName = brickBufferName;
        this.brickBufferWidth = brickBufferWidth;
        this.brickCheckbox = brickCheckbox;
        this.brickCreateBufferLayout = brickCreateBufferLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCreateBufferBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCreateBufferBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_create_buffer, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCreateBufferBinding bind(View rootView) {
        int i = R.id.brick_buffer_height;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_height);
        if (textView != null) {
            i = R.id.brick_buffer_name;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_name);
            if (textView2 != null) {
                i = R.id.brick_buffer_width;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_width);
                if (textView3 != null) {
                    i = R.id.brick_checkbox;
                    CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                    if (checkBox != null) {
                        i = R.id.brick_create_buffer_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_create_buffer_layout);
                        if (brickLayout != null) {
                            return new BrickCreateBufferBinding((LinearLayout) rootView, textView, textView2, textView3, checkBox, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
