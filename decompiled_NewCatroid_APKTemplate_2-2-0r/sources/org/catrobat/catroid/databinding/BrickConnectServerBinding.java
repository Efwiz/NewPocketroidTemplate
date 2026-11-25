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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickConnectServerBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickConnectServerBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickConnectServerEditIp;
    public final TextView brickConnectServerEditPort;
    public final TextView brickConnectServerLabel;
    public final TextView brickConnectServerLabel2;
    public final TextView brickConnectServerLabel3;
    public final BrickLayout brickConnectServerLayout;
    private final LinearLayout rootView;

    private BrickConnectServerBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickConnectServerEditIp, TextView brickConnectServerEditPort, TextView brickConnectServerLabel, TextView brickConnectServerLabel2, TextView brickConnectServerLabel3, BrickLayout brickConnectServerLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickConnectServerEditIp = brickConnectServerEditIp;
        this.brickConnectServerEditPort = brickConnectServerEditPort;
        this.brickConnectServerLabel = brickConnectServerLabel;
        this.brickConnectServerLabel2 = brickConnectServerLabel2;
        this.brickConnectServerLabel3 = brickConnectServerLabel3;
        this.brickConnectServerLayout = brickConnectServerLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickConnectServerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickConnectServerBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_connect_server, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickConnectServerBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_connect_server_edit_ip;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_connect_server_edit_ip);
            if (textView != null) {
                i = R.id.brick_connect_server_edit_port;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_connect_server_edit_port);
                if (textView2 != null) {
                    i = R.id.brick_connect_server_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_connect_server_label);
                    if (textView3 != null) {
                        i = R.id.brick_connect_server_label2;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_connect_server_label2);
                        if (textView4 != null) {
                            i = R.id.brick_connect_server_label3;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_connect_server_label3);
                            if (textView5 != null) {
                                i = R.id.brick_connect_server_layout;
                                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_connect_server_layout);
                                if (brickLayout != null) {
                                    return new BrickConnectServerBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, textView5, brickLayout);
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
