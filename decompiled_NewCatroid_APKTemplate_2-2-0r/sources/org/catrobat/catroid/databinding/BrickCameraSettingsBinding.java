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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCameraSettingsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCameraSettingsBinding implements ViewBinding {
    public final BrickLayout brickCameraSettingsLayout;
    public final CheckBox brickCheckbox;
    public final TextView cameraFovEdit;
    public final TextView cameraShakeDurEdit;
    public final TextView cameraShakeIntEdit;
    private final LinearLayout rootView;

    private BrickCameraSettingsBinding(LinearLayout rootView, BrickLayout brickCameraSettingsLayout, CheckBox brickCheckbox, TextView cameraFovEdit, TextView cameraShakeDurEdit, TextView cameraShakeIntEdit) {
        this.rootView = rootView;
        this.brickCameraSettingsLayout = brickCameraSettingsLayout;
        this.brickCheckbox = brickCheckbox;
        this.cameraFovEdit = cameraFovEdit;
        this.cameraShakeDurEdit = cameraShakeDurEdit;
        this.cameraShakeIntEdit = cameraShakeIntEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCameraSettingsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCameraSettingsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_camera_settings, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCameraSettingsBinding bind(View rootView) {
        int i = R.id.brick_camera_settings_layout;
        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_camera_settings_layout);
        if (brickLayout != null) {
            i = R.id.brick_checkbox;
            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
            if (checkBox != null) {
                i = R.id.camera_fov_edit;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.camera_fov_edit);
                if (textView != null) {
                    i = R.id.camera_shake_dur_edit;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.camera_shake_dur_edit);
                    if (textView2 != null) {
                        i = R.id.camera_shake_int_edit;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.camera_shake_int_edit);
                        if (textView3 != null) {
                            return new BrickCameraSettingsBinding((LinearLayout) rootView, brickLayout, checkBox, textView, textView2, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
