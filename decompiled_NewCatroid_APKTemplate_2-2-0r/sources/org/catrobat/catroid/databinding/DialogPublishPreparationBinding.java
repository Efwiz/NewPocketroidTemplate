package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogPublishPreparationBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogPublishPreparationBinding implements ViewBinding {
    public final MaterialButton btnSubmitPublish;
    public final ChipGroup chipGroupPublishGenres;
    public final TextInputEditText etPublishDescription;
    public final TextInputEditText etPublishTitle;
    private final MaterialCardView rootView;
    public final TextInputLayout tilPublishDescription;
    public final TextInputLayout tilPublishTitle;
    public final TextView tvPublishRulesLink;

    private DialogPublishPreparationBinding(MaterialCardView rootView, MaterialButton btnSubmitPublish, ChipGroup chipGroupPublishGenres, TextInputEditText etPublishDescription, TextInputEditText etPublishTitle, TextInputLayout tilPublishDescription, TextInputLayout tilPublishTitle, TextView tvPublishRulesLink) {
        this.rootView = rootView;
        this.btnSubmitPublish = btnSubmitPublish;
        this.chipGroupPublishGenres = chipGroupPublishGenres;
        this.etPublishDescription = etPublishDescription;
        this.etPublishTitle = etPublishTitle;
        this.tilPublishDescription = tilPublishDescription;
        this.tilPublishTitle = tilPublishTitle;
        this.tvPublishRulesLink = tvPublishRulesLink;
    }

    @Override // androidx.viewbinding.ViewBinding
    public MaterialCardView getRoot() {
        return this.rootView;
    }

    public static DialogPublishPreparationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogPublishPreparationBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_publish_preparation, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogPublishPreparationBinding bind(View rootView) {
        int i = R.id.btnSubmitPublish;
        MaterialButton materialButton = (MaterialButton) ViewBindings.findChildViewById(rootView, R.id.btnSubmitPublish);
        if (materialButton != null) {
            i = R.id.chipGroupPublishGenres;
            ChipGroup chipGroup = (ChipGroup) ViewBindings.findChildViewById(rootView, R.id.chipGroupPublishGenres);
            if (chipGroup != null) {
                i = R.id.etPublishDescription;
                TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.etPublishDescription);
                if (textInputEditText != null) {
                    i = R.id.etPublishTitle;
                    TextInputEditText textInputEditText2 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.etPublishTitle);
                    if (textInputEditText2 != null) {
                        i = R.id.tilPublishDescription;
                        TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.tilPublishDescription);
                        if (textInputLayout != null) {
                            i = R.id.tilPublishTitle;
                            TextInputLayout textInputLayout2 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.tilPublishTitle);
                            if (textInputLayout2 != null) {
                                i = R.id.tvPublishRulesLink;
                                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvPublishRulesLink);
                                if (textView != null) {
                                    return new DialogPublishPreparationBinding((MaterialCardView) rootView, materialButton, chipGroup, textInputEditText, textInputEditText2, textInputLayout, textInputLayout2, textView);
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
