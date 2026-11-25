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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCreateButtonBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCreateButtonBinding implements ViewBinding {
    public final TextView brickBgField;
    public final CheckBox brickCheckbox;
    public final TextView brickColorField;
    public final TextView brickHField;
    public final TextView brickIdField;
    public final BrickLayout brickLayout;
    public final TextView brickRadiusField;
    public final TextView brickSizeField;
    public final TextView brickTextField;
    public final TextView brickWField;
    public final TextView brickXField;
    public final TextView brickYField;
    private final LinearLayout rootView;

    private BrickCreateButtonBinding(LinearLayout rootView, TextView brickBgField, CheckBox brickCheckbox, TextView brickColorField, TextView brickHField, TextView brickIdField, BrickLayout brickLayout, TextView brickRadiusField, TextView brickSizeField, TextView brickTextField, TextView brickWField, TextView brickXField, TextView brickYField) {
        this.rootView = rootView;
        this.brickBgField = brickBgField;
        this.brickCheckbox = brickCheckbox;
        this.brickColorField = brickColorField;
        this.brickHField = brickHField;
        this.brickIdField = brickIdField;
        this.brickLayout = brickLayout;
        this.brickRadiusField = brickRadiusField;
        this.brickSizeField = brickSizeField;
        this.brickTextField = brickTextField;
        this.brickWField = brickWField;
        this.brickXField = brickXField;
        this.brickYField = brickYField;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCreateButtonBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCreateButtonBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_create_button, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCreateButtonBinding bind(View rootView) {
        int i = R.id.brick_bg_field;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_bg_field);
        if (textView != null) {
            i = R.id.brick_checkbox;
            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
            if (checkBox != null) {
                i = R.id.brick_color_field;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_color_field);
                if (textView2 != null) {
                    i = R.id.brick_h_field;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_h_field);
                    if (textView3 != null) {
                        i = R.id.brick_id_field;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_id_field);
                        if (textView4 != null) {
                            i = R.id.brick_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_layout);
                            if (brickLayout != null) {
                                i = R.id.brick_radius_field;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_radius_field);
                                if (textView5 != null) {
                                    i = R.id.brick_size_field;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_size_field);
                                    if (textView6 != null) {
                                        i = R.id.brick_text_field;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_text_field);
                                        if (textView7 != null) {
                                            i = R.id.brick_w_field;
                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_w_field);
                                            if (textView8 != null) {
                                                i = R.id.brick_x_field;
                                                TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_x_field);
                                                if (textView9 != null) {
                                                    i = R.id.brick_y_field;
                                                    TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_y_field);
                                                    if (textView10 != null) {
                                                        return new BrickCreateButtonBinding((LinearLayout) rootView, textView, checkBox, textView2, textView3, textView4, brickLayout, textView5, textView6, textView7, textView8, textView9, textView10);
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
