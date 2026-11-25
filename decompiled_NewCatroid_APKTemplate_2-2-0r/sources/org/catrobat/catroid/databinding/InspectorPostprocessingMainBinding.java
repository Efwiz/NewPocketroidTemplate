package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorPostprocessingMainBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorPostprocessingMainBinding implements ViewBinding {
    public final Button btnAddEffect;
    public final CheckBox checkPpActive;
    public final LinearLayout containerEffectsList;
    public final EditText editPpQuality;
    private final LinearLayout rootView;

    private InspectorPostprocessingMainBinding(LinearLayout rootView, Button btnAddEffect, CheckBox checkPpActive, LinearLayout containerEffectsList, EditText editPpQuality) {
        this.rootView = rootView;
        this.btnAddEffect = btnAddEffect;
        this.checkPpActive = checkPpActive;
        this.containerEffectsList = containerEffectsList;
        this.editPpQuality = editPpQuality;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorPostprocessingMainBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorPostprocessingMainBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_postprocessing_main, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorPostprocessingMainBinding bind(View rootView) {
        int i = R.id.btn_add_effect;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_add_effect);
        if (button != null) {
            i = R.id.check_pp_active;
            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.check_pp_active);
            if (checkBox != null) {
                i = R.id.container_effects_list;
                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.container_effects_list);
                if (linearLayout != null) {
                    i = R.id.edit_pp_quality;
                    EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_pp_quality);
                    if (editText != null) {
                        return new InspectorPostprocessingMainBinding((LinearLayout) rootView, button, checkBox, linearLayout, editText);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
