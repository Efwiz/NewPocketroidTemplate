package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorLightBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorLightBinding implements ViewBinding {
    public final Button btnLightColor;
    public final EditText editLightAngle;
    public final EditText editLightExponent;
    public final EditText editLightIntensity;
    public final EditText editLightRange;
    public final LinearLayout layoutLightRange;
    public final LinearLayout layoutLightSpot;
    private final LinearLayout rootView;
    public final Spinner spinnerLightType;

    private InspectorLightBinding(LinearLayout rootView, Button btnLightColor, EditText editLightAngle, EditText editLightExponent, EditText editLightIntensity, EditText editLightRange, LinearLayout layoutLightRange, LinearLayout layoutLightSpot, Spinner spinnerLightType) {
        this.rootView = rootView;
        this.btnLightColor = btnLightColor;
        this.editLightAngle = editLightAngle;
        this.editLightExponent = editLightExponent;
        this.editLightIntensity = editLightIntensity;
        this.editLightRange = editLightRange;
        this.layoutLightRange = layoutLightRange;
        this.layoutLightSpot = layoutLightSpot;
        this.spinnerLightType = spinnerLightType;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorLightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorLightBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_light, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorLightBinding bind(View rootView) {
        int i = R.id.btn_light_color;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_light_color);
        if (button != null) {
            i = R.id.edit_light_angle;
            EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_light_angle);
            if (editText != null) {
                i = R.id.edit_light_exponent;
                EditText editText2 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_light_exponent);
                if (editText2 != null) {
                    i = R.id.edit_light_intensity;
                    EditText editText3 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_light_intensity);
                    if (editText3 != null) {
                        i = R.id.edit_light_range;
                        EditText editText4 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_light_range);
                        if (editText4 != null) {
                            i = R.id.layout_light_range;
                            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.layout_light_range);
                            if (linearLayout != null) {
                                i = R.id.layout_light_spot;
                                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.layout_light_spot);
                                if (linearLayout2 != null) {
                                    i = R.id.spinner_light_type;
                                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.spinner_light_type);
                                    if (spinner != null) {
                                        return new InspectorLightBinding((LinearLayout) rootView, button, editText, editText2, editText3, editText4, linearLayout, linearLayout2, spinner);
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
