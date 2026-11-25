package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorFogBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorFogBinding implements ViewBinding {
    public final Button btnFogColor;
    public final EditText editFogDensity;
    public final EditText editFogEnd;
    public final EditText editFogHeight;
    public final EditText editFogStart;
    public final CheckBox fogEnabled;
    public final LinearLayout layoutFogExp;
    public final LinearLayout layoutFogLinear;
    private final LinearLayout rootView;
    public final Spinner spinnerFogType;

    private InspectorFogBinding(LinearLayout rootView, Button btnFogColor, EditText editFogDensity, EditText editFogEnd, EditText editFogHeight, EditText editFogStart, CheckBox fogEnabled, LinearLayout layoutFogExp, LinearLayout layoutFogLinear, Spinner spinnerFogType) {
        this.rootView = rootView;
        this.btnFogColor = btnFogColor;
        this.editFogDensity = editFogDensity;
        this.editFogEnd = editFogEnd;
        this.editFogHeight = editFogHeight;
        this.editFogStart = editFogStart;
        this.fogEnabled = fogEnabled;
        this.layoutFogExp = layoutFogExp;
        this.layoutFogLinear = layoutFogLinear;
        this.spinnerFogType = spinnerFogType;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorFogBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorFogBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_fog, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorFogBinding bind(View rootView) {
        int i = R.id.btn_fog_color;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_fog_color);
        if (button != null) {
            i = R.id.edit_fog_density;
            EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_fog_density);
            if (editText != null) {
                i = R.id.edit_fog_end;
                EditText editText2 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_fog_end);
                if (editText2 != null) {
                    i = R.id.edit_fog_height;
                    EditText editText3 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_fog_height);
                    if (editText3 != null) {
                        i = R.id.edit_fog_start;
                        EditText editText4 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_fog_start);
                        if (editText4 != null) {
                            i = R.id.fog_enabled;
                            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.fog_enabled);
                            if (checkBox != null) {
                                i = R.id.layout_fog_exp;
                                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.layout_fog_exp);
                                if (linearLayout != null) {
                                    i = R.id.layout_fog_linear;
                                    LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.layout_fog_linear);
                                    if (linearLayout2 != null) {
                                        i = R.id.spinner_fog_type;
                                        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.spinner_fog_type);
                                        if (spinner != null) {
                                            return new InspectorFogBinding((LinearLayout) rootView, button, editText, editText2, editText3, editText4, checkBox, linearLayout, linearLayout2, spinner);
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
