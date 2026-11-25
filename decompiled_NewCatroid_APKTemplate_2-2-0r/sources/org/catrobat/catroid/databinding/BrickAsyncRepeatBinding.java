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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickAsyncRepeatBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickAsyncRepeatBinding implements ViewBinding {
    public final TextView brickAsyncRepeatEditText;
    public final BrickLayout brickAsyncRepeatLayout;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickAsyncRepeatBinding(LinearLayout rootView, TextView brickAsyncRepeatEditText, BrickLayout brickAsyncRepeatLayout, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickAsyncRepeatEditText = brickAsyncRepeatEditText;
        this.brickAsyncRepeatLayout = brickAsyncRepeatLayout;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickAsyncRepeatBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickAsyncRepeatBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_async_repeat, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickAsyncRepeatBinding bind(View rootView) {
        int i = R.id.brick_async_repeat_edit_text;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_async_repeat_edit_text);
        if (textView != null) {
            i = R.id.brick_async_repeat_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_async_repeat_layout);
            if (brickLayout != null) {
                i = R.id.brick_checkbox;
                CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                if (checkBox != null) {
                    return new BrickAsyncRepeatBinding((LinearLayout) rootView, textView, brickLayout, checkBox);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
