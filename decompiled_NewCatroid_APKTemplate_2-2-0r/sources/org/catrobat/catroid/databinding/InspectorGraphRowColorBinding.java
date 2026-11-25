package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorGraphRowColorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorGraphRowColorBinding implements ViewBinding {
    public final Button btnColor;
    public final ImageButton btnDelete;
    public final EditText editTime;
    private final LinearLayout rootView;

    private InspectorGraphRowColorBinding(LinearLayout rootView, Button btnColor, ImageButton btnDelete, EditText editTime) {
        this.rootView = rootView;
        this.btnColor = btnColor;
        this.btnDelete = btnDelete;
        this.editTime = editTime;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorGraphRowColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorGraphRowColorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_graph_row_color, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorGraphRowColorBinding bind(View rootView) {
        int i = R.id.btn_color;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_color);
        if (button != null) {
            i = R.id.btn_delete;
            ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_delete);
            if (imageButton != null) {
                i = R.id.edit_time;
                EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_time);
                if (editText != null) {
                    return new InspectorGraphRowColorBinding((LinearLayout) rootView, button, imageButton, editText);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
