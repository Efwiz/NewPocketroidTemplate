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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorScriptBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorScriptBinding implements ViewBinding {
    public final Button btnSelectScript;
    private final LinearLayout rootView;
    public final TextView textScriptPath;

    private InspectorScriptBinding(LinearLayout rootView, Button btnSelectScript, TextView textScriptPath) {
        this.rootView = rootView;
        this.btnSelectScript = btnSelectScript;
        this.textScriptPath = textScriptPath;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorScriptBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorScriptBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_script, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorScriptBinding bind(View rootView) {
        int i = R.id.btn_select_script;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_select_script);
        if (button != null) {
            i = R.id.text_script_path;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.text_script_path);
            if (textView != null) {
                return new InspectorScriptBinding((LinearLayout) rootView, button, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
