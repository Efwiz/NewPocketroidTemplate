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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickBufferModeBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickBufferModeBinding implements ViewBinding {
    public final TextView brickBuffer2d;
    public final TextView brickBuffer3d;
    public final BrickLayout brickBufferModeLayout;
    public final TextView brickBufferName;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickBufferModeBinding(LinearLayout rootView, TextView brickBuffer2d, TextView brickBuffer3d, BrickLayout brickBufferModeLayout, TextView brickBufferName, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickBuffer2d = brickBuffer2d;
        this.brickBuffer3d = brickBuffer3d;
        this.brickBufferModeLayout = brickBufferModeLayout;
        this.brickBufferName = brickBufferName;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickBufferModeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickBufferModeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_buffer_mode, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickBufferModeBinding bind(View rootView) {
        int i = R.id.brick_buffer_2d;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_2d);
        if (textView != null) {
            i = R.id.brick_buffer_3d;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_3d);
            if (textView2 != null) {
                i = R.id.brick_buffer_mode_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_mode_layout);
                if (brickLayout != null) {
                    i = R.id.brick_buffer_name;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_name);
                    if (textView3 != null) {
                        i = R.id.brick_checkbox;
                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                        if (checkBox != null) {
                            return new BrickBufferModeBinding((LinearLayout) rootView, textView, textView2, brickLayout, textView3, checkBox);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
