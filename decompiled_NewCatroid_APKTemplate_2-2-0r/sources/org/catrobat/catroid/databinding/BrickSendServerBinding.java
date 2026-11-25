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
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSendServerBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSendServerBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSendServerEditText;
    public final BrickLayout brickSendServerLayout;
    public final TextView brickShowToastLabel;
    private final LinearLayout rootView;

    private BrickSendServerBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSendServerEditText, BrickLayout brickSendServerLayout, TextView brickShowToastLabel) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSendServerEditText = brickSendServerEditText;
        this.brickSendServerLayout = brickSendServerLayout;
        this.brickShowToastLabel = brickShowToastLabel;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSendServerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSendServerBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_send_server, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSendServerBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_send_server_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_send_server_edit_text);
            if (textView != null) {
                i = R.id.brick_send_server_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_send_server_layout);
                if (brickLayout != null) {
                    i = R.id.brick_show_toast_label;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_label);
                    if (textView2 != null) {
                        return new BrickSendServerBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
