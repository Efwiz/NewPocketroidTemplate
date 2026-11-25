package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentProjectOptionsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentProjectOptionsBinding implements ViewBinding {
    public final ImageButton btnDismissPublishNotification;
    public final ChipGroup chipGroupTags;
    public final TextView gitConnectButton;
    public final TextView gitPublishButton;
    public final TextView gitUpdateButton;
    public final SwitchMaterial projectOptionsAspectRatio;
    public final TextView projectOptionsBake;
    public final TextView projectOptionsBuildApk;
    public final TextView projectOptionsChangeIcon;
    public final TextView projectOptionsChangeOrientation;
    public final TextView projectOptionsClearVars;
    public final SwitchMaterial projectOptionsCustomResolution;
    public final Button projectOptionsDelete;
    public final TextInputEditText projectOptionsDescription;
    public final TextInputLayout projectOptionsDescriptionLayout;
    public final SwitchMaterial projectOptionsDisableScientificNotation;
    public final LinearLayout projectOptionsLayout;
    public final TextView projectOptionsMoreDetails;
    public final TextInputEditText projectOptionsName;
    public final TextInputLayout projectOptionsNameLayout;
    public final TextInputEditText projectOptionsNotesAndCredits;
    public final TextInputLayout projectOptionsNotesAndCreditsLayout;
    public final TextInputEditText projectOptionsPhysicsHeight;
    public final TextInputLayout projectOptionsPhysicsHeightLayout;
    public final TextInputEditText projectOptionsPhysicsWidth;
    public final TextInputLayout projectOptionsPhysicsWidthLayout;
    public final TextView projectOptionsRebuildCache;
    public final TextView projectOptionsSaveExternal;
    public final TextView projectOptionsUpload;
    public final ProgressBar publishProgressBar;
    public final MaterialCardView publishProgressPill;
    private final ConstraintLayout rootView;
    public final LinearLayout tags;
    public final TextView tvPublishProgressStatus;
    public final TextView tvPublishProgressTitle;

    private FragmentProjectOptionsBinding(ConstraintLayout rootView, ImageButton btnDismissPublishNotification, ChipGroup chipGroupTags, TextView gitConnectButton, TextView gitPublishButton, TextView gitUpdateButton, SwitchMaterial projectOptionsAspectRatio, TextView projectOptionsBake, TextView projectOptionsBuildApk, TextView projectOptionsChangeIcon, TextView projectOptionsChangeOrientation, TextView projectOptionsClearVars, SwitchMaterial projectOptionsCustomResolution, Button projectOptionsDelete, TextInputEditText projectOptionsDescription, TextInputLayout projectOptionsDescriptionLayout, SwitchMaterial projectOptionsDisableScientificNotation, LinearLayout projectOptionsLayout, TextView projectOptionsMoreDetails, TextInputEditText projectOptionsName, TextInputLayout projectOptionsNameLayout, TextInputEditText projectOptionsNotesAndCredits, TextInputLayout projectOptionsNotesAndCreditsLayout, TextInputEditText projectOptionsPhysicsHeight, TextInputLayout projectOptionsPhysicsHeightLayout, TextInputEditText projectOptionsPhysicsWidth, TextInputLayout projectOptionsPhysicsWidthLayout, TextView projectOptionsRebuildCache, TextView projectOptionsSaveExternal, TextView projectOptionsUpload, ProgressBar publishProgressBar, MaterialCardView publishProgressPill, LinearLayout tags, TextView tvPublishProgressStatus, TextView tvPublishProgressTitle) {
        this.rootView = rootView;
        this.btnDismissPublishNotification = btnDismissPublishNotification;
        this.chipGroupTags = chipGroupTags;
        this.gitConnectButton = gitConnectButton;
        this.gitPublishButton = gitPublishButton;
        this.gitUpdateButton = gitUpdateButton;
        this.projectOptionsAspectRatio = projectOptionsAspectRatio;
        this.projectOptionsBake = projectOptionsBake;
        this.projectOptionsBuildApk = projectOptionsBuildApk;
        this.projectOptionsChangeIcon = projectOptionsChangeIcon;
        this.projectOptionsChangeOrientation = projectOptionsChangeOrientation;
        this.projectOptionsClearVars = projectOptionsClearVars;
        this.projectOptionsCustomResolution = projectOptionsCustomResolution;
        this.projectOptionsDelete = projectOptionsDelete;
        this.projectOptionsDescription = projectOptionsDescription;
        this.projectOptionsDescriptionLayout = projectOptionsDescriptionLayout;
        this.projectOptionsDisableScientificNotation = projectOptionsDisableScientificNotation;
        this.projectOptionsLayout = projectOptionsLayout;
        this.projectOptionsMoreDetails = projectOptionsMoreDetails;
        this.projectOptionsName = projectOptionsName;
        this.projectOptionsNameLayout = projectOptionsNameLayout;
        this.projectOptionsNotesAndCredits = projectOptionsNotesAndCredits;
        this.projectOptionsNotesAndCreditsLayout = projectOptionsNotesAndCreditsLayout;
        this.projectOptionsPhysicsHeight = projectOptionsPhysicsHeight;
        this.projectOptionsPhysicsHeightLayout = projectOptionsPhysicsHeightLayout;
        this.projectOptionsPhysicsWidth = projectOptionsPhysicsWidth;
        this.projectOptionsPhysicsWidthLayout = projectOptionsPhysicsWidthLayout;
        this.projectOptionsRebuildCache = projectOptionsRebuildCache;
        this.projectOptionsSaveExternal = projectOptionsSaveExternal;
        this.projectOptionsUpload = projectOptionsUpload;
        this.publishProgressBar = publishProgressBar;
        this.publishProgressPill = publishProgressPill;
        this.tags = tags;
        this.tvPublishProgressStatus = tvPublishProgressStatus;
        this.tvPublishProgressTitle = tvPublishProgressTitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static FragmentProjectOptionsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentProjectOptionsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_project_options, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentProjectOptionsBinding bind(View rootView) {
        int i = R.id.btnDismissPublishNotification;
        ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btnDismissPublishNotification);
        if (imageButton != null) {
            i = R.id.chip_group_tags;
            ChipGroup chipGroup = (ChipGroup) ViewBindings.findChildViewById(rootView, R.id.chip_group_tags);
            if (chipGroup != null) {
                i = R.id.git_connect_button;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.git_connect_button);
                if (textView != null) {
                    i = R.id.git_publish_button;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.git_publish_button);
                    if (textView2 != null) {
                        i = R.id.git_update_button;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.git_update_button);
                        if (textView3 != null) {
                            i = R.id.project_options_aspect_ratio;
                            SwitchMaterial switchMaterial = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.project_options_aspect_ratio);
                            if (switchMaterial != null) {
                                i = R.id.project_options_bake;
                                TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.project_options_bake);
                                if (textView4 != null) {
                                    i = R.id.project_options_build_apk;
                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.project_options_build_apk);
                                    if (textView5 != null) {
                                        i = R.id.project_options_change_icon;
                                        TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.project_options_change_icon);
                                        if (textView6 != null) {
                                            i = R.id.project_options_change_orientation;
                                            TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.project_options_change_orientation);
                                            if (textView7 != null) {
                                                i = R.id.project_options_clear_vars;
                                                TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.project_options_clear_vars);
                                                if (textView8 != null) {
                                                    i = R.id.project_options_custom_resolution;
                                                    SwitchMaterial switchMaterial2 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.project_options_custom_resolution);
                                                    if (switchMaterial2 != null) {
                                                        i = R.id.project_options_delete;
                                                        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.project_options_delete);
                                                        if (button != null) {
                                                            i = R.id.project_options_description;
                                                            TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.project_options_description);
                                                            if (textInputEditText != null) {
                                                                i = R.id.project_options_description_layout;
                                                                TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.project_options_description_layout);
                                                                if (textInputLayout != null) {
                                                                    i = R.id.project_options_disable_scientific_notation;
                                                                    SwitchMaterial switchMaterial3 = (SwitchMaterial) ViewBindings.findChildViewById(rootView, R.id.project_options_disable_scientific_notation);
                                                                    if (switchMaterial3 != null) {
                                                                        i = R.id.project_options_layout;
                                                                        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.project_options_layout);
                                                                        if (linearLayout != null) {
                                                                            i = R.id.project_options_more_details;
                                                                            TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.project_options_more_details);
                                                                            if (textView9 != null) {
                                                                                i = R.id.project_options_name;
                                                                                TextInputEditText textInputEditText2 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.project_options_name);
                                                                                if (textInputEditText2 != null) {
                                                                                    i = R.id.project_options_name_layout;
                                                                                    TextInputLayout textInputLayout2 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.project_options_name_layout);
                                                                                    if (textInputLayout2 != null) {
                                                                                        i = R.id.project_options_notes_and_credits;
                                                                                        TextInputEditText textInputEditText3 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.project_options_notes_and_credits);
                                                                                        if (textInputEditText3 != null) {
                                                                                            i = R.id.project_options_notes_and_credits_layout;
                                                                                            TextInputLayout textInputLayout3 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.project_options_notes_and_credits_layout);
                                                                                            if (textInputLayout3 != null) {
                                                                                                i = R.id.project_options_physics_height;
                                                                                                TextInputEditText textInputEditText4 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.project_options_physics_height);
                                                                                                if (textInputEditText4 != null) {
                                                                                                    i = R.id.project_options_physics_height_layout;
                                                                                                    TextInputLayout textInputLayout4 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.project_options_physics_height_layout);
                                                                                                    if (textInputLayout4 != null) {
                                                                                                        i = R.id.project_options_physics_width;
                                                                                                        TextInputEditText textInputEditText5 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.project_options_physics_width);
                                                                                                        if (textInputEditText5 != null) {
                                                                                                            i = R.id.project_options_physics_width_layout;
                                                                                                            TextInputLayout textInputLayout5 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.project_options_physics_width_layout);
                                                                                                            if (textInputLayout5 != null) {
                                                                                                                i = R.id.project_options_rebuild_cache;
                                                                                                                TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.project_options_rebuild_cache);
                                                                                                                if (textView10 != null) {
                                                                                                                    i = R.id.project_options_save_external;
                                                                                                                    TextView textView11 = (TextView) ViewBindings.findChildViewById(rootView, R.id.project_options_save_external);
                                                                                                                    if (textView11 != null) {
                                                                                                                        i = R.id.project_options_upload;
                                                                                                                        TextView textView12 = (TextView) ViewBindings.findChildViewById(rootView, R.id.project_options_upload);
                                                                                                                        if (textView12 != null) {
                                                                                                                            i = R.id.publishProgressBar;
                                                                                                                            ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, R.id.publishProgressBar);
                                                                                                                            if (progressBar != null) {
                                                                                                                                i = R.id.publishProgressPill;
                                                                                                                                MaterialCardView materialCardView = (MaterialCardView) ViewBindings.findChildViewById(rootView, R.id.publishProgressPill);
                                                                                                                                if (materialCardView != null) {
                                                                                                                                    i = R.id.tags;
                                                                                                                                    LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.tags);
                                                                                                                                    if (linearLayout2 != null) {
                                                                                                                                        i = R.id.tvPublishProgressStatus;
                                                                                                                                        TextView textView13 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvPublishProgressStatus);
                                                                                                                                        if (textView13 != null) {
                                                                                                                                            i = R.id.tvPublishProgressTitle;
                                                                                                                                            TextView textView14 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvPublishProgressTitle);
                                                                                                                                            if (textView14 != null) {
                                                                                                                                                return new FragmentProjectOptionsBinding((ConstraintLayout) rootView, imageButton, chipGroup, textView, textView2, textView3, switchMaterial, textView4, textView5, textView6, textView7, textView8, switchMaterial2, button, textInputEditText, textInputLayout, switchMaterial3, linearLayout, textView9, textInputEditText2, textInputLayout2, textInputEditText3, textInputLayout3, textInputEditText4, textInputLayout4, textInputEditText5, textInputLayout5, textView10, textView11, textView12, progressBar, materialCardView, linearLayout2, textView13, textView14);
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
