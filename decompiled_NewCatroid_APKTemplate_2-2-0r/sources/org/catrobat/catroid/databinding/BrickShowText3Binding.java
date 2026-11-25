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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickShowText3Binding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickShowText3Binding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickShowText3Layout;
    public final TextView brickShowTextAtLabel;
    public final TextView brickShowTextColorSizeAndTextview;
    public final TextView brickShowTextEditColor;
    public final TextView brickShowTextEditName;
    public final TextView brickShowTextEditSize;
    public final TextView brickShowTextEditText;
    public final TextView brickShowTextEditX;
    public final TextView brickShowTextEditY;
    public final TextView brickShowTextLabel;
    public final TextView brickShowTextLabel2;
    public final TextView brickShowTextPercentTextview;
    public final Spinner brickShowTextSpinner;
    public final TextView brickShowTextSpinnerText;
    public final TextView brickShowTextWithTextview;
    public final TextView brickShowTextXTextview;
    public final TextView brickShowTextYTextview;
    private final LinearLayout rootView;

    private BrickShowText3Binding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickShowText3Layout, TextView brickShowTextAtLabel, TextView brickShowTextColorSizeAndTextview, TextView brickShowTextEditColor, TextView brickShowTextEditName, TextView brickShowTextEditSize, TextView brickShowTextEditText, TextView brickShowTextEditX, TextView brickShowTextEditY, TextView brickShowTextLabel, TextView brickShowTextLabel2, TextView brickShowTextPercentTextview, Spinner brickShowTextSpinner, TextView brickShowTextSpinnerText, TextView brickShowTextWithTextview, TextView brickShowTextXTextview, TextView brickShowTextYTextview) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickShowText3Layout = brickShowText3Layout;
        this.brickShowTextAtLabel = brickShowTextAtLabel;
        this.brickShowTextColorSizeAndTextview = brickShowTextColorSizeAndTextview;
        this.brickShowTextEditColor = brickShowTextEditColor;
        this.brickShowTextEditName = brickShowTextEditName;
        this.brickShowTextEditSize = brickShowTextEditSize;
        this.brickShowTextEditText = brickShowTextEditText;
        this.brickShowTextEditX = brickShowTextEditX;
        this.brickShowTextEditY = brickShowTextEditY;
        this.brickShowTextLabel = brickShowTextLabel;
        this.brickShowTextLabel2 = brickShowTextLabel2;
        this.brickShowTextPercentTextview = brickShowTextPercentTextview;
        this.brickShowTextSpinner = brickShowTextSpinner;
        this.brickShowTextSpinnerText = brickShowTextSpinnerText;
        this.brickShowTextWithTextview = brickShowTextWithTextview;
        this.brickShowTextXTextview = brickShowTextXTextview;
        this.brickShowTextYTextview = brickShowTextYTextview;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickShowText3Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickShowText3Binding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_show_text3, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickShowText3Binding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_show_text3_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_show_text3_layout);
            if (brickLayout != null) {
                i = R.id.brick_show_text_at_label;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_at_label);
                if (textView != null) {
                    i = R.id.brick_show_text_color_size_and_textview;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_color_size_and_textview);
                    if (textView2 != null) {
                        i = R.id.brick_show_text_edit_color;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_edit_color);
                        if (textView3 != null) {
                            i = R.id.brick_show_text_edit_name;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_edit_name);
                            if (textView4 != null) {
                                i = R.id.brick_show_text_edit_size;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_edit_size);
                                if (textView5 != null) {
                                    i = R.id.brick_show_text_edit_text;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_edit_text);
                                    if (textView6 != null) {
                                        i = R.id.brick_show_text_edit_x;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_edit_x);
                                        if (textView7 != null) {
                                            i = R.id.brick_show_text_edit_y;
                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_edit_y);
                                            if (textView8 != null) {
                                                i = R.id.brick_show_text_label;
                                                TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_label);
                                                if (textView9 != null) {
                                                    i = R.id.brick_show_text_label2;
                                                    TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_label2);
                                                    if (textView10 != null) {
                                                        i = R.id.brick_show_text_percent_textview;
                                                        TextView textView11 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_percent_textview);
                                                        if (textView11 != null) {
                                                            i = R.id.brick_show_text_spinner;
                                                            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_spinner);
                                                            if (spinner != null) {
                                                                i = R.id.brick_show_text_spinner_text;
                                                                TextView textView12 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_spinner_text);
                                                                if (textView12 != null) {
                                                                    i = R.id.brick_show_text_with_textview;
                                                                    TextView textView13 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_with_textview);
                                                                    if (textView13 != null) {
                                                                        i = R.id.brick_show_text_x_textview;
                                                                        TextView textView14 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_x_textview);
                                                                        if (textView14 != null) {
                                                                            i = R.id.brick_show_text_y_textview;
                                                                            TextView textView15 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_y_textview);
                                                                            if (textView15 != null) {
                                                                                return new BrickShowText3Binding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, spinner, textView12, textView13, textView14, textView15);
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
