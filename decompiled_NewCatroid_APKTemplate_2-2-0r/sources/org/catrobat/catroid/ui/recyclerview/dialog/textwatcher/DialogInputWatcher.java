package org.catrobat.catroid.ui.recyclerview.dialog.textwatcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/textwatcher/DialogInputWatcher.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DialogInputWatcher implements TextWatcher {
    private boolean allowEmptyInput;
    private TextInputLayout inputLayout;
    private Button positiveButton;

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable s) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public DialogInputWatcher(TextInputLayout inputLayout, Button positiveButton, boolean allowEmptyInput) {
        this.inputLayout = inputLayout;
        this.positiveButton = positiveButton;
        this.allowEmptyInput = allowEmptyInput;
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.inputLayout.setError(null);
        if (this.allowEmptyInput) {
            return;
        }
        this.positiveButton.setEnabled(s.length() > 0);
    }
}
