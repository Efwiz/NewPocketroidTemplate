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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSet3dSoundPositionBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSet3dSoundPositionBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSet3dSoundName;
    public final BrickLayout brickSet3dSoundPositionLayout;
    public final TextView brickSet3dSoundX;
    public final TextView brickSet3dSoundY;
    public final TextView brickSet3dSoundZ;
    private final LinearLayout rootView;

    private BrickSet3dSoundPositionBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSet3dSoundName, BrickLayout brickSet3dSoundPositionLayout, TextView brickSet3dSoundX, TextView brickSet3dSoundY, TextView brickSet3dSoundZ) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSet3dSoundName = brickSet3dSoundName;
        this.brickSet3dSoundPositionLayout = brickSet3dSoundPositionLayout;
        this.brickSet3dSoundX = brickSet3dSoundX;
        this.brickSet3dSoundY = brickSet3dSoundY;
        this.brickSet3dSoundZ = brickSet3dSoundZ;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSet3dSoundPositionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSet3dSoundPositionBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_3d_sound_position, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSet3dSoundPositionBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_3d_sound_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_sound_name);
            if (textView != null) {
                i = R.id.brick_set_3d_sound_position_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_sound_position_layout);
                if (brickLayout != null) {
                    i = R.id.brick_set_3d_sound_x;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_sound_x);
                    if (textView2 != null) {
                        i = R.id.brick_set_3d_sound_y;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_sound_y);
                        if (textView3 != null) {
                            i = R.id.brick_set_3d_sound_z;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_sound_z);
                            if (textView4 != null) {
                                return new BrickSet3dSoundPositionBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2, textView3, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
