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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetPointLightBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetPointLightBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetPointLightColorBValue;
    public final TextView brickSetPointLightColorGValue;
    public final TextView brickSetPointLightColorRValue;
    public final TextView brickSetPointLightIdValue;
    public final TextView brickSetPointLightIntensityValue;
    public final BrickLayout brickSetPointLightLayout;
    public final TextView brickSetPointLightPosXValue;
    public final TextView brickSetPointLightPosYValue;
    public final TextView brickSetPointLightPosZValue;
    public final TextView brickSetPointLightRangeValue;
    private final LinearLayout rootView;

    private BrickSetPointLightBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetPointLightColorBValue, TextView brickSetPointLightColorGValue, TextView brickSetPointLightColorRValue, TextView brickSetPointLightIdValue, TextView brickSetPointLightIntensityValue, BrickLayout brickSetPointLightLayout, TextView brickSetPointLightPosXValue, TextView brickSetPointLightPosYValue, TextView brickSetPointLightPosZValue, TextView brickSetPointLightRangeValue) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetPointLightColorBValue = brickSetPointLightColorBValue;
        this.brickSetPointLightColorGValue = brickSetPointLightColorGValue;
        this.brickSetPointLightColorRValue = brickSetPointLightColorRValue;
        this.brickSetPointLightIdValue = brickSetPointLightIdValue;
        this.brickSetPointLightIntensityValue = brickSetPointLightIntensityValue;
        this.brickSetPointLightLayout = brickSetPointLightLayout;
        this.brickSetPointLightPosXValue = brickSetPointLightPosXValue;
        this.brickSetPointLightPosYValue = brickSetPointLightPosYValue;
        this.brickSetPointLightPosZValue = brickSetPointLightPosZValue;
        this.brickSetPointLightRangeValue = brickSetPointLightRangeValue;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetPointLightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetPointLightBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_point_light, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetPointLightBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_point_light_color_b_value;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_point_light_color_b_value);
            if (textView != null) {
                i = R.id.brick_set_point_light_color_g_value;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_point_light_color_g_value);
                if (textView2 != null) {
                    i = R.id.brick_set_point_light_color_r_value;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_point_light_color_r_value);
                    if (textView3 != null) {
                        i = R.id.brick_set_point_light_id_value;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_point_light_id_value);
                        if (textView4 != null) {
                            i = R.id.brick_set_point_light_intensity_value;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_point_light_intensity_value);
                            if (textView5 != null) {
                                i = R.id.brick_set_point_light_layout;
                                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_point_light_layout);
                                if (brickLayout != null) {
                                    i = R.id.brick_set_point_light_pos_x_value;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_point_light_pos_x_value);
                                    if (textView6 != null) {
                                        i = R.id.brick_set_point_light_pos_y_value;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_point_light_pos_y_value);
                                        if (textView7 != null) {
                                            i = R.id.brick_set_point_light_pos_z_value;
                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_point_light_pos_z_value);
                                            if (textView8 != null) {
                                                i = R.id.brick_set_point_light_range_value;
                                                TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_point_light_range_value);
                                                if (textView9 != null) {
                                                    return new BrickSetPointLightBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, textView5, brickLayout, textView6, textView7, textView8, textView9);
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
