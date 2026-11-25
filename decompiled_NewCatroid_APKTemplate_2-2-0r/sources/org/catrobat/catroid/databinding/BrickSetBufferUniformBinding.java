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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetBufferUniformBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetBufferUniformBinding implements ViewBinding {
    public final TextView brickBufUniformBufName;
    public final TextView brickBufUniformName;
    public final TextView brickBufUniformV1;
    public final TextView brickBufUniformV2;
    public final TextView brickBufUniformV3;
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSetBufferUniformLayout;
    private final LinearLayout rootView;

    private BrickSetBufferUniformBinding(LinearLayout rootView, TextView brickBufUniformBufName, TextView brickBufUniformName, TextView brickBufUniformV1, TextView brickBufUniformV2, TextView brickBufUniformV3, CheckBox brickCheckbox, BrickLayout brickSetBufferUniformLayout) {
        this.rootView = rootView;
        this.brickBufUniformBufName = brickBufUniformBufName;
        this.brickBufUniformName = brickBufUniformName;
        this.brickBufUniformV1 = brickBufUniformV1;
        this.brickBufUniformV2 = brickBufUniformV2;
        this.brickBufUniformV3 = brickBufUniformV3;
        this.brickCheckbox = brickCheckbox;
        this.brickSetBufferUniformLayout = brickSetBufferUniformLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetBufferUniformBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetBufferUniformBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_buffer_uniform, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetBufferUniformBinding bind(View rootView) {
        int i = R.id.brick_buf_uniform_buf_name;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buf_uniform_buf_name);
        if (textView != null) {
            i = R.id.brick_buf_uniform_name;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buf_uniform_name);
            if (textView2 != null) {
                i = R.id.brick_buf_uniform_v1;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buf_uniform_v1);
                if (textView3 != null) {
                    i = R.id.brick_buf_uniform_v2;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buf_uniform_v2);
                    if (textView4 != null) {
                        i = R.id.brick_buf_uniform_v3;
                        TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buf_uniform_v3);
                        if (textView5 != null) {
                            i = R.id.brick_checkbox;
                            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                            if (checkBox != null) {
                                i = R.id.brick_set_buffer_uniform_layout;
                                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_buffer_uniform_layout);
                                if (brickLayout != null) {
                                    return new BrickSetBufferUniformBinding((LinearLayout) rootView, textView, textView2, textView3, textView4, textView5, checkBox, brickLayout);
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
