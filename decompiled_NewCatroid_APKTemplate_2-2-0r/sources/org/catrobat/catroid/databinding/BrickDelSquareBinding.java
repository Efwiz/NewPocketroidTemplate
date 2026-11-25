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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickDelSquareBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickDelSquareBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickDelSquareEditName;
    public final BrickLayout brickDelSquareLayout;
    private final LinearLayout rootView;

    private BrickDelSquareBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickDelSquareEditName, BrickLayout brickDelSquareLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickDelSquareEditName = brickDelSquareEditName;
        this.brickDelSquareLayout = brickDelSquareLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickDelSquareBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickDelSquareBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_del_square, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickDelSquareBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_del_square_edit_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_del_square_edit_name);
            if (textView != null) {
                i = R.id.brick_del_square_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_del_square_layout);
                if (brickLayout != null) {
                    return new BrickDelSquareBinding((LinearLayout) rootView, checkBox, textView, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
