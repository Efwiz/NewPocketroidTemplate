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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickDeleteLookBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickDeleteLookBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickDeleteLookLayout;
    public final TextView brickDeleteLookTextView;
    private final LinearLayout rootView;

    private BrickDeleteLookBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickDeleteLookLayout, TextView brickDeleteLookTextView) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickDeleteLookLayout = brickDeleteLookLayout;
        this.brickDeleteLookTextView = brickDeleteLookTextView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickDeleteLookBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickDeleteLookBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_delete_look, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickDeleteLookBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_delete_look_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_delete_look_layout);
            if (brickLayout != null) {
                i = R.id.brick_delete_look_text_view;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_delete_look_text_view);
                if (textView != null) {
                    return new BrickDeleteLookBinding((LinearLayout) rootView, checkBox, brickLayout, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
