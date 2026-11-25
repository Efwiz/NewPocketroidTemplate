package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickMouseEventBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickMouseEventBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickMouseStateEdit;
    public final TextView brickMouseXEdit;
    public final TextView brickMouseYEdit;
    private final LinearLayout rootView;

    private BrickMouseEventBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickMouseStateEdit, TextView brickMouseXEdit, TextView brickMouseYEdit) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickMouseStateEdit = brickMouseStateEdit;
        this.brickMouseXEdit = brickMouseXEdit;
        this.brickMouseYEdit = brickMouseYEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickMouseEventBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickMouseEventBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_mouse_event, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickMouseEventBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_mouse_state_edit;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_mouse_state_edit);
            if (textView != null) {
                i = R.id.brick_mouse_x_edit;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_mouse_x_edit);
                if (textView2 != null) {
                    i = R.id.brick_mouse_y_edit;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_mouse_y_edit);
                    if (textView3 != null) {
                        return new BrickMouseEventBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
