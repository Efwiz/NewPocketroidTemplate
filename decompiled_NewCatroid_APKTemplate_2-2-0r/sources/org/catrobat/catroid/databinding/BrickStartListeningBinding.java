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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickStartListeningBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickStartListeningBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickStartListeningLayout;
    public final Spinner brickStartListeningSpinner;
    private final LinearLayout rootView;

    private BrickStartListeningBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickStartListeningLayout, Spinner brickStartListeningSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickStartListeningLayout = brickStartListeningLayout;
        this.brickStartListeningSpinner = brickStartListeningSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickStartListeningBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickStartListeningBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_start_listening, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickStartListeningBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_start_listening_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_start_listening_layout);
            if (brickLayout != null) {
                i = R.id.brick_start_listening_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_start_listening_spinner);
                if (spinner != null) {
                    return new BrickStartListeningBinding((LinearLayout) rootView, checkBox, brickLayout, spinner);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
