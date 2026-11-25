package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickWhenFingerMovedOverSpriteBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickWhenFingerMovedOverSpriteBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickWhenFingerMovedOverSpriteLayout;
    private final LinearLayout rootView;

    private BrickWhenFingerMovedOverSpriteBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickWhenFingerMovedOverSpriteLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickWhenFingerMovedOverSpriteLayout = brickWhenFingerMovedOverSpriteLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickWhenFingerMovedOverSpriteBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickWhenFingerMovedOverSpriteBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_when_finger_moved_over_sprite, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickWhenFingerMovedOverSpriteBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_when_finger_moved_over_sprite_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_when_finger_moved_over_sprite_layout);
            if (brickLayout != null) {
                return new BrickWhenFingerMovedOverSpriteBinding((LinearLayout) rootView, checkBox, brickLayout);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
