package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogNewProjectBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogNewProjectBinding implements ViewBinding {
    public final MaterialButton btnLandscape;
    public final MaterialButton btnPortrait;
    public final LinearLayout contentContainer;
    public final SwitchMaterial exampleProjectSwitch;
    public final SwitchMaterial gitCloneSwitch;
    public final LinearLayout gitInputContainer;
    public final ProgressBar gitProgressBar;
    public final TextInputEditText gitTokenEditText;
    public final TextInputEditText gitUrlEditText;
    public final TextInputLayout input;
    public final TextInputEditText inputEditText;
    public final MaterialButtonToggleGroup orientationToggleGroup;
    private final ScrollView rootView;
    public final LinearLayout standardSettingsContainer;
    public final ToolbarBinding toolbar;

    private DialogNewProjectBinding(ScrollView rootView, MaterialButton btnLandscape, MaterialButton btnPortrait, LinearLayout contentContainer, SwitchMaterial exampleProjectSwitch, SwitchMaterial gitCloneSwitch, LinearLayout gitInputContainer, ProgressBar gitProgressBar, TextInputEditText gitTokenEditText, TextInputEditText gitUrlEditText, TextInputLayout input, TextInputEditText inputEditText, MaterialButtonToggleGroup orientationToggleGroup, LinearLayout standardSettingsContainer, ToolbarBinding toolbar) {
        this.rootView = rootView;
        this.btnLandscape = btnLandscape;
        this.btnPortrait = btnPortrait;
        this.contentContainer = contentContainer;
        this.exampleProjectSwitch = exampleProjectSwitch;
        this.gitCloneSwitch = gitCloneSwitch;
        this.gitInputContainer = gitInputContainer;
        this.gitProgressBar = gitProgressBar;
        this.gitTokenEditText = gitTokenEditText;
        this.gitUrlEditText = gitUrlEditText;
        this.input = input;
        this.inputEditText = inputEditText;
        this.orientationToggleGroup = orientationToggleGroup;
        this.standardSettingsContainer = standardSettingsContainer;
        this.toolbar = toolbar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogNewProjectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogNewProjectBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_new_project, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogNewProjectBinding bind(View rootView) {
        int i = R.id.btn_landscape;
        MaterialButton materialButton = (MaterialButton) ViewBindings.findChildViewById(rootView, R.id.btn_landscape);
        if (materialButton != null) {
            i = R.id.btn_portrait;
            MaterialButton materialButton2 = (MaterialButton) ViewBindings.findChildViewById(rootView, R.id.btn_portrait);
            if (materialButton2 != null) {
                i = R.id.content_container;
                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.content_container);
                if (linearLayout != null) {
                    i = R.id.example_project_switch;
                    SwitchMaterial switchMaterial = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.example_project_switch);
                    if (switchMaterial != null) {
                        i = R.id.git_clone_switch;
                        SwitchMaterial switchMaterial2 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.git_clone_switch);
                        if (switchMaterial2 != null) {
                            i = R.id.git_input_container;
                            LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.git_input_container);
                            if (linearLayout2 != null) {
                                i = R.id.git_progress_bar;
                                ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, R.id.git_progress_bar);
                                if (progressBar != null) {
                                    i = R.id.git_token_edit_text;
                                    TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.git_token_edit_text);
                                    if (textInputEditText != null) {
                                        i = R.id.git_url_edit_text;
                                        TextInputEditText textInputEditText2 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.git_url_edit_text);
                                        if (textInputEditText2 != null) {
                                            i = R.id.input;
                                            TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.input);
                                            if (textInputLayout != null) {
                                                i = R.id.input_edit_text;
                                                TextInputEditText textInputEditText3 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.input_edit_text);
                                                if (textInputEditText3 != null) {
                                                    i = R.id.orientation_toggle_group;
                                                    MaterialButtonToggleGroup materialButtonToggleGroup = (MaterialButtonToggleGroup) ViewBindings.findChildViewById(rootView, R.id.orientation_toggle_group);
                                                    if (materialButtonToggleGroup != null) {
                                                        i = R.id.standard_settings_container;
                                                        LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.standard_settings_container);
                                                        if (linearLayout3 != null) {
                                                            i = R.id.toolbar;
                                                            View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.toolbar);
                                                            if (viewFindChildViewById != null) {
                                                                return new DialogNewProjectBinding((ScrollView) rootView, materialButton, materialButton2, linearLayout, switchMaterial, switchMaterial2, linearLayout2, progressBar, textInputEditText, textInputEditText2, textInputLayout, textInputEditText3, materialButtonToggleGroup, linearLayout3, ToolbarBinding.bind(viewFindChildViewById));
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
