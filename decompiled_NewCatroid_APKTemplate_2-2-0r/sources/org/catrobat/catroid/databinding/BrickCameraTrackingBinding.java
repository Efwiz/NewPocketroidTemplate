package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCameraTrackingBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCameraTrackingBinding implements ViewBinding {
    public final TextView brickCameraTrackId;
    public final BrickLayout brickCameraTrackLayout;
    public final LinearLayout brickCameraTrackPosLayout;
    public final TextView brickCameraTrackPx;
    public final TextView brickCameraTrackPy;
    public final TextView brickCameraTrackPz;
    public final LinearLayout brickCameraTrackRotLayout;
    public final TextView brickCameraTrackRp;
    public final TextView brickCameraTrackRr;
    public final TextView brickCameraTrackRy;
    public final Spinner brickCameraTrackSpinner;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickCameraTrackingBinding(LinearLayout rootView, TextView brickCameraTrackId, BrickLayout brickCameraTrackLayout, LinearLayout brickCameraTrackPosLayout, TextView brickCameraTrackPx, TextView brickCameraTrackPy, TextView brickCameraTrackPz, LinearLayout brickCameraTrackRotLayout, TextView brickCameraTrackRp, TextView brickCameraTrackRr, TextView brickCameraTrackRy, Spinner brickCameraTrackSpinner, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickCameraTrackId = brickCameraTrackId;
        this.brickCameraTrackLayout = brickCameraTrackLayout;
        this.brickCameraTrackPosLayout = brickCameraTrackPosLayout;
        this.brickCameraTrackPx = brickCameraTrackPx;
        this.brickCameraTrackPy = brickCameraTrackPy;
        this.brickCameraTrackPz = brickCameraTrackPz;
        this.brickCameraTrackRotLayout = brickCameraTrackRotLayout;
        this.brickCameraTrackRp = brickCameraTrackRp;
        this.brickCameraTrackRr = brickCameraTrackRr;
        this.brickCameraTrackRy = brickCameraTrackRy;
        this.brickCameraTrackSpinner = brickCameraTrackSpinner;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCameraTrackingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCameraTrackingBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_camera_tracking, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCameraTrackingBinding bind(View rootView) {
        int i = R.id.brick_camera_track_id;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_camera_track_id);
        if (textView != null) {
            i = R.id.brick_camera_track_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_camera_track_layout);
            if (brickLayout != null) {
                i = R.id.brick_camera_track_pos_layout;
                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.brick_camera_track_pos_layout);
                if (linearLayout != null) {
                    i = R.id.brick_camera_track_px;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_camera_track_px);
                    if (textView2 != null) {
                        i = R.id.brick_camera_track_py;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_camera_track_py);
                        if (textView3 != null) {
                            i = R.id.brick_camera_track_pz;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_camera_track_pz);
                            if (textView4 != null) {
                                i = R.id.brick_camera_track_rot_layout;
                                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.brick_camera_track_rot_layout);
                                if (linearLayout2 != null) {
                                    i = R.id.brick_camera_track_rp;
                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_camera_track_rp);
                                    if (textView5 != null) {
                                        i = R.id.brick_camera_track_rr;
                                        TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_camera_track_rr);
                                        if (textView6 != null) {
                                            i = R.id.brick_camera_track_ry;
                                            TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_camera_track_ry);
                                            if (textView7 != null) {
                                                i = R.id.brick_camera_track_spinner;
                                                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_camera_track_spinner);
                                                if (spinner != null) {
                                                    i = R.id.brick_checkbox;
                                                    CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                                                    if (checkBox != null) {
                                                        return new BrickCameraTrackingBinding((LinearLayout) rootView, textView, brickLayout, linearLayout, textView2, textView3, textView4, linearLayout2, textView5, textView6, textView7, spinner, checkBox);
                                                    }
                                                }
                                            }
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
