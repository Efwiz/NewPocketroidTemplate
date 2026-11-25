package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogSceneSettingsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogSceneSettingsBinding implements ViewBinding {
    public final Button btnApplyPerformance;
    public final Button btnApplyShadows;
    public final ImageButton btnClearSkybox;
    public final Button btnSelectSkybox;
    public final Button btnSkyColor;
    public final CheckBox checkboxCsm;
    public final EditText editBones;
    public final EditText editCsmFactor;
    public final EditText editDirLights;
    public final EditText editPointLights;
    public final EditText editShadowDistance;
    public final EditText editSpotLights;
    public final LinearLayout layoutCsmFactor;
    private final ScrollView rootView;
    public final SeekBar seekbarAmbientLight;
    public final Spinner spinnerShadowResolution;
    public final TextView textAmbientValue;
    public final TextView textCsmHint;
    public final TextView textSkyboxPath;

    private DialogSceneSettingsBinding(ScrollView rootView, Button btnApplyPerformance, Button btnApplyShadows, ImageButton btnClearSkybox, Button btnSelectSkybox, Button btnSkyColor, CheckBox checkboxCsm, EditText editBones, EditText editCsmFactor, EditText editDirLights, EditText editPointLights, EditText editShadowDistance, EditText editSpotLights, LinearLayout layoutCsmFactor, SeekBar seekbarAmbientLight, Spinner spinnerShadowResolution, TextView textAmbientValue, TextView textCsmHint, TextView textSkyboxPath) {
        this.rootView = rootView;
        this.btnApplyPerformance = btnApplyPerformance;
        this.btnApplyShadows = btnApplyShadows;
        this.btnClearSkybox = btnClearSkybox;
        this.btnSelectSkybox = btnSelectSkybox;
        this.btnSkyColor = btnSkyColor;
        this.checkboxCsm = checkboxCsm;
        this.editBones = editBones;
        this.editCsmFactor = editCsmFactor;
        this.editDirLights = editDirLights;
        this.editPointLights = editPointLights;
        this.editShadowDistance = editShadowDistance;
        this.editSpotLights = editSpotLights;
        this.layoutCsmFactor = layoutCsmFactor;
        this.seekbarAmbientLight = seekbarAmbientLight;
        this.spinnerShadowResolution = spinnerShadowResolution;
        this.textAmbientValue = textAmbientValue;
        this.textCsmHint = textCsmHint;
        this.textSkyboxPath = textSkyboxPath;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogSceneSettingsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogSceneSettingsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_scene_settings, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogSceneSettingsBinding bind(View rootView) {
        int i = R.id.btn_apply_performance;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_apply_performance);
        if (button != null) {
            i = R.id.btn_apply_shadows;
            Button button2 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_apply_shadows);
            if (button2 != null) {
                i = R.id.btn_clear_skybox;
                ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btn_clear_skybox);
                if (imageButton != null) {
                    i = R.id.btn_select_skybox;
                    Button button3 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_select_skybox);
                    if (button3 != null) {
                        i = R.id.btn_sky_color;
                        Button button4 = (Button) ViewBindings.findChildViewById(rootView, R.id.btn_sky_color);
                        if (button4 != null) {
                            i = R.id.checkbox_csm;
                            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.checkbox_csm);
                            if (checkBox != null) {
                                i = R.id.edit_bones;
                                EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_bones);
                                if (editText != null) {
                                    i = R.id.edit_csm_factor;
                                    EditText editText2 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_csm_factor);
                                    if (editText2 != null) {
                                        i = R.id.edit_dir_lights;
                                        EditText editText3 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_dir_lights);
                                        if (editText3 != null) {
                                            i = R.id.edit_point_lights;
                                            EditText editText4 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_point_lights);
                                            if (editText4 != null) {
                                                i = R.id.edit_shadow_distance;
                                                EditText editText5 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_shadow_distance);
                                                if (editText5 != null) {
                                                    i = R.id.edit_spot_lights;
                                                    EditText editText6 = (EditText) ViewBindings.findChildViewById(rootView, R.id.edit_spot_lights);
                                                    if (editText6 != null) {
                                                        i = R.id.layout_csm_factor;
                                                        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.layout_csm_factor);
                                                        if (linearLayout != null) {
                                                            i = R.id.seekbar_ambient_light;
                                                            SeekBar seekBar = (SeekBar) ViewBindings.findChildViewById(rootView, R.id.seekbar_ambient_light);
                                                            if (seekBar != null) {
                                                                i = R.id.spinner_shadow_resolution;
                                                                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.spinner_shadow_resolution);
                                                                if (spinner != null) {
                                                                    i = R.id.text_ambient_value;
                                                                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.text_ambient_value);
                                                                    if (textView != null) {
                                                                        i = R.id.text_csm_hint;
                                                                        TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.text_csm_hint);
                                                                        if (textView2 != null) {
                                                                            i = R.id.text_skybox_path;
                                                                            TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.text_skybox_path);
                                                                            if (textView3 != null) {
                                                                                return new DialogSceneSettingsBinding((ScrollView) rootView, button, button2, imageButton, button3, button4, checkBox, editText, editText2, editText3, editText4, editText5, editText6, linearLayout, seekBar, spinner, textView, textView2, textView3);
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
