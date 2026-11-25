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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCreateTextLabelBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCreateTextLabelBinding implements ViewBinding {
    public final Spinner brickAlignSpinner;
    public final TextView brickBgField;
    public final CheckBox brickCheckbox;
    public final TextView brickColorField;
    public final TextView brickFontField;
    public final Spinner brickFormatSpinner;
    public final TextView brickHField;
    public final TextView brickIdField;
    public final BrickLayout brickLayout;
    public final TextView brickRadiusField;
    public final Spinner brickScrollSpinner;
    public final TextView brickSizeField;
    public final TextView brickSpacingField;
    public final TextView brickTextField;
    public final TextView brickWField;
    public final TextView brickXField;
    public final TextView brickYField;
    private final LinearLayout rootView;

    private BrickCreateTextLabelBinding(LinearLayout rootView, Spinner brickAlignSpinner, TextView brickBgField, CheckBox brickCheckbox, TextView brickColorField, TextView brickFontField, Spinner brickFormatSpinner, TextView brickHField, TextView brickIdField, BrickLayout brickLayout, TextView brickRadiusField, Spinner brickScrollSpinner, TextView brickSizeField, TextView brickSpacingField, TextView brickTextField, TextView brickWField, TextView brickXField, TextView brickYField) {
        this.rootView = rootView;
        this.brickAlignSpinner = brickAlignSpinner;
        this.brickBgField = brickBgField;
        this.brickCheckbox = brickCheckbox;
        this.brickColorField = brickColorField;
        this.brickFontField = brickFontField;
        this.brickFormatSpinner = brickFormatSpinner;
        this.brickHField = brickHField;
        this.brickIdField = brickIdField;
        this.brickLayout = brickLayout;
        this.brickRadiusField = brickRadiusField;
        this.brickScrollSpinner = brickScrollSpinner;
        this.brickSizeField = brickSizeField;
        this.brickSpacingField = brickSpacingField;
        this.brickTextField = brickTextField;
        this.brickWField = brickWField;
        this.brickXField = brickXField;
        this.brickYField = brickYField;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCreateTextLabelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCreateTextLabelBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_create_text_label, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCreateTextLabelBinding bind(View rootView) {
        int i = R.id.brick_align_spinner;
        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_align_spinner);
        if (spinner != null) {
            i = R.id.brick_bg_field;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_bg_field);
            if (textView != null) {
                i = R.id.brick_checkbox;
                CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                if (checkBox != null) {
                    i = R.id.brick_color_field;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_color_field);
                    if (textView2 != null) {
                        i = R.id.brick_font_field;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_font_field);
                        if (textView3 != null) {
                            i = R.id.brick_format_spinner;
                            Spinner spinner2 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_format_spinner);
                            if (spinner2 != null) {
                                i = R.id.brick_h_field;
                                TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_h_field);
                                if (textView4 != null) {
                                    i = R.id.brick_id_field;
                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_id_field);
                                    if (textView5 != null) {
                                        i = R.id.brick_layout;
                                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_layout);
                                        if (brickLayout != null) {
                                            i = R.id.brick_radius_field;
                                            TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_radius_field);
                                            if (textView6 != null) {
                                                i = R.id.brick_scroll_spinner;
                                                Spinner spinner3 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_scroll_spinner);
                                                if (spinner3 != null) {
                                                    i = R.id.brick_size_field;
                                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_size_field);
                                                    if (textView7 != null) {
                                                        i = R.id.brick_spacing_field;
                                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_spacing_field);
                                                        if (textView8 != null) {
                                                            i = R.id.brick_text_field;
                                                            TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_text_field);
                                                            if (textView9 != null) {
                                                                i = R.id.brick_w_field;
                                                                TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_w_field);
                                                                if (textView10 != null) {
                                                                    i = R.id.brick_x_field;
                                                                    TextView textView11 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_x_field);
                                                                    if (textView11 != null) {
                                                                        i = R.id.brick_y_field;
                                                                        TextView textView12 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_y_field);
                                                                        if (textView12 != null) {
                                                                            return new BrickCreateTextLabelBinding((LinearLayout) rootView, spinner, textView, checkBox, textView2, textView3, spinner2, textView4, textView5, brickLayout, textView6, spinner3, textView7, textView8, textView9, textView10, textView11, textView12);
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
