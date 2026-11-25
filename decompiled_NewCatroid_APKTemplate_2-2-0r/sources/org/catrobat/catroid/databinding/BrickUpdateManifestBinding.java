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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickUpdateManifestBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickUpdateManifestBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickManifEditApk;
    public final TextView brickManifEditDebug;
    public final TextView brickManifEditMin;
    public final TextView brickManifEditName;
    public final TextView brickManifEditPAdd;
    public final TextView brickManifEditPRem;
    public final TextView brickManifEditPkg;
    public final TextView brickManifEditTarget;
    public final TextView brickManifEditVcode;
    public final TextView brickManifEditVname;
    public final BrickLayout brickUpdateManifestLayout;
    private final LinearLayout rootView;

    private BrickUpdateManifestBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickManifEditApk, TextView brickManifEditDebug, TextView brickManifEditMin, TextView brickManifEditName, TextView brickManifEditPAdd, TextView brickManifEditPRem, TextView brickManifEditPkg, TextView brickManifEditTarget, TextView brickManifEditVcode, TextView brickManifEditVname, BrickLayout brickUpdateManifestLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickManifEditApk = brickManifEditApk;
        this.brickManifEditDebug = brickManifEditDebug;
        this.brickManifEditMin = brickManifEditMin;
        this.brickManifEditName = brickManifEditName;
        this.brickManifEditPAdd = brickManifEditPAdd;
        this.brickManifEditPRem = brickManifEditPRem;
        this.brickManifEditPkg = brickManifEditPkg;
        this.brickManifEditTarget = brickManifEditTarget;
        this.brickManifEditVcode = brickManifEditVcode;
        this.brickManifEditVname = brickManifEditVname;
        this.brickUpdateManifestLayout = brickUpdateManifestLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickUpdateManifestBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickUpdateManifestBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_update_manifest, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickUpdateManifestBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_manif_edit_apk;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_manif_edit_apk);
            if (textView != null) {
                i = R.id.brick_manif_edit_debug;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_manif_edit_debug);
                if (textView2 != null) {
                    i = R.id.brick_manif_edit_min;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_manif_edit_min);
                    if (textView3 != null) {
                        i = R.id.brick_manif_edit_name;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_manif_edit_name);
                        if (textView4 != null) {
                            i = R.id.brick_manif_edit_p_add;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_manif_edit_p_add);
                            if (textView5 != null) {
                                i = R.id.brick_manif_edit_p_rem;
                                TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_manif_edit_p_rem);
                                if (textView6 != null) {
                                    i = R.id.brick_manif_edit_pkg;
                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_manif_edit_pkg);
                                    if (textView7 != null) {
                                        i = R.id.brick_manif_edit_target;
                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_manif_edit_target);
                                        if (textView8 != null) {
                                            i = R.id.brick_manif_edit_vcode;
                                            TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_manif_edit_vcode);
                                            if (textView9 != null) {
                                                i = R.id.brick_manif_edit_vname;
                                                TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_manif_edit_vname);
                                                if (textView10 != null) {
                                                    i = R.id.brick_update_manifest_layout;
                                                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_update_manifest_layout);
                                                    if (brickLayout != null) {
                                                        return new BrickUpdateManifestBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, brickLayout);
                                                    }
                                                }
                                            }
                                        }
                                    }
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
