package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickListenServerBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickListenServerBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickListenServerLayout;
    public final Spinner listenServerSpinner;
    private final LinearLayout rootView;

    private BrickListenServerBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickListenServerLayout, Spinner listenServerSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickListenServerLayout = brickListenServerLayout;
        this.listenServerSpinner = listenServerSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickListenServerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickListenServerBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_listen_server, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickListenServerBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_listen_server_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_listen_server_layout);
            if (brickLayout != null) {
                i = R.id.listen_server_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.listen_server_spinner);
                if (spinner != null) {
                    return new BrickListenServerBinding((LinearLayout) rootView, checkBox, brickLayout, spinner);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
