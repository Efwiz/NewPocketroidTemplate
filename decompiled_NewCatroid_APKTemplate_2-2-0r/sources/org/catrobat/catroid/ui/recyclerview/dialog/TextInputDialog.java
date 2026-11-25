package org.catrobat.catroid.ui.recyclerview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.ui.ViewUtils;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.InputWatcher;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/TextInputDialog.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class TextInputDialog extends AlertDialog {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/TextInputDialog$OnClickListener.class
      classes14.dex
     */
    public interface OnClickListener {
        void onPositiveButtonClick(DialogInterface dialog, String textInput);
    }

    private TextInputDialog(Context context) {
        super(context);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/TextInputDialog$Builder.class
      classes14.dex
     */
    public static class Builder extends AlertDialog.Builder {
        private String hint;
        private String text;
        private InputWatcher.TextWatcher textWatcher;

        public Builder(Context context) {
            super(context);
            setView(R.layout.dialog_text_input);
        }

        public Builder setHint(String hint) {
            this.hint = hint;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setTextWatcher(InputWatcher.TextWatcher textWatcher) {
            this.textWatcher = textWatcher;
            return this;
        }

        public Builder setPositiveButton(String buttonText, final OnClickListener listener) {
            setPositiveButton(buttonText, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog$Builder$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    listener.onPositiveButtonClick(dialogInterface, ((TextInputLayout) ((Dialog) dialogInterface).findViewById(R.id.input)).getEditText().getText().toString());
                }
            });
            return this;
        }

        @Override // androidx.appcompat.app.AlertDialog.Builder
        public AlertDialog create() {
            final AlertDialog alertDialogCreate = super.create();
            alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog$Builder$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    this.f$0.m5756xb235e4ca(alertDialogCreate, dialogInterface);
                }
            });
            return alertDialogCreate;
        }

        /* JADX INFO: renamed from: lambda$create$1$org-catrobat-catroid-ui-recyclerview-dialog-TextInputDialog$Builder, reason: not valid java name */
        /* synthetic */ void m5756xb235e4ca(AlertDialog alertDialog, DialogInterface dialogInterface) {
            TextInputLayout textInputLayout = (TextInputLayout) alertDialog.findViewById(R.id.input);
            EditText editText = textInputLayout.getEditText();
            textInputLayout.setHint(this.hint);
            if (this.textWatcher != null) {
                textInputLayout.getEditText().addTextChangedListener(this.textWatcher);
                this.textWatcher.setInputLayout(textInputLayout);
                this.textWatcher.setButton(alertDialog.getButton(-1));
                this.textWatcher.setContext(getContext());
            }
            editText.setText(this.text);
            editText.selectAll();
            ViewUtils.showKeyboard(editText);
        }

        public UniqueNameProvider createUniqueNameProvider(int stringID) {
            if (stringID == R.string.default_list_name) {
                return new UniqueNameProvider() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder.1
                    @Override // org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider, org.catrobat.catroid.ui.recyclerview.util.UniqueNameProviderInterface
                    public boolean isUnique(String newName) {
                        return ProjectManager.getInstance().getCurrentProject().getUserList(newName) == null && ProjectManager.getInstance().getCurrentSprite().getUserList(newName) == null;
                    }
                };
            }
            if (stringID == R.string.default_variable_name) {
                return new UniqueNameProvider() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder.2
                    @Override // org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider, org.catrobat.catroid.ui.recyclerview.util.UniqueNameProviderInterface
                    public boolean isUnique(String newName) {
                        return ProjectManager.getInstance().getCurrentProject().getUserVariable(newName) == null && ProjectManager.getInstance().getCurrentProject().getMultiplayerVariable(newName) == null && ProjectManager.getInstance().getCurrentSprite().getUserVariable(newName) == null;
                    }
                };
            }
            return new UniqueNameProvider();
        }
    }
}
