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

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickUserDefinedScriptBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickUserDefinedScriptBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final Spinner brickSetScreenRefreshSpinner;
    public final LinearLayout brickUserDefinedScriptBrick;
    private final LinearLayout rootView;
    public final LinearLayout userBrickSpace;

    private BrickUserDefinedScriptBinding(LinearLayout rootView, CheckBox brickCheckbox, Spinner brickSetScreenRefreshSpinner, LinearLayout brickUserDefinedScriptBrick, LinearLayout userBrickSpace) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetScreenRefreshSpinner = brickSetScreenRefreshSpinner;
        this.brickUserDefinedScriptBrick = brickUserDefinedScriptBrick;
        this.userBrickSpace = userBrickSpace;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickUserDefinedScriptBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickUserDefinedScriptBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_user_defined_script, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickUserDefinedScriptBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_screen_refresh_spinner;
            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_set_screen_refresh_spinner);
            if (spinner != null) {
                LinearLayout linearLayout = (LinearLayout) rootView;
                i = R.id.user_brick_space;
                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.user_brick_space);
                if (linearLayout2 != null) {
                    return new BrickUserDefinedScriptBinding(linearLayout, checkBox, spinner, linearLayout, linearLayout2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
