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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorRenderBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorRenderBinding implements ViewBinding {
    public final Button btnSelectModel;
    private final LinearLayout rootView;
    public final TextView textModelPath;

    private InspectorRenderBinding(LinearLayout rootView, Button btnSelectModel, TextView textModelPath) {
        this.rootView = rootView;
        this.btnSelectModel = btnSelectModel;
        this.textModelPath = textModelPath;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorRenderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorRenderBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_render, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorRenderBinding bind(View rootView) {
        int i = R.id.btn_select_model;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_select_model);
        if (button != null) {
            i = R.id.text_model_path;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.text_model_path);
            if (textView != null) {
                return new InspectorRenderBinding((LinearLayout) rootView, button, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
