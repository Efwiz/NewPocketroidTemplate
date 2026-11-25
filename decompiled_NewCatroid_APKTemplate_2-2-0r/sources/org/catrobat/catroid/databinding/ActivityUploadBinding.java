package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivityUploadBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivityUploadBinding implements ViewBinding {
    public final TextInputLayout inputProjectDescription;
    public final TextInputLayout inputProjectName;
    public final TextInputLayout inputProjectNotesAndCredits;
    public final TextInputEditText projectDescriptionUpload;
    public final ImageView projectImageView;
    public final TextView projectNotesAndCreditsExplanation;
    public final TextInputEditText projectNotesAndCreditsUpload;
    public final TextView projectSize;
    public final TextView projectSizeView;
    public final TextInputEditText projectUploadName;
    private final LinearLayout rootView;
    public final LinearLayout uploadLayout;

    private ActivityUploadBinding(LinearLayout rootView, TextInputLayout inputProjectDescription, TextInputLayout inputProjectName, TextInputLayout inputProjectNotesAndCredits, TextInputEditText projectDescriptionUpload, ImageView projectImageView, TextView projectNotesAndCreditsExplanation, TextInputEditText projectNotesAndCreditsUpload, TextView projectSize, TextView projectSizeView, TextInputEditText projectUploadName, LinearLayout uploadLayout) {
        this.rootView = rootView;
        this.inputProjectDescription = inputProjectDescription;
        this.inputProjectName = inputProjectName;
        this.inputProjectNotesAndCredits = inputProjectNotesAndCredits;
        this.projectDescriptionUpload = projectDescriptionUpload;
        this.projectImageView = projectImageView;
        this.projectNotesAndCreditsExplanation = projectNotesAndCreditsExplanation;
        this.projectNotesAndCreditsUpload = projectNotesAndCreditsUpload;
        this.projectSize = projectSize;
        this.projectSizeView = projectSizeView;
        this.projectUploadName = projectUploadName;
        this.uploadLayout = uploadLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityUploadBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityUploadBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_upload, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityUploadBinding bind(View rootView) {
        int i = R.id.input_project_description;
        TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.input_project_description);
        if (textInputLayout != null) {
            i = R.id.input_project_name;
            TextInputLayout textInputLayout2 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.input_project_name);
            if (textInputLayout2 != null) {
                i = R.id.input_project_notes_and_credits;
                TextInputLayout textInputLayout3 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.input_project_notes_and_credits);
                if (textInputLayout3 != null) {
                    i = R.id.project_description_upload;
                    TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.project_description_upload);
                    if (textInputEditText != null) {
                        i = R.id.project_image_view;
                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.project_image_view);
                        if (imageView != null) {
                            i = R.id.project_notes_and_credits_explanation;
                            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.project_notes_and_credits_explanation);
                            if (textView != null) {
                                i = R.id.project_notes_and_credits_upload;
                                TextInputEditText textInputEditText2 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.project_notes_and_credits_upload);
                                if (textInputEditText2 != null) {
                                    i = R.id.project_size;
                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.project_size);
                                    if (textView2 != null) {
                                        i = R.id.project_size_view;
                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.project_size_view);
                                        if (textView3 != null) {
                                            i = R.id.project_upload_name;
                                            TextInputEditText textInputEditText3 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.project_upload_name);
                                            if (textInputEditText3 != null) {
                                                i = R.id.upload_layout;
                                                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.upload_layout);
                                                if (linearLayout != null) {
                                                    return new ActivityUploadBinding((LinearLayout) rootView, textInputLayout, textInputLayout2, textInputLayout3, textInputEditText, imageView, textView, textInputEditText2, textView2, textView3, textInputEditText3, linearLayout);
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
