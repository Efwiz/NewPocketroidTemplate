package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorKeyframeItemBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorKeyframeItemBinding implements ViewBinding {
    public final ImageButton btnDeleteKeyframe;
    public final EditText editKeyframeTime;
    private final LinearLayout rootView;
    public final Spinner spinnerKeyframeEasing;

    private InspectorKeyframeItemBinding(LinearLayout rootView, ImageButton btnDeleteKeyframe, EditText editKeyframeTime, Spinner spinnerKeyframeEasing) {
        this.rootView = rootView;
        this.btnDeleteKeyframe = btnDeleteKeyframe;
        this.editKeyframeTime = editKeyframeTime;
        this.spinnerKeyframeEasing = spinnerKeyframeEasing;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorKeyframeItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorKeyframeItemBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_keyframe_item, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorKeyframeItemBinding bind(View rootView) {
        int i = R.id.btn_delete_keyframe;
        ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_delete_keyframe);
        if (imageButton != null) {
            i = R.id.edit_keyframe_time;
            EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_keyframe_time);
            if (editText != null) {
                i = R.id.spinner_keyframe_easing;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.spinner_keyframe_easing);
                if (spinner != null) {
                    return new InspectorKeyframeItemBinding((LinearLayout) rootView, imageButton, editText, spinner);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
