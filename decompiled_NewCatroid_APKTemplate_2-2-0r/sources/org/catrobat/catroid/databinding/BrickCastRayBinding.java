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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCastRayBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCastRayBinding implements ViewBinding {
    public final TextView brickCastRayDirX;
    public final TextView brickCastRayDirY;
    public final TextView brickCastRayDirZ;
    public final TextView brickCastRayFromX;
    public final TextView brickCastRayFromY;
    public final TextView brickCastRayFromZ;
    public final BrickLayout brickCastRayLayout;
    public final TextView brickCastRayName;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickCastRayBinding(LinearLayout rootView, TextView brickCastRayDirX, TextView brickCastRayDirY, TextView brickCastRayDirZ, TextView brickCastRayFromX, TextView brickCastRayFromY, TextView brickCastRayFromZ, BrickLayout brickCastRayLayout, TextView brickCastRayName, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickCastRayDirX = brickCastRayDirX;
        this.brickCastRayDirY = brickCastRayDirY;
        this.brickCastRayDirZ = brickCastRayDirZ;
        this.brickCastRayFromX = brickCastRayFromX;
        this.brickCastRayFromY = brickCastRayFromY;
        this.brickCastRayFromZ = brickCastRayFromZ;
        this.brickCastRayLayout = brickCastRayLayout;
        this.brickCastRayName = brickCastRayName;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCastRayBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCastRayBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_cast_ray, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCastRayBinding bind(View rootView) {
        int i = R.id.brick_cast_ray_dir_x;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_cast_ray_dir_x);
        if (textView != null) {
            i = R.id.brick_cast_ray_dir_y;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_cast_ray_dir_y);
            if (textView2 != null) {
                i = R.id.brick_cast_ray_dir_z;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_cast_ray_dir_z);
                if (textView3 != null) {
                    i = R.id.brick_cast_ray_from_x;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_cast_ray_from_x);
                    if (textView4 != null) {
                        i = R.id.brick_cast_ray_from_y;
                        TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_cast_ray_from_y);
                        if (textView5 != null) {
                            i = R.id.brick_cast_ray_from_z;
                            TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_cast_ray_from_z);
                            if (textView6 != null) {
                                i = R.id.brick_cast_ray_layout;
                                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_cast_ray_layout);
                                if (brickLayout != null) {
                                    i = R.id.brick_cast_ray_name;
                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_cast_ray_name);
                                    if (textView7 != null) {
                                        i = R.id.brick_checkbox;
                                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                                        if (checkBox != null) {
                                            return new BrickCastRayBinding((LinearLayout) rootView, textView, textView2, textView3, textView4, textView5, textView6, brickLayout, textView7, checkBox);
                                        }
                                    }
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
