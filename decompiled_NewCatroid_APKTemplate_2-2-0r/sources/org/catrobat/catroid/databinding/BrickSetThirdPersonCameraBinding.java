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

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetThirdPersonCameraBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetThirdPersonCameraBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickThirdPersonCameraDistance;
    public final TextView brickThirdPersonCameraHeight;
    public final TextView brickThirdPersonCameraObjectId;
    public final TextView brickThirdPersonCameraPitch;
    private final LinearLayout rootView;

    private BrickSetThirdPersonCameraBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickThirdPersonCameraDistance, TextView brickThirdPersonCameraHeight, TextView brickThirdPersonCameraObjectId, TextView brickThirdPersonCameraPitch) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickThirdPersonCameraDistance = brickThirdPersonCameraDistance;
        this.brickThirdPersonCameraHeight = brickThirdPersonCameraHeight;
        this.brickThirdPersonCameraObjectId = brickThirdPersonCameraObjectId;
        this.brickThirdPersonCameraPitch = brickThirdPersonCameraPitch;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetThirdPersonCameraBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetThirdPersonCameraBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_third_person_camera, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetThirdPersonCameraBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_third_person_camera_distance;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_third_person_camera_distance);
            if (textView != null) {
                i = R.id.brick_third_person_camera_height;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_third_person_camera_height);
                if (textView2 != null) {
                    i = R.id.brick_third_person_camera_object_id;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_third_person_camera_object_id);
                    if (textView3 != null) {
                        i = R.id.brick_third_person_camera_pitch;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_third_person_camera_pitch);
                        if (textView4 != null) {
                            return new BrickSetThirdPersonCameraBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
