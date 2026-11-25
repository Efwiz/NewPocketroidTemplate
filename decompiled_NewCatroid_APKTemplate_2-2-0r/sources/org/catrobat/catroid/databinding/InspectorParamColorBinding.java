package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorParamColorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorParamColorBinding implements ViewBinding {
    public final Button btnColorPicker;
    private final LinearLayout rootView;
    public final TextView textParamName;

    private InspectorParamColorBinding(LinearLayout rootView, Button btnColorPicker, TextView textParamName) {
        this.rootView = rootView;
        this.btnColorPicker = btnColorPicker;
        this.textParamName = textParamName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorParamColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorParamColorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_param_color, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorParamColorBinding bind(View rootView) {
        int i = R.id.btn_color_picker;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_color_picker);
        if (button != null) {
            i = R.id.text_param_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.text_param_name);
            if (textView != null) {
                return new InspectorParamColorBinding((LinearLayout) rootView, button, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
