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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickThreedHingeBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickThreedHingeBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickHingeAax;
    public final TextView brickHingeAay;
    public final TextView brickHingeAaz;
    public final TextView brickHingeAbx;
    public final TextView brickHingeAby;
    public final TextView brickHingeAbz;
    public final TextView brickHingeId;
    public final TextView brickHingeObja;
    public final TextView brickHingeObjb;
    public final TextView brickHingePax;
    public final TextView brickHingePay;
    public final TextView brickHingePaz;
    public final TextView brickHingePbx;
    public final TextView brickHingePby;
    public final TextView brickHingePbz;
    public final BrickLayout brickThreedHingeLayout;
    private final LinearLayout rootView;

    private BrickThreedHingeBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickHingeAax, TextView brickHingeAay, TextView brickHingeAaz, TextView brickHingeAbx, TextView brickHingeAby, TextView brickHingeAbz, TextView brickHingeId, TextView brickHingeObja, TextView brickHingeObjb, TextView brickHingePax, TextView brickHingePay, TextView brickHingePaz, TextView brickHingePbx, TextView brickHingePby, TextView brickHingePbz, BrickLayout brickThreedHingeLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickHingeAax = brickHingeAax;
        this.brickHingeAay = brickHingeAay;
        this.brickHingeAaz = brickHingeAaz;
        this.brickHingeAbx = brickHingeAbx;
        this.brickHingeAby = brickHingeAby;
        this.brickHingeAbz = brickHingeAbz;
        this.brickHingeId = brickHingeId;
        this.brickHingeObja = brickHingeObja;
        this.brickHingeObjb = brickHingeObjb;
        this.brickHingePax = brickHingePax;
        this.brickHingePay = brickHingePay;
        this.brickHingePaz = brickHingePaz;
        this.brickHingePbx = brickHingePbx;
        this.brickHingePby = brickHingePby;
        this.brickHingePbz = brickHingePbz;
        this.brickThreedHingeLayout = brickThreedHingeLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickThreedHingeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickThreedHingeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_threed_hinge, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickThreedHingeBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_hinge_aax;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_hinge_aax);
            if (textView != null) {
                i = R.id.brick_hinge_aay;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_hinge_aay);
                if (textView2 != null) {
                    i = R.id.brick_hinge_aaz;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_hinge_aaz);
                    if (textView3 != null) {
                        i = R.id.brick_hinge_abx;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_hinge_abx);
                        if (textView4 != null) {
                            i = R.id.brick_hinge_aby;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_hinge_aby);
                            if (textView5 != null) {
                                i = R.id.brick_hinge_abz;
                                TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_hinge_abz);
                                if (textView6 != null) {
                                    i = R.id.brick_hinge_id;
                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_hinge_id);
                                    if (textView7 != null) {
                                        i = R.id.brick_hinge_obja;
                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_hinge_obja);
                                        if (textView8 != null) {
                                            i = R.id.brick_hinge_objb;
                                            TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_hinge_objb);
                                            if (textView9 != null) {
                                                i = R.id.brick_hinge_pax;
                                                TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_hinge_pax);
                                                if (textView10 != null) {
                                                    i = R.id.brick_hinge_pay;
                                                    TextView textView11 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_hinge_pay);
                                                    if (textView11 != null) {
                                                        i = R.id.brick_hinge_paz;
                                                        TextView textView12 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_hinge_paz);
                                                        if (textView12 != null) {
                                                            i = R.id.brick_hinge_pbx;
                                                            TextView textView13 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_hinge_pbx);
                                                            if (textView13 != null) {
                                                                i = R.id.brick_hinge_pby;
                                                                TextView textView14 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_hinge_pby);
                                                                if (textView14 != null) {
                                                                    i = R.id.brick_hinge_pbz;
                                                                    TextView textView15 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_hinge_pbz);
                                                                    if (textView15 != null) {
                                                                        i = R.id.brick_threed_hinge_layout;
                                                                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_threed_hinge_layout);
                                                                        if (brickLayout != null) {
                                                                            return new BrickThreedHingeBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, textView12, textView13, textView14, textView15, brickLayout);
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
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
