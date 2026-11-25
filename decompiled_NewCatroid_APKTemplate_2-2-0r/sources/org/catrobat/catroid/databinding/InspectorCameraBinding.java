package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorCameraBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorCameraBinding implements ViewBinding {
    public final CheckBox checkMainCamera;
    public final EditText editCamFar;
    public final EditText editCamFov;
    public final EditText editCamNear;
    private final LinearLayout rootView;

    private InspectorCameraBinding(LinearLayout rootView, CheckBox checkMainCamera, EditText editCamFar, EditText editCamFov, EditText editCamNear) {
        this.rootView = rootView;
        this.checkMainCamera = checkMainCamera;
        this.editCamFar = editCamFar;
        this.editCamFov = editCamFov;
        this.editCamNear = editCamNear;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorCameraBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorCameraBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_camera, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorCameraBinding bind(View rootView) {
        int i = R.id.check_main_camera;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.check_main_camera);
        if (checkBox != null) {
            i = R.id.edit_cam_far;
            EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_cam_far);
            if (editText != null) {
                i = R.id.edit_cam_fov;
                EditText editText2 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_cam_fov);
                if (editText2 != null) {
                    i = R.id.edit_cam_near;
                    EditText editText3 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_cam_near);
                    if (editText3 != null) {
                        return new InspectorCameraBinding((LinearLayout) rootView, checkBox, editText, editText2, editText3);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
