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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickShowVariableColorSizeAlignmentBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickShowVariableColorSizeAlignmentBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final Spinner brickShowVariableColorSizeAlignSpinner;
    public final TextView brickShowVariableColorSizeAndTextview;
    public final TextView brickShowVariableColorSizeAtLabel;
    public final TextView brickShowVariableColorSizeEditColor;
    public final TextView brickShowVariableColorSizeEditRelativeSize;
    public final TextView brickShowVariableColorSizeEditTextX;
    public final TextView brickShowVariableColorSizeEditTextY;
    public final TextView brickShowVariableColorSizeLabel;
    public final BrickLayout brickShowVariableColorSizeLayout;
    public final TextView brickShowVariableColorSizePercentTextview;
    public final TextView brickShowVariableColorSizeSpinner;
    public final TextView brickShowVariableColorSizeWithTextview;
    public final TextView brickShowVariableColorSizeXTextview;
    public final TextView brickShowVariableColorSizeYTextview;
    private final LinearLayout rootView;
    public final Spinner showVariableColorSizeSpinner;

    private BrickShowVariableColorSizeAlignmentBinding(LinearLayout rootView, CheckBox brickCheckbox, Spinner brickShowVariableColorSizeAlignSpinner, TextView brickShowVariableColorSizeAndTextview, TextView brickShowVariableColorSizeAtLabel, TextView brickShowVariableColorSizeEditColor, TextView brickShowVariableColorSizeEditRelativeSize, TextView brickShowVariableColorSizeEditTextX, TextView brickShowVariableColorSizeEditTextY, TextView brickShowVariableColorSizeLabel, BrickLayout brickShowVariableColorSizeLayout, TextView brickShowVariableColorSizePercentTextview, TextView brickShowVariableColorSizeSpinner, TextView brickShowVariableColorSizeWithTextview, TextView brickShowVariableColorSizeXTextview, TextView brickShowVariableColorSizeYTextview, Spinner showVariableColorSizeSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickShowVariableColorSizeAlignSpinner = brickShowVariableColorSizeAlignSpinner;
        this.brickShowVariableColorSizeAndTextview = brickShowVariableColorSizeAndTextview;
        this.brickShowVariableColorSizeAtLabel = brickShowVariableColorSizeAtLabel;
        this.brickShowVariableColorSizeEditColor = brickShowVariableColorSizeEditColor;
        this.brickShowVariableColorSizeEditRelativeSize = brickShowVariableColorSizeEditRelativeSize;
        this.brickShowVariableColorSizeEditTextX = brickShowVariableColorSizeEditTextX;
        this.brickShowVariableColorSizeEditTextY = brickShowVariableColorSizeEditTextY;
        this.brickShowVariableColorSizeLabel = brickShowVariableColorSizeLabel;
        this.brickShowVariableColorSizeLayout = brickShowVariableColorSizeLayout;
        this.brickShowVariableColorSizePercentTextview = brickShowVariableColorSizePercentTextview;
        this.brickShowVariableColorSizeSpinner = brickShowVariableColorSizeSpinner;
        this.brickShowVariableColorSizeWithTextview = brickShowVariableColorSizeWithTextview;
        this.brickShowVariableColorSizeXTextview = brickShowVariableColorSizeXTextview;
        this.brickShowVariableColorSizeYTextview = brickShowVariableColorSizeYTextview;
        this.showVariableColorSizeSpinner = showVariableColorSizeSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickShowVariableColorSizeAlignmentBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickShowVariableColorSizeAlignmentBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_show_variable_color_size_alignment, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickShowVariableColorSizeAlignmentBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_show_variable_color_size_align_spinner;
            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_color_size_align_spinner);
            if (spinner != null) {
                i = R.id.brick_show_variable_color_size_and_textview;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_color_size_and_textview);
                if (textView != null) {
                    i = R.id.brick_show_variable_color_size_at_label;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_color_size_at_label);
                    if (textView2 != null) {
                        i = R.id.brick_show_variable_color_size_edit_color;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_color_size_edit_color);
                        if (textView3 != null) {
                            i = R.id.brick_show_variable_color_size_edit_relative_size;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_color_size_edit_relative_size);
                            if (textView4 != null) {
                                i = R.id.brick_show_variable_color_size_edit_text_x;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_color_size_edit_text_x);
                                if (textView5 != null) {
                                    i = R.id.brick_show_variable_color_size_edit_text_y;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_color_size_edit_text_y);
                                    if (textView6 != null) {
                                        i = R.id.brick_show_variable_color_size_label;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_color_size_label);
                                        if (textView7 != null) {
                                            i = R.id.brick_show_variable_color_size_layout;
                                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_color_size_layout);
                                            if (brickLayout != null) {
                                                i = R.id.brick_show_variable_color_size_percent_textview;
                                                TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_color_size_percent_textview);
                                                if (textView8 != null) {
                                                    i = R.id.brick_show_variable_color_size_spinner;
                                                    TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_color_size_spinner);
                                                    if (textView9 != null) {
                                                        i = R.id.brick_show_variable_color_size_with_textview;
                                                        TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_color_size_with_textview);
                                                        if (textView10 != null) {
                                                            i = R.id.brick_show_variable_color_size_x_textview;
                                                            TextView textView11 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_color_size_x_textview);
                                                            if (textView11 != null) {
                                                                i = R.id.brick_show_variable_color_size_y_textview;
                                                                TextView textView12 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_color_size_y_textview);
                                                                if (textView12 != null) {
                                                                    i = R.id.show_variable_color_size_spinner;
                                                                    Spinner spinner2 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.show_variable_color_size_spinner);
                                                                    if (spinner2 != null) {
                                                                        return new BrickShowVariableColorSizeAlignmentBinding((LinearLayout) rootView, checkBox, spinner, textView, textView2, textView3, textView4, textView5, textView6, textView7, brickLayout, textView8, textView9, textView10, textView11, textView12, spinner2);
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
