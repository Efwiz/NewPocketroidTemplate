package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorParamFloatBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorParamFloatBinding implements ViewBinding {
    public final EditText editParamValue;
    private final LinearLayout rootView;
    public final TextView textParamName;

    private InspectorParamFloatBinding(LinearLayout rootView, EditText editParamValue, TextView textParamName) {
        this.rootView = rootView;
        this.editParamValue = editParamValue;
        this.textParamName = textParamName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorParamFloatBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorParamFloatBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_param_float, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorParamFloatBinding bind(View rootView) {
        int i = R.id.edit_param_value;
        EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_param_value);
        if (editText != null) {
            i = R.id.text_param_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.text_param_name);
            if (textView != null) {
                return new InspectorParamFloatBinding((LinearLayout) rootView, editText, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
