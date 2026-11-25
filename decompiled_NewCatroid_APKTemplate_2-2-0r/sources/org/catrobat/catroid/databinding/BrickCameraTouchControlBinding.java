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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCameraTouchControlBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCameraTouchControlBinding implements ViewBinding {
    public final BrickLayout brickCameraTouchLayout;
    public final CheckBox brickCheckbox;
    public final TextView camAreaH;
    public final TextView camAreaW;
    public final TextView camAreaX;
    public final TextView camAreaY;
    public final TextView camEnabled;
    public final TextView camSens;
    private final LinearLayout rootView;

    private BrickCameraTouchControlBinding(LinearLayout rootView, BrickLayout brickCameraTouchLayout, CheckBox brickCheckbox, TextView camAreaH, TextView camAreaW, TextView camAreaX, TextView camAreaY, TextView camEnabled, TextView camSens) {
        this.rootView = rootView;
        this.brickCameraTouchLayout = brickCameraTouchLayout;
        this.brickCheckbox = brickCheckbox;
        this.camAreaH = camAreaH;
        this.camAreaW = camAreaW;
        this.camAreaX = camAreaX;
        this.camAreaY = camAreaY;
        this.camEnabled = camEnabled;
        this.camSens = camSens;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCameraTouchControlBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCameraTouchControlBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_camera_touch_control, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCameraTouchControlBinding bind(View rootView) {
        int i = R.id.brick_camera_touch_layout;
        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_camera_touch_layout);
        if (brickLayout != null) {
            i = R.id.brick_checkbox;
            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
            if (checkBox != null) {
                i = R.id.cam_area_h;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.cam_area_h);
                if (textView != null) {
                    i = R.id.cam_area_w;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.cam_area_w);
                    if (textView2 != null) {
                        i = R.id.cam_area_x;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.cam_area_x);
                        if (textView3 != null) {
                            i = R.id.cam_area_y;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.cam_area_y);
                            if (textView4 != null) {
                                i = R.id.cam_enabled;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.cam_enabled);
                                if (textView5 != null) {
                                    i = R.id.cam_sens;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.cam_sens);
                                    if (textView6 != null) {
                                        return new BrickCameraTouchControlBinding((LinearLayout) rootView, brickLayout, checkBox, textView, textView2, textView3, textView4, textView5, textView6);
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
