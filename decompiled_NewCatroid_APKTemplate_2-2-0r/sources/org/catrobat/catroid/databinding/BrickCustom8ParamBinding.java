package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCustom8ParamBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCustom8ParamBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickCustomLayout;
    public final TextView customBrickFormula1;
    public final TextView customBrickFormula2;
    public final TextView customBrickFormula3;
    public final TextView customBrickFormula4;
    public final TextView customBrickFormula5;
    public final TextView customBrickFormula6;
    public final TextView customBrickFormula7;
    public final TextView customBrickFormula8;
    public final TextView customBrickFormula9;
    public final TextView customBrickHeaderPart1;
    public final TextView customBrickHeaderPart2;
    public final TextView customBrickHeaderPart3;
    public final TextView customBrickHeaderPart4;
    public final TextView customBrickHeaderPart5;
    public final TextView customBrickHeaderPart6;
    public final TextView customBrickHeaderPart7;
    public final TextView customBrickHeaderPart8;
    public final TextView customBrickHeaderPart9;
    public final Spinner customBrickSpinner1;
    public final Spinner customBrickSpinner2;
    public final Spinner customBrickSpinner3;
    public final Spinner customBrickSpinner4;
    public final Spinner customBrickSpinner5;
    public final Spinner customBrickSpinner6;
    public final Spinner customBrickSpinner7;
    public final Spinner customBrickSpinner8;
    public final Spinner customBrickSpinner9;
    private final LinearLayout rootView;

    private BrickCustom8ParamBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickCustomLayout, TextView customBrickFormula1, TextView customBrickFormula2, TextView customBrickFormula3, TextView customBrickFormula4, TextView customBrickFormula5, TextView customBrickFormula6, TextView customBrickFormula7, TextView customBrickFormula8, TextView customBrickFormula9, TextView customBrickHeaderPart1, TextView customBrickHeaderPart2, TextView customBrickHeaderPart3, TextView customBrickHeaderPart4, TextView customBrickHeaderPart5, TextView customBrickHeaderPart6, TextView customBrickHeaderPart7, TextView customBrickHeaderPart8, TextView customBrickHeaderPart9, Spinner customBrickSpinner1, Spinner customBrickSpinner2, Spinner customBrickSpinner3, Spinner customBrickSpinner4, Spinner customBrickSpinner5, Spinner customBrickSpinner6, Spinner customBrickSpinner7, Spinner customBrickSpinner8, Spinner customBrickSpinner9) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCustomLayout = brickCustomLayout;
        this.customBrickFormula1 = customBrickFormula1;
        this.customBrickFormula2 = customBrickFormula2;
        this.customBrickFormula3 = customBrickFormula3;
        this.customBrickFormula4 = customBrickFormula4;
        this.customBrickFormula5 = customBrickFormula5;
        this.customBrickFormula6 = customBrickFormula6;
        this.customBrickFormula7 = customBrickFormula7;
        this.customBrickFormula8 = customBrickFormula8;
        this.customBrickFormula9 = customBrickFormula9;
        this.customBrickHeaderPart1 = customBrickHeaderPart1;
        this.customBrickHeaderPart2 = customBrickHeaderPart2;
        this.customBrickHeaderPart3 = customBrickHeaderPart3;
        this.customBrickHeaderPart4 = customBrickHeaderPart4;
        this.customBrickHeaderPart5 = customBrickHeaderPart5;
        this.customBrickHeaderPart6 = customBrickHeaderPart6;
        this.customBrickHeaderPart7 = customBrickHeaderPart7;
        this.customBrickHeaderPart8 = customBrickHeaderPart8;
        this.customBrickHeaderPart9 = customBrickHeaderPart9;
        this.customBrickSpinner1 = customBrickSpinner1;
        this.customBrickSpinner2 = customBrickSpinner2;
        this.customBrickSpinner3 = customBrickSpinner3;
        this.customBrickSpinner4 = customBrickSpinner4;
        this.customBrickSpinner5 = customBrickSpinner5;
        this.customBrickSpinner6 = customBrickSpinner6;
        this.customBrickSpinner7 = customBrickSpinner7;
        this.customBrickSpinner8 = customBrickSpinner8;
        this.customBrickSpinner9 = customBrickSpinner9;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCustom8ParamBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCustom8ParamBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_custom_8_param, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCustom8ParamBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_custom_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_custom_layout);
            if (brickLayout != null) {
                i = R.id.custom_brick_formula_1;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_formula_1);
                if (textView != null) {
                    i = R.id.custom_brick_formula_2;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_formula_2);
                    if (textView2 != null) {
                        i = R.id.custom_brick_formula_3;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_formula_3);
                        if (textView3 != null) {
                            i = R.id.custom_brick_formula_4;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_formula_4);
                            if (textView4 != null) {
                                i = R.id.custom_brick_formula_5;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_formula_5);
                                if (textView5 != null) {
                                    i = R.id.custom_brick_formula_6;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_formula_6);
                                    if (textView6 != null) {
                                        i = R.id.custom_brick_formula_7;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_formula_7);
                                        if (textView7 != null) {
                                            i = R.id.custom_brick_formula_8;
                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_formula_8);
                                            if (textView8 != null) {
                                                i = R.id.custom_brick_formula_9;
                                                TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_formula_9);
                                                if (textView9 != null) {
                                                    i = R.id.custom_brick_header_part_1;
                                                    TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_header_part_1);
                                                    if (textView10 != null) {
                                                        i = R.id.custom_brick_header_part_2;
                                                        TextView textView11 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_header_part_2);
                                                        if (textView11 != null) {
                                                            i = R.id.custom_brick_header_part_3;
                                                            TextView textView12 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_header_part_3);
                                                            if (textView12 != null) {
                                                                i = R.id.custom_brick_header_part_4;
                                                                TextView textView13 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_header_part_4);
                                                                if (textView13 != null) {
                                                                    i = R.id.custom_brick_header_part_5;
                                                                    TextView textView14 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_header_part_5);
                                                                    if (textView14 != null) {
                                                                        i = R.id.custom_brick_header_part_6;
                                                                        TextView textView15 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_header_part_6);
                                                                        if (textView15 != null) {
                                                                            i = R.id.custom_brick_header_part_7;
                                                                            TextView textView16 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_header_part_7);
                                                                            if (textView16 != null) {
                                                                                i = R.id.custom_brick_header_part_8;
                                                                                TextView textView17 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_header_part_8);
                                                                                if (textView17 != null) {
                                                                                    i = R.id.custom_brick_header_part_9;
                                                                                    TextView textView18 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_header_part_9);
                                                                                    if (textView18 != null) {
                                                                                        i = R.id.custom_brick_spinner_1;
                                                                                        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.custom_brick_spinner_1);
                                                                                        if (spinner != null) {
                                                                                            i = R.id.custom_brick_spinner_2;
                                                                                            Spinner spinner2 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.custom_brick_spinner_2);
                                                                                            if (spinner2 != null) {
                                                                                                i = R.id.custom_brick_spinner_3;
                                                                                                Spinner spinner3 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.custom_brick_spinner_3);
                                                                                                if (spinner3 != null) {
                                                                                                    i = R.id.custom_brick_spinner_4;
                                                                                                    Spinner spinner4 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.custom_brick_spinner_4);
                                                                                                    if (spinner4 != null) {
                                                                                                        i = R.id.custom_brick_spinner_5;
                                                                                                        Spinner spinner5 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.custom_brick_spinner_5);
                                                                                                        if (spinner5 != null) {
                                                                                                            i = R.id.custom_brick_spinner_6;
                                                                                                            Spinner spinner6 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.custom_brick_spinner_6);
                                                                                                            if (spinner6 != null) {
                                                                                                                i = R.id.custom_brick_spinner_7;
                                                                                                                Spinner spinner7 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.custom_brick_spinner_7);
                                                                                                                if (spinner7 != null) {
                                                                                                                    i = R.id.custom_brick_spinner_8;
                                                                                                                    Spinner spinner8 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.custom_brick_spinner_8);
                                                                                                                    if (spinner8 != null) {
                                                                                                                        i = R.id.custom_brick_spinner_9;
                                                                                                                        Spinner spinner9 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.custom_brick_spinner_9);
                                                                                                                        if (spinner9 != null) {
                                                                                                                            return new BrickCustom8ParamBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, textView12, textView13, textView14, textView15, textView16, textView17, textView18, spinner, spinner2, spinner3, spinner4, spinner5, spinner6, spinner7, spinner8, spinner9);
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
