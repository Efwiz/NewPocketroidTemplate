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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetCameraFocusBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetCameraFocusBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetCameraFocusHorizontalInput;
    public final TextView brickSetCameraFocusHorizontalLabel;
    public final TextView brickSetCameraFocusHorizontalPercent;
    public final BrickLayout brickSetCameraFocusLayout;
    public final TextView brickSetCameraFocusPointLabel;
    public final TextView brickSetCameraFocusVerticalInput;
    public final TextView brickSetCameraFocusVerticalLabel;
    public final TextView brickSetCameraFocusVerticalPercent;
    private final LinearLayout rootView;

    private BrickSetCameraFocusBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetCameraFocusHorizontalInput, TextView brickSetCameraFocusHorizontalLabel, TextView brickSetCameraFocusHorizontalPercent, BrickLayout brickSetCameraFocusLayout, TextView brickSetCameraFocusPointLabel, TextView brickSetCameraFocusVerticalInput, TextView brickSetCameraFocusVerticalLabel, TextView brickSetCameraFocusVerticalPercent) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetCameraFocusHorizontalInput = brickSetCameraFocusHorizontalInput;
        this.brickSetCameraFocusHorizontalLabel = brickSetCameraFocusHorizontalLabel;
        this.brickSetCameraFocusHorizontalPercent = brickSetCameraFocusHorizontalPercent;
        this.brickSetCameraFocusLayout = brickSetCameraFocusLayout;
        this.brickSetCameraFocusPointLabel = brickSetCameraFocusPointLabel;
        this.brickSetCameraFocusVerticalInput = brickSetCameraFocusVerticalInput;
        this.brickSetCameraFocusVerticalLabel = brickSetCameraFocusVerticalLabel;
        this.brickSetCameraFocusVerticalPercent = brickSetCameraFocusVerticalPercent;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetCameraFocusBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetCameraFocusBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_camera_focus, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetCameraFocusBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_camera_focus_horizontal_input;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_focus_horizontal_input);
            if (textView != null) {
                i = R.id.brick_set_camera_focus_horizontal_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_focus_horizontal_label);
                if (textView2 != null) {
                    i = R.id.brick_set_camera_focus_horizontal_percent;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_focus_horizontal_percent);
                    if (textView3 != null) {
                        i = R.id.brick_set_camera_focus_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_focus_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_set_camera_focus_point_label;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_focus_point_label);
                            if (textView4 != null) {
                                i = R.id.brick_set_camera_focus_vertical_input;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_focus_vertical_input);
                                if (textView5 != null) {
                                    i = R.id.brick_set_camera_focus_vertical_label;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_focus_vertical_label);
                                    if (textView6 != null) {
                                        i = R.id.brick_set_camera_focus_vertical_percent;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_focus_vertical_percent);
                                        if (textView7 != null) {
                                            return new BrickSetCameraFocusBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, textView4, textView5, textView6, textView7);
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
