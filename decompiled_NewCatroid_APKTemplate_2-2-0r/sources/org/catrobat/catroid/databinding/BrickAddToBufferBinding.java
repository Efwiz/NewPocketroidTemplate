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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickAddToBufferBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickAddToBufferBinding implements ViewBinding {
    public final BrickLayout brickAddToBufferLayout;
    public final TextView brickBufferName;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickAddToBufferBinding(LinearLayout rootView, BrickLayout brickAddToBufferLayout, TextView brickBufferName, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickAddToBufferLayout = brickAddToBufferLayout;
        this.brickBufferName = brickBufferName;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickAddToBufferBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickAddToBufferBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_add_to_buffer, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickAddToBufferBinding bind(View rootView) {
        int i = R.id.brick_add_to_buffer_layout;
        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_add_to_buffer_layout);
        if (brickLayout != null) {
            i = R.id.brick_buffer_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_name);
            if (textView != null) {
                i = R.id.brick_checkbox;
                CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                if (checkBox != null) {
                    return new BrickAddToBufferBinding((LinearLayout) rootView, brickLayout, textView, checkBox);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
