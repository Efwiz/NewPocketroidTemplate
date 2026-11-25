package org.catrobat.catroid.ui.recyclerview.dialog.textwatcher;

import android.content.Context;
import android.text.Editable;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/textwatcher/InputWatcher.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class InputWatcher {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/textwatcher/InputWatcher$TextWatcher.class
      classes14.dex
     */
    public static class TextWatcher implements android.text.TextWatcher {
        protected Button button;
        protected Context context;
        protected TextInputLayout inputLayout;
        protected List<String> scope = new ArrayList();

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void setScope(List<String> scope) {
            this.scope = scope;
        }

        public void setButton(Button button) {
            this.button = button;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public void setInputLayout(TextInputLayout inputLayout) {
            this.inputLayout = inputLayout;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable s) {
            String strValidateInput = validateInput(s.toString(), this.context);
            this.inputLayout.setError(strValidateInput);
            this.button.setEnabled(strValidateInput == null);
        }

        public String validateInput(String input, Context context) {
            if (input.isEmpty()) {
                return context.getString(R.string.name_empty);
            }
            String strTrim = input.trim();
            if (strTrim.isEmpty()) {
                return context.getString(R.string.name_consists_of_spaces_only);
            }
            if (isNameUnique(strTrim)) {
                return null;
            }
            return context.getString(R.string.name_already_exists);
        }

        public String validatePhysicsInput(String input, Context context) {
            if (input.isEmpty()) {
                return context.getString(R.string.name_empty);
            }
            String strTrim = input.trim();
            if (strTrim.isEmpty()) {
                return context.getString(R.string.name_consists_of_spaces_only);
            }
            if (strTrim.matches("-?\\d+(\\.\\d+)?")) {
                return null;
            }
            return context.getString(R.string.only_numbers);
        }

        protected boolean isNameUnique(String name2) {
            return !this.scope.contains(name2);
        }
    }
}
