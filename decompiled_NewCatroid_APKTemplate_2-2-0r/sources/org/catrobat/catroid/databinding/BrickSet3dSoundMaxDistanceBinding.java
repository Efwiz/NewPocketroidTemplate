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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSet3dSoundMaxDistanceBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSet3dSoundMaxDistanceBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSet3dSoundMaxDistanceInstance;
    public final BrickLayout brickSet3dSoundMaxDistanceLayout;
    public final TextView brickSet3dSoundMaxDistanceVal;
    private final LinearLayout rootView;

    private BrickSet3dSoundMaxDistanceBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSet3dSoundMaxDistanceInstance, BrickLayout brickSet3dSoundMaxDistanceLayout, TextView brickSet3dSoundMaxDistanceVal) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSet3dSoundMaxDistanceInstance = brickSet3dSoundMaxDistanceInstance;
        this.brickSet3dSoundMaxDistanceLayout = brickSet3dSoundMaxDistanceLayout;
        this.brickSet3dSoundMaxDistanceVal = brickSet3dSoundMaxDistanceVal;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSet3dSoundMaxDistanceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSet3dSoundMaxDistanceBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_3d_sound_max_distance, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSet3dSoundMaxDistanceBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_3d_sound_max_distance_instance;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_sound_max_distance_instance);
            if (textView != null) {
                i = R.id.brick_set_3d_sound_max_distance_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_sound_max_distance_layout);
                if (brickLayout != null) {
                    i = R.id.brick_set_3d_sound_max_distance_val;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_sound_max_distance_val);
                    if (textView2 != null) {
                        return new BrickSet3dSoundMaxDistanceBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
