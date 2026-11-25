package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorGraphRowFloatBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorGraphRowFloatBinding implements ViewBinding {
    public final ImageButton btnDelete;
    public final EditText editTime;
    public final EditText editValue;
    private final LinearLayout rootView;

    private InspectorGraphRowFloatBinding(LinearLayout rootView, ImageButton btnDelete, EditText editTime, EditText editValue) {
        this.rootView = rootView;
        this.btnDelete = btnDelete;
        this.editTime = editTime;
        this.editValue = editValue;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorGraphRowFloatBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorGraphRowFloatBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_graph_row_float, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorGraphRowFloatBinding bind(View rootView) {
        int i = R.id.btn_delete;
        ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_delete);
        if (imageButton != null) {
            i = R.id.edit_time;
            EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_time);
            if (editText != null) {
                i = R.id.edit_value;
                EditText editText2 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_value);
                if (editText2 != null) {
                    return new InspectorGraphRowFloatBinding((LinearLayout) rootView, imageButton, editText, editText2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
