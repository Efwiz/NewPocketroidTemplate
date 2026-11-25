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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickShowTextRotationBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickShowTextRotationBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickShowTextRotationAtLabel;
    public final TextView brickShowTextRotationColorSizeAndTextview;
    public final TextView brickShowTextRotationEditColor;
    public final TextView brickShowTextRotationEditFont;
    public final TextView brickShowTextRotationEditName;
    public final TextView brickShowTextRotationEditRotation;
    public final TextView brickShowTextRotationEditSize;
    public final TextView brickShowTextRotationEditText;
    public final TextView brickShowTextRotationEditX;
    public final TextView brickShowTextRotationEditY;
    public final TextView brickShowTextRotationLabel;
    public final TextView brickShowTextRotationLabel2;
    public final BrickLayout brickShowTextRotationLayout;
    public final TextView brickShowTextRotationPercentTextview;
    public final Spinner brickShowTextRotationSpinner;
    public final TextView brickShowTextRotationSpinnerText;
    public final TextView brickShowTextRotationWithTextview;
    public final TextView brickShowTextRotationXTextview;
    public final TextView brickShowTextRotationYTextview;
    private final LinearLayout rootView;

    private BrickShowTextRotationBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickShowTextRotationAtLabel, TextView brickShowTextRotationColorSizeAndTextview, TextView brickShowTextRotationEditColor, TextView brickShowTextRotationEditFont, TextView brickShowTextRotationEditName, TextView brickShowTextRotationEditRotation, TextView brickShowTextRotationEditSize, TextView brickShowTextRotationEditText, TextView brickShowTextRotationEditX, TextView brickShowTextRotationEditY, TextView brickShowTextRotationLabel, TextView brickShowTextRotationLabel2, BrickLayout brickShowTextRotationLayout, TextView brickShowTextRotationPercentTextview, Spinner brickShowTextRotationSpinner, TextView brickShowTextRotationSpinnerText, TextView brickShowTextRotationWithTextview, TextView brickShowTextRotationXTextview, TextView brickShowTextRotationYTextview) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickShowTextRotationAtLabel = brickShowTextRotationAtLabel;
        this.brickShowTextRotationColorSizeAndTextview = brickShowTextRotationColorSizeAndTextview;
        this.brickShowTextRotationEditColor = brickShowTextRotationEditColor;
        this.brickShowTextRotationEditFont = brickShowTextRotationEditFont;
        this.brickShowTextRotationEditName = brickShowTextRotationEditName;
        this.brickShowTextRotationEditRotation = brickShowTextRotationEditRotation;
        this.brickShowTextRotationEditSize = brickShowTextRotationEditSize;
        this.brickShowTextRotationEditText = brickShowTextRotationEditText;
        this.brickShowTextRotationEditX = brickShowTextRotationEditX;
        this.brickShowTextRotationEditY = brickShowTextRotationEditY;
        this.brickShowTextRotationLabel = brickShowTextRotationLabel;
        this.brickShowTextRotationLabel2 = brickShowTextRotationLabel2;
        this.brickShowTextRotationLayout = brickShowTextRotationLayout;
        this.brickShowTextRotationPercentTextview = brickShowTextRotationPercentTextview;
        this.brickShowTextRotationSpinner = brickShowTextRotationSpinner;
        this.brickShowTextRotationSpinnerText = brickShowTextRotationSpinnerText;
        this.brickShowTextRotationWithTextview = brickShowTextRotationWithTextview;
        this.brickShowTextRotationXTextview = brickShowTextRotationXTextview;
        this.brickShowTextRotationYTextview = brickShowTextRotationYTextview;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickShowTextRotationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickShowTextRotationBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_show_text_rotation, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickShowTextRotationBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_show_text_rotation_at_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_at_label);
            if (textView != null) {
                i = R.id.brick_show_text_rotation_color_size_and_textview;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_color_size_and_textview);
                if (textView2 != null) {
                    i = R.id.brick_show_text_rotation_edit_color;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_edit_color);
                    if (textView3 != null) {
                        i = R.id.brick_show_text_rotation_edit_font;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_edit_font);
                        if (textView4 != null) {
                            i = R.id.brick_show_text_rotation_edit_name;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_edit_name);
                            if (textView5 != null) {
                                i = R.id.brick_show_text_rotation_edit_rotation;
                                TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_edit_rotation);
                                if (textView6 != null) {
                                    i = R.id.brick_show_text_rotation_edit_size;
                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_edit_size);
                                    if (textView7 != null) {
                                        i = R.id.brick_show_text_rotation_edit_text;
                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_edit_text);
                                        if (textView8 != null) {
                                            i = R.id.brick_show_text_rotation_edit_x;
                                            TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_edit_x);
                                            if (textView9 != null) {
                                                i = R.id.brick_show_text_rotation_edit_y;
                                                TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_edit_y);
                                                if (textView10 != null) {
                                                    i = R.id.brick_show_text_rotation_label;
                                                    TextView textView11 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_label);
                                                    if (textView11 != null) {
                                                        i = R.id.brick_show_text_rotation_label2;
                                                        TextView textView12 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_label2);
                                                        if (textView12 != null) {
                                                            i = R.id.brick_show_text_rotation_layout;
                                                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_layout);
                                                            if (brickLayout != null) {
                                                                i = R.id.brick_show_text_rotation_percent_textview;
                                                                TextView textView13 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_percent_textview);
                                                                if (textView13 != null) {
                                                                    i = R.id.brick_show_text_rotation_spinner;
                                                                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_spinner);
                                                                    if (spinner != null) {
                                                                        i = R.id.brick_show_text_rotation_spinner_text;
                                                                        TextView textView14 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_spinner_text);
                                                                        if (textView14 != null) {
                                                                            i = R.id.brick_show_text_rotation_with_textview;
                                                                            TextView textView15 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_with_textview);
                                                                            if (textView15 != null) {
                                                                                i = R.id.brick_show_text_rotation_x_textview;
                                                                                TextView textView16 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_x_textview);
                                                                                if (textView16 != null) {
                                                                                    i = R.id.brick_show_text_rotation_y_textview;
                                                                                    TextView textView17 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_text_rotation_y_textview);
                                                                                    if (textView17 != null) {
                                                                                        return new BrickShowTextRotationBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, textView12, brickLayout, textView13, spinner, textView14, textView15, textView16, textView17);
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
