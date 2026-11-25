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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickShowTextFontBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickShowTextFontBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickShowTextFontAtLabel;
    public final TextView brickShowTextFontColorSizeAndTextview;
    public final TextView brickShowTextFontEditColor;
    public final TextView brickShowTextFontEditFont;
    public final TextView brickShowTextFontEditName;
    public final TextView brickShowTextFontEditSize;
    public final TextView brickShowTextFontEditText;
    public final TextView brickShowTextFontEditX;
    public final TextView brickShowTextFontEditY;
    public final TextView brickShowTextFontLabel;
    public final TextView brickShowTextFontLabel2;
    public final BrickLayout brickShowTextFontLayout;
    public final TextView brickShowTextFontPercentTextview;
    public final Spinner brickShowTextFontSpinner;
    public final TextView brickShowTextFontSpinnerText;
    public final TextView brickShowTextFontWithTextview;
    public final TextView brickShowTextFontXTextview;
    public final TextView brickShowTextFontYTextview;
    private final LinearLayout rootView;

    private BrickShowTextFontBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickShowTextFontAtLabel, TextView brickShowTextFontColorSizeAndTextview, TextView brickShowTextFontEditColor, TextView brickShowTextFontEditFont, TextView brickShowTextFontEditName, TextView brickShowTextFontEditSize, TextView brickShowTextFontEditText, TextView brickShowTextFontEditX, TextView brickShowTextFontEditY, TextView brickShowTextFontLabel, TextView brickShowTextFontLabel2, BrickLayout brickShowTextFontLayout, TextView brickShowTextFontPercentTextview, Spinner brickShowTextFontSpinner, TextView brickShowTextFontSpinnerText, TextView brickShowTextFontWithTextview, TextView brickShowTextFontXTextview, TextView brickShowTextFontYTextview) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickShowTextFontAtLabel = brickShowTextFontAtLabel;
        this.brickShowTextFontColorSizeAndTextview = brickShowTextFontColorSizeAndTextview;
        this.brickShowTextFontEditColor = brickShowTextFontEditColor;
        this.brickShowTextFontEditFont = brickShowTextFontEditFont;
        this.brickShowTextFontEditName = brickShowTextFontEditName;
        this.brickShowTextFontEditSize = brickShowTextFontEditSize;
        this.brickShowTextFontEditText = brickShowTextFontEditText;
        this.brickShowTextFontEditX = brickShowTextFontEditX;
        this.brickShowTextFontEditY = brickShowTextFontEditY;
        this.brickShowTextFontLabel = brickShowTextFontLabel;
        this.brickShowTextFontLabel2 = brickShowTextFontLabel2;
        this.brickShowTextFontLayout = brickShowTextFontLayout;
        this.brickShowTextFontPercentTextview = brickShowTextFontPercentTextview;
        this.brickShowTextFontSpinner = brickShowTextFontSpinner;
        this.brickShowTextFontSpinnerText = brickShowTextFontSpinnerText;
        this.brickShowTextFontWithTextview = brickShowTextFontWithTextview;
        this.brickShowTextFontXTextview = brickShowTextFontXTextview;
        this.brickShowTextFontYTextview = brickShowTextFontYTextview;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickShowTextFontBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickShowTextFontBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_show_text_font, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickShowTextFontBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_show_text_font_at_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_at_label);
            if (textView != null) {
                i = R.id.brick_show_text_font_color_size_and_textview;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_color_size_and_textview);
                if (textView2 != null) {
                    i = R.id.brick_show_text_font_edit_color;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_edit_color);
                    if (textView3 != null) {
                        i = R.id.brick_show_text_font_edit_font;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_edit_font);
                        if (textView4 != null) {
                            i = R.id.brick_show_text_font_edit_name;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_edit_name);
                            if (textView5 != null) {
                                i = R.id.brick_show_text_font_edit_size;
                                TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_edit_size);
                                if (textView6 != null) {
                                    i = R.id.brick_show_text_font_edit_text;
                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_edit_text);
                                    if (textView7 != null) {
                                        i = R.id.brick_show_text_font_edit_x;
                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_edit_x);
                                        if (textView8 != null) {
                                            i = R.id.brick_show_text_font_edit_y;
                                            TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_edit_y);
                                            if (textView9 != null) {
                                                i = R.id.brick_show_text_font_label;
                                                TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_label);
                                                if (textView10 != null) {
                                                    i = R.id.brick_show_text_font_label2;
                                                    TextView textView11 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_label2);
                                                    if (textView11 != null) {
                                                        i = R.id.brick_show_text_font_layout;
                                                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_layout);
                                                        if (brickLayout != null) {
                                                            i = R.id.brick_show_text_font_percent_textview;
                                                            TextView textView12 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_percent_textview);
                                                            if (textView12 != null) {
                                                                i = R.id.brick_show_text_font_spinner;
                                                                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_spinner);
                                                                if (spinner != null) {
                                                                    i = R.id.brick_show_text_font_spinner_text;
                                                                    TextView textView13 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_spinner_text);
                                                                    if (textView13 != null) {
                                                                        i = R.id.brick_show_text_font_with_textview;
                                                                        TextView textView14 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_with_textview);
                                                                        if (textView14 != null) {
                                                                            i = R.id.brick_show_text_font_x_textview;
                                                                            TextView textView15 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_x_textview);
                                                                            if (textView15 != null) {
                                                                                i = R.id.brick_show_text_font_y_textview;
                                                                                TextView textView16 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_font_y_textview);
                                                                                if (textView16 != null) {
                                                                                    return new BrickShowTextFontBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, brickLayout, textView12, spinner, textView13, textView14, textView15, textView16);
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
