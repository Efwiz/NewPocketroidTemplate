package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogArrayDuplicateBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogArrayDuplicateBinding implements ViewBinding {
    public final TextInputEditText editArrayCount;
    public final TextInputEditText editArrayStep;
    private final LinearLayout rootView;
    public final Spinner spinnerArrayAxis;

    private DialogArrayDuplicateBinding(LinearLayout rootView, TextInputEditText editArrayCount, TextInputEditText editArrayStep, Spinner spinnerArrayAxis) {
        this.rootView = rootView;
        this.editArrayCount = editArrayCount;
        this.editArrayStep = editArrayStep;
        this.spinnerArrayAxis = spinnerArrayAxis;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogArrayDuplicateBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogArrayDuplicateBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_array_duplicate, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogArrayDuplicateBinding bind(View rootView) {
        int i = R.id.edit_array_count;
        TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.edit_array_count);
        if (textInputEditText != null) {
            i = R.id.edit_array_step;
            TextInputEditText textInputEditText2 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.edit_array_step);
            if (textInputEditText2 != null) {
                i = R.id.spinner_array_axis;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.spinner_array_axis);
                if (spinner != null) {
                    return new DialogArrayDuplicateBinding((LinearLayout) rootView, textInputEditText, textInputEditText2, spinner);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
