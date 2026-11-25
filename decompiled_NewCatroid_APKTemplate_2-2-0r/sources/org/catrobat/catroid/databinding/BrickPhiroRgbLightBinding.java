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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPhiroRgbLightBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPhiroRgbLightBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickPhiroRgbLedActionBlueEditText;
    public final TextView brickPhiroRgbLedActionGreenEditText;
    public final TextView brickPhiroRgbLedActionLabel;
    public final TextView brickPhiroRgbLedActionRedEditText;
    public final TextView brickPhiroRgbLedBlueTextView;
    public final TextView brickPhiroRgbLedGreenTextView;
    public final BrickLayout brickPhiroRgbLedLayout;
    public final TextView brickPhiroRgbLedRedTextView;
    public final Spinner brickPhiroRgbLightSpinner;
    private final LinearLayout rootView;

    private BrickPhiroRgbLightBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickPhiroRgbLedActionBlueEditText, TextView brickPhiroRgbLedActionGreenEditText, TextView brickPhiroRgbLedActionLabel, TextView brickPhiroRgbLedActionRedEditText, TextView brickPhiroRgbLedBlueTextView, TextView brickPhiroRgbLedGreenTextView, BrickLayout brickPhiroRgbLedLayout, TextView brickPhiroRgbLedRedTextView, Spinner brickPhiroRgbLightSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPhiroRgbLedActionBlueEditText = brickPhiroRgbLedActionBlueEditText;
        this.brickPhiroRgbLedActionGreenEditText = brickPhiroRgbLedActionGreenEditText;
        this.brickPhiroRgbLedActionLabel = brickPhiroRgbLedActionLabel;
        this.brickPhiroRgbLedActionRedEditText = brickPhiroRgbLedActionRedEditText;
        this.brickPhiroRgbLedBlueTextView = brickPhiroRgbLedBlueTextView;
        this.brickPhiroRgbLedGreenTextView = brickPhiroRgbLedGreenTextView;
        this.brickPhiroRgbLedLayout = brickPhiroRgbLedLayout;
        this.brickPhiroRgbLedRedTextView = brickPhiroRgbLedRedTextView;
        this.brickPhiroRgbLightSpinner = brickPhiroRgbLightSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPhiroRgbLightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPhiroRgbLightBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_phiro_rgb_light, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPhiroRgbLightBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_phiro_rgb_led_action_blue_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_phiro_rgb_led_action_blue_edit_text);
            if (textView != null) {
                i = R.id.brick_phiro_rgb_led_action_green_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_phiro_rgb_led_action_green_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_phiro_rgb_led_action_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_phiro_rgb_led_action_label);
                    if (textView3 != null) {
                        i = R.id.brick_phiro_rgb_led_action_red_edit_text;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_phiro_rgb_led_action_red_edit_text);
                        if (textView4 != null) {
                            i = R.id.brick_phiro_rgb_led_blue_text_view;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_phiro_rgb_led_blue_text_view);
                            if (textView5 != null) {
                                i = R.id.brick_phiro_rgb_led_green_text_view;
                                TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_phiro_rgb_led_green_text_view);
                                if (textView6 != null) {
                                    i = R.id.brick_phiro_rgb_led_layout;
                                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_phiro_rgb_led_layout);
                                    if (brickLayout != null) {
                                        i = R.id.brick_phiro_rgb_led_red_text_view;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_phiro_rgb_led_red_text_view);
                                        if (textView7 != null) {
                                            i = R.id.brick_phiro_rgb_light_spinner;
                                            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_phiro_rgb_light_spinner);
                                            if (spinner != null) {
                                                return new BrickPhiroRgbLightBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, textView5, textView6, brickLayout, textView7, spinner);
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
