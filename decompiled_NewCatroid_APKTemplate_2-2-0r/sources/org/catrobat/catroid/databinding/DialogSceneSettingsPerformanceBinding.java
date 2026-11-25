package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogSceneSettingsPerformanceBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogSceneSettingsPerformanceBinding implements ViewBinding {
    public final EditText editBones;
    public final EditText editDirLights;
    public final EditText editPointLights;
    public final EditText editSpotLights;
    private final GridLayout rootView;

    private DialogSceneSettingsPerformanceBinding(GridLayout rootView, EditText editBones, EditText editDirLights, EditText editPointLights, EditText editSpotLights) {
        this.rootView = rootView;
        this.editBones = editBones;
        this.editDirLights = editDirLights;
        this.editPointLights = editPointLights;
        this.editSpotLights = editSpotLights;
    }

    @Override // androidx.viewbinding.ViewBinding
    public GridLayout getRoot() {
        return this.rootView;
    }

    public static DialogSceneSettingsPerformanceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogSceneSettingsPerformanceBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_scene_settings_performance, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogSceneSettingsPerformanceBinding bind(View rootView) {
        int i = R.id.edit_bones;
        EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_bones);
        if (editText != null) {
            i = R.id.edit_dir_lights;
            EditText editText2 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_dir_lights);
            if (editText2 != null) {
                i = R.id.edit_point_lights;
                EditText editText3 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_point_lights);
                if (editText3 != null) {
                    i = R.id.edit_spot_lights;
                    EditText editText4 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_spot_lights);
                    if (editText4 != null) {
                        return new DialogSceneSettingsPerformanceBinding((GridLayout) rootView, editText, editText2, editText3, editText4);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
