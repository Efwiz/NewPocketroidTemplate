package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogApkBuildSettingsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogApkBuildSettingsBinding implements ViewBinding {
    public final MaterialButton btnBack;
    public final MaterialButton btnCancel;
    public final MaterialButton btnGenerateAndExportKeystore;
    public final MaterialButton btnLandscape;
    public final MaterialButton btnNext;
    public final MaterialButton btnPortrait;
    public final MaterialButton btnSelectKeystore;
    public final MaterialButton btnSensor;
    public final View buttonDivider;
    public final CheckBox cbArm64;
    public final CheckBox cbArmv7;
    public final ChipGroup cgDetectedPermissions;
    public final TextInputEditText etBuildAppName;
    public final TextInputEditText etBuildPackageName;
    public final AutoCompleteTextView etBuildTemplate;
    public final TextInputEditText etBuildVersionCode;
    public final TextInputEditText etBuildVersionName;
    public final TextInputEditText etGenAlias;
    public final TextInputEditText etGenOwner;
    public final TextInputEditText etGenPass;
    public final TextInputEditText etKeystoreAlias;
    public final TextInputEditText etKeystorePass;
    public final LinearLayout llCustomKeystoreFields;
    public final LinearLayout llGenerateKeystoreFields;
    public final LinearLayout llManualPermissions;
    public final ConstraintLayout navigationContainer;
    public final NestedScrollView nestedScrollView;
    public final RadioButton rbAutoDebug;
    public final RadioButton rbCustomKeystore;
    public final RadioButton rbGenerateNew;
    public final RadioGroup rgKeystore;
    private final MaterialCardView rootView;
    public final LinearLayout step1Container;
    public final LinearLayout step2Container;
    public final LinearLayout step3Container;
    public final LinearLayout step4Container;
    public final ProgressBar stepProgressIndicator;
    public final FrameLayout stepsContainer;
    public final SwitchMaterial switchCompiler;
    public final SwitchMaterial switchCustomizePermissions;
    public final SwitchMaterial switchMkit;
    public final SwitchMaterial switchMnn;
    public final SwitchMaterial switchOnnx;
    public final SwitchMaterial switchPermAudio;
    public final SwitchMaterial switchPermBackground;
    public final SwitchMaterial switchPermBluetooth;
    public final SwitchMaterial switchPermCamera;
    public final SwitchMaterial switchPermInternet;
    public final SwitchMaterial switchPermLocation;
    public final SwitchMaterial switchPermNfc;
    public final SwitchMaterial switchPermOverlay;
    public final SwitchMaterial switchPermStorage;
    public final SwitchMaterial switchPermVibrate;
    public final SwitchMaterial switchPython;
    public final SwitchMaterial switchQemu;
    public final TextInputLayout tilAppName;
    public final TextInputLayout tilPackageName;
    public final TextInputLayout tilTemplates;
    public final TextInputLayout tilVersionCode;
    public final TextInputLayout tilVersionName;
    public final MaterialButtonToggleGroup toggleGroupOrientation;
    public final TextView tvDialogTitle;
    public final TextView tvSelectedKeystorePath;
    public final TextView tvStepSubtitle;

    private DialogApkBuildSettingsBinding(MaterialCardView rootView, MaterialButton btnBack, MaterialButton btnCancel, MaterialButton btnGenerateAndExportKeystore, MaterialButton btnLandscape, MaterialButton btnNext, MaterialButton btnPortrait, MaterialButton btnSelectKeystore, MaterialButton btnSensor, View buttonDivider, CheckBox cbArm64, CheckBox cbArmv7, ChipGroup cgDetectedPermissions, TextInputEditText etBuildAppName, TextInputEditText etBuildPackageName, AutoCompleteTextView etBuildTemplate, TextInputEditText etBuildVersionCode, TextInputEditText etBuildVersionName, TextInputEditText etGenAlias, TextInputEditText etGenOwner, TextInputEditText etGenPass, TextInputEditText etKeystoreAlias, TextInputEditText etKeystorePass, LinearLayout llCustomKeystoreFields, LinearLayout llGenerateKeystoreFields, LinearLayout llManualPermissions, ConstraintLayout navigationContainer, NestedScrollView nestedScrollView, RadioButton rbAutoDebug, RadioButton rbCustomKeystore, RadioButton rbGenerateNew, RadioGroup rgKeystore, LinearLayout step1Container, LinearLayout step2Container, LinearLayout step3Container, LinearLayout step4Container, ProgressBar stepProgressIndicator, FrameLayout stepsContainer, SwitchMaterial switchCompiler, SwitchMaterial switchCustomizePermissions, SwitchMaterial switchMkit, SwitchMaterial switchMnn, SwitchMaterial switchOnnx, SwitchMaterial switchPermAudio, SwitchMaterial switchPermBackground, SwitchMaterial switchPermBluetooth, SwitchMaterial switchPermCamera, SwitchMaterial switchPermInternet, SwitchMaterial switchPermLocation, SwitchMaterial switchPermNfc, SwitchMaterial switchPermOverlay, SwitchMaterial switchPermStorage, SwitchMaterial switchPermVibrate, SwitchMaterial switchPython, SwitchMaterial switchQemu, TextInputLayout tilAppName, TextInputLayout tilPackageName, TextInputLayout tilTemplates, TextInputLayout tilVersionCode, TextInputLayout tilVersionName, MaterialButtonToggleGroup toggleGroupOrientation, TextView tvDialogTitle, TextView tvSelectedKeystorePath, TextView tvStepSubtitle) {
        this.rootView = rootView;
        this.btnBack = btnBack;
        this.btnCancel = btnCancel;
        this.btnGenerateAndExportKeystore = btnGenerateAndExportKeystore;
        this.btnLandscape = btnLandscape;
        this.btnNext = btnNext;
        this.btnPortrait = btnPortrait;
        this.btnSelectKeystore = btnSelectKeystore;
        this.btnSensor = btnSensor;
        this.buttonDivider = buttonDivider;
        this.cbArm64 = cbArm64;
        this.cbArmv7 = cbArmv7;
        this.cgDetectedPermissions = cgDetectedPermissions;
        this.etBuildAppName = etBuildAppName;
        this.etBuildPackageName = etBuildPackageName;
        this.etBuildTemplate = etBuildTemplate;
        this.etBuildVersionCode = etBuildVersionCode;
        this.etBuildVersionName = etBuildVersionName;
        this.etGenAlias = etGenAlias;
        this.etGenOwner = etGenOwner;
        this.etGenPass = etGenPass;
        this.etKeystoreAlias = etKeystoreAlias;
        this.etKeystorePass = etKeystorePass;
        this.llCustomKeystoreFields = llCustomKeystoreFields;
        this.llGenerateKeystoreFields = llGenerateKeystoreFields;
        this.llManualPermissions = llManualPermissions;
        this.navigationContainer = navigationContainer;
        this.nestedScrollView = nestedScrollView;
        this.rbAutoDebug = rbAutoDebug;
        this.rbCustomKeystore = rbCustomKeystore;
        this.rbGenerateNew = rbGenerateNew;
        this.rgKeystore = rgKeystore;
        this.step1Container = step1Container;
        this.step2Container = step2Container;
        this.step3Container = step3Container;
        this.step4Container = step4Container;
        this.stepProgressIndicator = stepProgressIndicator;
        this.stepsContainer = stepsContainer;
        this.switchCompiler = switchCompiler;
        this.switchCustomizePermissions = switchCustomizePermissions;
        this.switchMkit = switchMkit;
        this.switchMnn = switchMnn;
        this.switchOnnx = switchOnnx;
        this.switchPermAudio = switchPermAudio;
        this.switchPermBackground = switchPermBackground;
        this.switchPermBluetooth = switchPermBluetooth;
        this.switchPermCamera = switchPermCamera;
        this.switchPermInternet = switchPermInternet;
        this.switchPermLocation = switchPermLocation;
        this.switchPermNfc = switchPermNfc;
        this.switchPermOverlay = switchPermOverlay;
        this.switchPermStorage = switchPermStorage;
        this.switchPermVibrate = switchPermVibrate;
        this.switchPython = switchPython;
        this.switchQemu = switchQemu;
        this.tilAppName = tilAppName;
        this.tilPackageName = tilPackageName;
        this.tilTemplates = tilTemplates;
        this.tilVersionCode = tilVersionCode;
        this.tilVersionName = tilVersionName;
        this.toggleGroupOrientation = toggleGroupOrientation;
        this.tvDialogTitle = tvDialogTitle;
        this.tvSelectedKeystorePath = tvSelectedKeystorePath;
        this.tvStepSubtitle = tvStepSubtitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public MaterialCardView getRoot() {
        return this.rootView;
    }

    public static DialogApkBuildSettingsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogApkBuildSettingsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_apk_build_settings, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogApkBuildSettingsBinding bind(View rootView) {
        int i = R.id.btnBack;
        MaterialButton materialButton = (MaterialButton) ViewBindings.findChildViewById(rootView, R.id.btnBack);
        if (materialButton != null) {
            i = R.id.btnCancel;
            MaterialButton materialButton2 = (MaterialButton) ViewBindings.findChildViewById(rootView, R.id.btnCancel);
            if (materialButton2 != null) {
                i = R.id.btnGenerateAndExportKeystore;
                MaterialButton materialButton3 = (MaterialButton) ViewBindings.findChildViewById(rootView, R.id.btnGenerateAndExportKeystore);
                if (materialButton3 != null) {
                    i = R.id.btnLandscape;
                    MaterialButton materialButton4 = (MaterialButton) ViewBindings.findChildViewById(rootView, R.id.btnLandscape);
                    if (materialButton4 != null) {
                        i = R.id.btnNext;
                        MaterialButton materialButton5 = (MaterialButton) ViewBindings.findChildViewById(rootView, R.id.btnNext);
                        if (materialButton5 != null) {
                            i = R.id.btnPortrait;
                            MaterialButton materialButton6 = (MaterialButton) ViewBindings.findChildViewById(rootView, R.id.btnPortrait);
                            if (materialButton6 != null) {
                                i = R.id.btnSelectKeystore;
                                MaterialButton materialButton7 = (MaterialButton) ViewBindings.findChildViewById(rootView, R.id.btnSelectKeystore);
                                if (materialButton7 != null) {
                                    i = R.id.btnSensor;
                                    MaterialButton materialButton8 = (MaterialButton) ViewBindings.findChildViewById(rootView, R.id.btnSensor);
                                    if (materialButton8 != null) {
                                        i = R.id.buttonDivider;
                                        View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.buttonDivider);
                                        if (viewFindChildViewById != null) {
                                            i = R.id.cbArm64;
                                            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.cbArm64);
                                            if (checkBox != null) {
                                                i = R.id.cbArmv7;
                                                CheckBox checkBox2 = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.cbArmv7);
                                                if (checkBox2 != null) {
                                                    i = R.id.cgDetectedPermissions;
                                                    ChipGroup chipGroup = (ChipGroup) ViewBindings.findChildViewById(rootView, R.id.cgDetectedPermissions);
                                                    if (chipGroup != null) {
                                                        i = R.id.etBuildAppName;
                                                        TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.etBuildAppName);
                                                        if (textInputEditText != null) {
                                                            i = R.id.etBuildPackageName;
                                                            TextInputEditText textInputEditText2 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.etBuildPackageName);
                                                            if (textInputEditText2 != null) {
                                                                i = R.id.etBuildTemplate;
                                                                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) ViewBindings.findChildViewById(rootView, R.id.etBuildTemplate);
                                                                if (autoCompleteTextView != null) {
                                                                    i = R.id.etBuildVersionCode;
                                                                    TextInputEditText textInputEditText3 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.etBuildVersionCode);
                                                                    if (textInputEditText3 != null) {
                                                                        i = R.id.etBuildVersionName;
                                                                        TextInputEditText textInputEditText4 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.etBuildVersionName);
                                                                        if (textInputEditText4 != null) {
                                                                            i = R.id.etGenAlias;
                                                                            TextInputEditText textInputEditText5 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.etGenAlias);
                                                                            if (textInputEditText5 != null) {
                                                                                i = R.id.etGenOwner;
                                                                                TextInputEditText textInputEditText6 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.etGenOwner);
                                                                                if (textInputEditText6 != null) {
                                                                                    i = R.id.etGenPass;
                                                                                    TextInputEditText textInputEditText7 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.etGenPass);
                                                                                    if (textInputEditText7 != null) {
                                                                                        i = R.id.etKeystoreAlias;
                                                                                        TextInputEditText textInputEditText8 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.etKeystoreAlias);
                                                                                        if (textInputEditText8 != null) {
                                                                                            i = R.id.etKeystorePass;
                                                                                            TextInputEditText textInputEditText9 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.etKeystorePass);
                                                                                            if (textInputEditText9 != null) {
                                                                                                i = R.id.llCustomKeystoreFields;
                                                                                                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llCustomKeystoreFields);
                                                                                                if (linearLayout != null) {
                                                                                                    i = R.id.llGenerateKeystoreFields;
                                                                                                    LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llGenerateKeystoreFields);
                                                                                                    if (linearLayout2 != null) {
                                                                                                        i = R.id.llManualPermissions;
                                                                                                        LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llManualPermissions);
                                                                                                        if (linearLayout3 != null) {
                                                                                                            i = R.id.navigationContainer;
                                                                                                            ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(rootView, R.id.navigationContainer);
                                                                                                            if (constraintLayout != null) {
                                                                                                                i = R.id.nestedScrollView;
                                                                                                                NestedScrollView nestedScrollView = (NestedScrollView) ViewBindings.findChildViewById(rootView, R.id.nestedScrollView);
                                                                                                                if (nestedScrollView != null) {
                                                                                                                    i = R.id.rbAutoDebug;
                                                                                                                    RadioButton radioButton = (RadioButton) ViewBindings.findChildViewById(rootView, R.id.rbAutoDebug);
                                                                                                                    if (radioButton != null) {
                                                                                                                        i = R.id.rbCustomKeystore;
                                                                                                                        RadioButton radioButton2 = (RadioButton) ViewBindings.findChildViewById(rootView, R.id.rbCustomKeystore);
                                                                                                                        if (radioButton2 != null) {
                                                                                                                            i = R.id.rbGenerateNew;
                                                                                                                            RadioButton radioButton3 = (RadioButton) ViewBindings.findChildViewById(rootView, R.id.rbGenerateNew);
                                                                                                                            if (radioButton3 != null) {
                                                                                                                                i = R.id.rgKeystore;
                                                                                                                                RadioGroup radioGroup = (RadioGroup) ViewBindings.findChildViewById(rootView, R.id.rgKeystore);
                                                                                                                                if (radioGroup != null) {
                                                                                                                                    i = R.id.step1Container;
                                                                                                                                    LinearLayout linearLayout4 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.step1Container);
                                                                                                                                    if (linearLayout4 != null) {
                                                                                                                                        i = R.id.step2Container;
                                                                                                                                        LinearLayout linearLayout5 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.step2Container);
                                                                                                                                        if (linearLayout5 != null) {
                                                                                                                                            i = R.id.step3Container;
                                                                                                                                            LinearLayout linearLayout6 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.step3Container);
                                                                                                                                            if (linearLayout6 != null) {
                                                                                                                                                i = R.id.step4Container;
                                                                                                                                                LinearLayout linearLayout7 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.step4Container);
                                                                                                                                                if (linearLayout7 != null) {
                                                                                                                                                    i = R.id.stepProgressIndicator;
                                                                                                                                                    ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, R.id.stepProgressIndicator);
                                                                                                                                                    if (progressBar != null) {
                                                                                                                                                        i = R.id.stepsContainer;
                                                                                                                                                        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(rootView, R.id.stepsContainer);
                                                                                                                                                        if (frameLayout != null) {
                                                                                                                                                            i = R.id.switchCompiler;
                                                                                                                                                            SwitchMaterial switchMaterial = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchCompiler);
                                                                                                                                                            if (switchMaterial != null) {
                                                                                                                                                                i = R.id.switchCustomizePermissions;
                                                                                                                                                                SwitchMaterial switchMaterial2 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchCustomizePermissions);
                                                                                                                                                                if (switchMaterial2 != null) {
                                                                                                                                                                    i = R.id.switchMkit;
                                                                                                                                                                    SwitchMaterial switchMaterial3 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchMkit);
                                                                                                                                                                    if (switchMaterial3 != null) {
                                                                                                                                                                        i = R.id.switchMnn;
                                                                                                                                                                        SwitchMaterial switchMaterial4 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchMnn);
                                                                                                                                                                        if (switchMaterial4 != null) {
                                                                                                                                                                            i = R.id.switchOnnx;
                                                                                                                                                                            SwitchMaterial switchMaterial5 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchOnnx);
                                                                                                                                                                            if (switchMaterial5 != null) {
                                                                                                                                                                                i = R.id.switchPermAudio;
                                                                                                                                                                                SwitchMaterial switchMaterial6 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchPermAudio);
                                                                                                                                                                                if (switchMaterial6 != null) {
                                                                                                                                                                                    i = R.id.switchPermBackground;
                                                                                                                                                                                    SwitchMaterial switchMaterial7 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchPermBackground);
                                                                                                                                                                                    if (switchMaterial7 != null) {
                                                                                                                                                                                        i = R.id.switchPermBluetooth;
                                                                                                                                                                                        SwitchMaterial switchMaterial8 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchPermBluetooth);
                                                                                                                                                                                        if (switchMaterial8 != null) {
                                                                                                                                                                                            i = R.id.switchPermCamera;
                                                                                                                                                                                            SwitchMaterial switchMaterial9 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchPermCamera);
                                                                                                                                                                                            if (switchMaterial9 != null) {
                                                                                                                                                                                                i = R.id.switchPermInternet;
                                                                                                                                                                                                SwitchMaterial switchMaterial10 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchPermInternet);
                                                                                                                                                                                                if (switchMaterial10 != null) {
                                                                                                                                                                                                    i = R.id.switchPermLocation;
                                                                                                                                                                                                    SwitchMaterial switchMaterial11 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchPermLocation);
                                                                                                                                                                                                    if (switchMaterial11 != null) {
                                                                                                                                                                                                        i = R.id.switchPermNfc;
                                                                                                                                                                                                        SwitchMaterial switchMaterial12 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchPermNfc);
                                                                                                                                                                                                        if (switchMaterial12 != null) {
                                                                                                                                                                                                            i = R.id.switchPermOverlay;
                                                                                                                                                                                                            SwitchMaterial switchMaterial13 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchPermOverlay);
                                                                                                                                                                                                            if (switchMaterial13 != null) {
                                                                                                                                                                                                                i = R.id.switchPermStorage;
                                                                                                                                                                                                                SwitchMaterial switchMaterial14 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchPermStorage);
                                                                                                                                                                                                                if (switchMaterial14 != null) {
                                                                                                                                                                                                                    i = R.id.switchPermVibrate;
                                                                                                                                                                                                                    SwitchMaterial switchMaterial15 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchPermVibrate);
                                                                                                                                                                                                                    if (switchMaterial15 != null) {
                                                                                                                                                                                                                        i = R.id.switchPython;
                                                                                                                                                                                                                        SwitchMaterial switchMaterial16 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchPython);
                                                                                                                                                                                                                        if (switchMaterial16 != null) {
                                                                                                                                                                                                                            i = R.id.switchQemu;
                                                                                                                                                                                                                            SwitchMaterial switchMaterial17 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.switchQemu);
                                                                                                                                                                                                                            if (switchMaterial17 != null) {
                                                                                                                                                                                                                                i = R.id.tilAppName;
                                                                                                                                                                                                                                TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.tilAppName);
                                                                                                                                                                                                                                if (textInputLayout != null) {
                                                                                                                                                                                                                                    i = R.id.tilPackageName;
                                                                                                                                                                                                                                    TextInputLayout textInputLayout2 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.tilPackageName);
                                                                                                                                                                                                                                    if (textInputLayout2 != null) {
                                                                                                                                                                                                                                        i = R.id.tilTemplates;
                                                                                                                                                                                                                                        TextInputLayout textInputLayout3 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.tilTemplates);
                                                                                                                                                                                                                                        if (textInputLayout3 != null) {
                                                                                                                                                                                                                                            i = R.id.tilVersionCode;
                                                                                                                                                                                                                                            TextInputLayout textInputLayout4 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.tilVersionCode);
                                                                                                                                                                                                                                            if (textInputLayout4 != null) {
                                                                                                                                                                                                                                                i = R.id.tilVersionName;
                                                                                                                                                                                                                                                TextInputLayout textInputLayout5 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.tilVersionName);
                                                                                                                                                                                                                                                if (textInputLayout5 != null) {
                                                                                                                                                                                                                                                    i = R.id.toggleGroupOrientation;
                                                                                                                                                                                                                                                    MaterialButtonToggleGroup materialButtonToggleGroup = (MaterialButtonToggleGroup) ViewBindings.findChildViewById(rootView, R.id.toggleGroupOrientation);
                                                                                                                                                                                                                                                    if (materialButtonToggleGroup != null) {
                                                                                                                                                                                                                                                        i = R.id.tvDialogTitle;
                                                                                                                                                                                                                                                        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvDialogTitle);
                                                                                                                                                                                                                                                        if (textView != null) {
                                                                                                                                                                                                                                                            i = R.id.tvSelectedKeystorePath;
                                                                                                                                                                                                                                                            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvSelectedKeystorePath);
                                                                                                                                                                                                                                                            if (textView2 != null) {
                                                                                                                                                                                                                                                                i = R.id.tvStepSubtitle;
                                                                                                                                                                                                                                                                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvStepSubtitle);
                                                                                                                                                                                                                                                                if (textView3 != null) {
                                                                                                                                                                                                                                                                    return new DialogApkBuildSettingsBinding((MaterialCardView) rootView, materialButton, materialButton2, materialButton3, materialButton4, materialButton5, materialButton6, materialButton7, materialButton8, viewFindChildViewById, checkBox, checkBox2, chipGroup, textInputEditText, textInputEditText2, autoCompleteTextView, textInputEditText3, textInputEditText4, textInputEditText5, textInputEditText6, textInputEditText7, textInputEditText8, textInputEditText9, linearLayout, linearLayout2, linearLayout3, constraintLayout, nestedScrollView, radioButton, radioButton2, radioButton3, radioGroup, linearLayout4, linearLayout5, linearLayout6, linearLayout7, progressBar, frameLayout, switchMaterial, switchMaterial2, switchMaterial3, switchMaterial4, switchMaterial5, switchMaterial6, switchMaterial7, switchMaterial8, switchMaterial9, switchMaterial10, switchMaterial11, switchMaterial12, switchMaterial13, switchMaterial14, switchMaterial15, switchMaterial16, switchMaterial17, textInputLayout, textInputLayout2, textInputLayout3, textInputLayout4, textInputLayout5, materialButtonToggleGroup, textView, textView2, textView3);
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
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
