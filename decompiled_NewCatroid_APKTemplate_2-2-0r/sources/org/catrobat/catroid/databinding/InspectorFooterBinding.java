package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorFooterBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorFooterBinding implements ViewBinding {
    public final Button btnAddComponent;
    private final FrameLayout rootView;

    private InspectorFooterBinding(FrameLayout rootView, Button btnAddComponent) {
        this.rootView = rootView;
        this.btnAddComponent = btnAddComponent;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static InspectorFooterBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorFooterBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_footer, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorFooterBinding bind(View rootView) {
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_add_component);
        if (button != null) {
            return new InspectorFooterBinding((FrameLayout) rootView, button);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(R.id.btn_add_component)));
    }
}
