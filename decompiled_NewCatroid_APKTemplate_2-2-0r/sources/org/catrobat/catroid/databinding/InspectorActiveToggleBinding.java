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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorActiveToggleBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorActiveToggleBinding implements ViewBinding {
    public final CheckBox checkboxIsActive;
    public final EditText editObjectName;
    private final LinearLayout rootView;

    private InspectorActiveToggleBinding(LinearLayout rootView, CheckBox checkboxIsActive, EditText editObjectName) {
        this.rootView = rootView;
        this.checkboxIsActive = checkboxIsActive;
        this.editObjectName = editObjectName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorActiveToggleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorActiveToggleBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_active_toggle, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorActiveToggleBinding bind(View rootView) {
        int i = R.id.checkbox_is_active;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.checkbox_is_active);
        if (checkBox != null) {
            i = R.id.edit_object_name;
            EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_object_name);
            if (editText != null) {
                return new InspectorActiveToggleBinding((LinearLayout) rootView, checkBox, editText);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
