package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorAnimationBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorAnimationBinding implements ViewBinding {
    public final CheckBox checkboxAnimLoop;
    public final EditText editAnimSpeed;
    public final EditText editAnimTransition;
    private final LinearLayout rootView;
    public final Spinner spinnerAnimations;

    private InspectorAnimationBinding(LinearLayout rootView, CheckBox checkboxAnimLoop, EditText editAnimSpeed, EditText editAnimTransition, Spinner spinnerAnimations) {
        this.rootView = rootView;
        this.checkboxAnimLoop = checkboxAnimLoop;
        this.editAnimSpeed = editAnimSpeed;
        this.editAnimTransition = editAnimTransition;
        this.spinnerAnimations = spinnerAnimations;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorAnimationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorAnimationBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_animation, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorAnimationBinding bind(View rootView) {
        int i = R.id.checkbox_anim_loop;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.checkbox_anim_loop);
        if (checkBox != null) {
            i = R.id.edit_anim_speed;
            EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_anim_speed);
            if (editText != null) {
                i = R.id.edit_anim_transition;
                EditText editText2 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_anim_transition);
                if (editText2 != null) {
                    i = R.id.spinner_animations;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.spinner_animations);
                    if (spinner != null) {
                        return new InspectorAnimationBinding((LinearLayout) rootView, checkBox, editText, editText2, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
