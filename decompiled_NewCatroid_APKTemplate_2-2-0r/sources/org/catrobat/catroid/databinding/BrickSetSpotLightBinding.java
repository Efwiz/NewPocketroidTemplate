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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetSpotLightBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetSpotLightBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetSpotLightAngleValue;
    public final TextView brickSetSpotLightColorBValue;
    public final TextView brickSetSpotLightColorGValue;
    public final TextView brickSetSpotLightColorRValue;
    public final TextView brickSetSpotLightDirXValue;
    public final TextView brickSetSpotLightDirYValue;
    public final TextView brickSetSpotLightDirZValue;
    public final TextView brickSetSpotLightExponentValue;
    public final TextView brickSetSpotLightIdValue;
    public final TextView brickSetSpotLightIntensityValue;
    public final BrickLayout brickSetSpotLightLayout;
    public final TextView brickSetSpotLightPosXValue;
    public final TextView brickSetSpotLightPosYValue;
    public final TextView brickSetSpotLightPosZValue;
    public final TextView brickSetSpotLightRangeValue;
    private final LinearLayout rootView;

    private BrickSetSpotLightBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetSpotLightAngleValue, TextView brickSetSpotLightColorBValue, TextView brickSetSpotLightColorGValue, TextView brickSetSpotLightColorRValue, TextView brickSetSpotLightDirXValue, TextView brickSetSpotLightDirYValue, TextView brickSetSpotLightDirZValue, TextView brickSetSpotLightExponentValue, TextView brickSetSpotLightIdValue, TextView brickSetSpotLightIntensityValue, BrickLayout brickSetSpotLightLayout, TextView brickSetSpotLightPosXValue, TextView brickSetSpotLightPosYValue, TextView brickSetSpotLightPosZValue, TextView brickSetSpotLightRangeValue) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetSpotLightAngleValue = brickSetSpotLightAngleValue;
        this.brickSetSpotLightColorBValue = brickSetSpotLightColorBValue;
        this.brickSetSpotLightColorGValue = brickSetSpotLightColorGValue;
        this.brickSetSpotLightColorRValue = brickSetSpotLightColorRValue;
        this.brickSetSpotLightDirXValue = brickSetSpotLightDirXValue;
        this.brickSetSpotLightDirYValue = brickSetSpotLightDirYValue;
        this.brickSetSpotLightDirZValue = brickSetSpotLightDirZValue;
        this.brickSetSpotLightExponentValue = brickSetSpotLightExponentValue;
        this.brickSetSpotLightIdValue = brickSetSpotLightIdValue;
        this.brickSetSpotLightIntensityValue = brickSetSpotLightIntensityValue;
        this.brickSetSpotLightLayout = brickSetSpotLightLayout;
        this.brickSetSpotLightPosXValue = brickSetSpotLightPosXValue;
        this.brickSetSpotLightPosYValue = brickSetSpotLightPosYValue;
        this.brickSetSpotLightPosZValue = brickSetSpotLightPosZValue;
        this.brickSetSpotLightRangeValue = brickSetSpotLightRangeValue;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetSpotLightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetSpotLightBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_spot_light, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetSpotLightBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_spot_light_angle_value;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_spot_light_angle_value);
            if (textView != null) {
                i = R.id.brick_set_spot_light_color_b_value;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_spot_light_color_b_value);
                if (textView2 != null) {
                    i = R.id.brick_set_spot_light_color_g_value;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_spot_light_color_g_value);
                    if (textView3 != null) {
                        i = R.id.brick_set_spot_light_color_r_value;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_spot_light_color_r_value);
                        if (textView4 != null) {
                            i = R.id.brick_set_spot_light_dir_x_value;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_spot_light_dir_x_value);
                            if (textView5 != null) {
                                i = R.id.brick_set_spot_light_dir_y_value;
                                TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_spot_light_dir_y_value);
                                if (textView6 != null) {
                                    i = R.id.brick_set_spot_light_dir_z_value;
                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_spot_light_dir_z_value);
                                    if (textView7 != null) {
                                        i = R.id.brick_set_spot_light_exponent_value;
                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_spot_light_exponent_value);
                                        if (textView8 != null) {
                                            i = R.id.brick_set_spot_light_id_value;
                                            TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_spot_light_id_value);
                                            if (textView9 != null) {
                                                i = R.id.brick_set_spot_light_intensity_value;
                                                TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_spot_light_intensity_value);
                                                if (textView10 != null) {
                                                    i = R.id.brick_set_spot_light_layout;
                                                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_spot_light_layout);
                                                    if (brickLayout != null) {
                                                        i = R.id.brick_set_spot_light_pos_x_value;
                                                        TextView textView11 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_spot_light_pos_x_value);
                                                        if (textView11 != null) {
                                                            i = R.id.brick_set_spot_light_pos_y_value;
                                                            TextView textView12 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_spot_light_pos_y_value);
                                                            if (textView12 != null) {
                                                                i = R.id.brick_set_spot_light_pos_z_value;
                                                                TextView textView13 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_spot_light_pos_z_value);
                                                                if (textView13 != null) {
                                                                    i = R.id.brick_set_spot_light_range_value;
                                                                    TextView textView14 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_spot_light_range_value);
                                                                    if (textView14 != null) {
                                                                        return new BrickSetSpotLightBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, brickLayout, textView11, textView12, textView13, textView14);
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
