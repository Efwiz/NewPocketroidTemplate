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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickShowVarFontBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickShowVarFontBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final Spinner brickShowVarFontAlignSpinner;
    public final TextView brickShowVarFontAndTextview;
    public final TextView brickShowVarFontAtLabel;
    public final TextView brickShowVarFontEditColor;
    public final TextView brickShowVarFontEditFont;
    public final TextView brickShowVarFontEditRelativeSize;
    public final TextView brickShowVarFontEditTextX;
    public final TextView brickShowVarFontEditTextY;
    public final TextView brickShowVarFontLabel;
    public final BrickLayout brickShowVarFontLayout;
    public final TextView brickShowVarFontPercentTextview;
    public final TextView brickShowVarFontSpinner;
    public final TextView brickShowVarFontWithTextview;
    public final TextView brickShowVarFontXTextview;
    public final TextView brickShowVarFontYTextview;
    private final LinearLayout rootView;
    public final Spinner showVariableColorSizeSpinner;

    private BrickShowVarFontBinding(LinearLayout rootView, CheckBox brickCheckbox, Spinner brickShowVarFontAlignSpinner, TextView brickShowVarFontAndTextview, TextView brickShowVarFontAtLabel, TextView brickShowVarFontEditColor, TextView brickShowVarFontEditFont, TextView brickShowVarFontEditRelativeSize, TextView brickShowVarFontEditTextX, TextView brickShowVarFontEditTextY, TextView brickShowVarFontLabel, BrickLayout brickShowVarFontLayout, TextView brickShowVarFontPercentTextview, TextView brickShowVarFontSpinner, TextView brickShowVarFontWithTextview, TextView brickShowVarFontXTextview, TextView brickShowVarFontYTextview, Spinner showVariableColorSizeSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickShowVarFontAlignSpinner = brickShowVarFontAlignSpinner;
        this.brickShowVarFontAndTextview = brickShowVarFontAndTextview;
        this.brickShowVarFontAtLabel = brickShowVarFontAtLabel;
        this.brickShowVarFontEditColor = brickShowVarFontEditColor;
        this.brickShowVarFontEditFont = brickShowVarFontEditFont;
        this.brickShowVarFontEditRelativeSize = brickShowVarFontEditRelativeSize;
        this.brickShowVarFontEditTextX = brickShowVarFontEditTextX;
        this.brickShowVarFontEditTextY = brickShowVarFontEditTextY;
        this.brickShowVarFontLabel = brickShowVarFontLabel;
        this.brickShowVarFontLayout = brickShowVarFontLayout;
        this.brickShowVarFontPercentTextview = brickShowVarFontPercentTextview;
        this.brickShowVarFontSpinner = brickShowVarFontSpinner;
        this.brickShowVarFontWithTextview = brickShowVarFontWithTextview;
        this.brickShowVarFontXTextview = brickShowVarFontXTextview;
        this.brickShowVarFontYTextview = brickShowVarFontYTextview;
        this.showVariableColorSizeSpinner = showVariableColorSizeSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickShowVarFontBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickShowVarFontBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_show_var_font, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickShowVarFontBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_show_var_font_align_spinner;
            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_show_var_font_align_spinner);
            if (spinner != null) {
                i = R.id.brick_show_var_font_and_textview;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_var_font_and_textview);
                if (textView != null) {
                    i = R.id.brick_show_var_font_at_label;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_var_font_at_label);
                    if (textView2 != null) {
                        i = R.id.brick_show_var_font_edit_color;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_var_font_edit_color);
                        if (textView3 != null) {
                            i = R.id.brick_show_var_font_edit_font;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_var_font_edit_font);
                            if (textView4 != null) {
                                i = R.id.brick_show_var_font_edit_relative_size;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_var_font_edit_relative_size);
                                if (textView5 != null) {
                                    i = R.id.brick_show_var_font_edit_text_x;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_var_font_edit_text_x);
                                    if (textView6 != null) {
                                        i = R.id.brick_show_var_font_edit_text_y;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_var_font_edit_text_y);
                                        if (textView7 != null) {
                                            i = R.id.brick_show_var_font_label;
                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_var_font_label);
                                            if (textView8 != null) {
                                                i = R.id.brick_show_var_font_layout;
                                                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_show_var_font_layout);
                                                if (brickLayout != null) {
                                                    i = R.id.brick_show_var_font_percent_textview;
                                                    TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_var_font_percent_textview);
                                                    if (textView9 != null) {
                                                        i = R.id.brick_show_var_font_spinner;
                                                        TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_var_font_spinner);
                                                        if (textView10 != null) {
                                                            i = R.id.brick_show_var_font_with_textview;
                                                            TextView textView11 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_var_font_with_textview);
                                                            if (textView11 != null) {
                                                                i = R.id.brick_show_var_font_x_textview;
                                                                TextView textView12 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_var_font_x_textview);
                                                                if (textView12 != null) {
                                                                    i = R.id.brick_show_var_font_y_textview;
                                                                    TextView textView13 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_var_font_y_textview);
                                                                    if (textView13 != null) {
                                                                        i = R.id.show_variable_color_size_spinner;
                                                                        Spinner spinner2 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.show_variable_color_size_spinner);
                                                                        if (spinner2 != null) {
                                                                            return new BrickShowVarFontBinding((LinearLayout) rootView, checkBox, spinner, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, brickLayout, textView9, textView10, textView11, textView12, textView13, spinner2);
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
