package org.catrobat.catroid.ui.dialogs.regexassistant;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.ViewUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/regexassistant/HtmlExtractorInputDialog.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class HtmlExtractorInputDialog extends AlertDialog {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/regexassistant/HtmlExtractorInputDialog$OnClickListener.class
      classes14.dex
     */
    public interface OnClickListener {
        void onPositiveButtonClick(DialogInterface dialog, String keywordInput, String htmlInput);
    }

    private HtmlExtractorInputDialog(Context context) {
        super(context);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/regexassistant/HtmlExtractorInputDialog$Builder.class
      classes14.dex
     */
    public static class Builder extends AlertDialog.Builder {
        private String htmlHint;
        private String htmlText;
        private String keyword;
        private String keywordHint;
        private TextWatcherHtml textWatcherHtml;
        private TextWatcher textWatcherKeyword;

        public Builder(Context context) {
            super(context);
            setView(R.layout.dialog_regex_html_extractor);
        }

        public Builder setKeywordHint(String keywordHint) {
            this.keywordHint = keywordHint;
            return this;
        }

        public Builder setKeywordHint(int id2) {
            setKeywordHint(getContext().getText(id2).toString());
            return this;
        }

        public Builder setKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public Builder setHtmlHint(String htmlHint) {
            this.htmlHint = htmlHint;
            return this;
        }

        public Builder setHtmlHint(int id2) {
            setHtmlHint(getContext().getText(id2).toString());
            return this;
        }

        public Builder setHtmlText(String htmlText) {
            this.htmlText = htmlText;
            return this;
        }

        public Builder setPositiveButton(String text, final OnClickListener listener) {
            setPositiveButton(text, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.regexassistant.HtmlExtractorInputDialog.Builder.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    Dialog dialog2 = (Dialog) dialog;
                    TextInputLayout textInputLayout = (TextInputLayout) dialog2.findViewById(R.id.input);
                    TextInputLayout textInputLayout2 = (TextInputLayout) dialog2.findViewById(R.id.input1);
                    listener.onPositiveButtonClick(dialog, textInputLayout.getEditText().getText().toString(), textInputLayout2.getEditText().getText().toString());
                }
            });
            return this;
        }

        public Builder setPositiveButton(int id2, final OnClickListener listener) {
            return setPositiveButton(getContext().getText(id2).toString(), listener);
        }

        @Override // androidx.appcompat.app.AlertDialog.Builder
        public AlertDialog create() {
            final AlertDialog alertDialogCreate = super.create();
            alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.catrobat.catroid.ui.dialogs.regexassistant.HtmlExtractorInputDialog$Builder$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    this.f$0.m5681xe8d53ed(alertDialogCreate, dialogInterface);
                }
            });
            return alertDialogCreate;
        }

        /* JADX INFO: renamed from: lambda$create$0$org-catrobat-catroid-ui-dialogs-regexassistant-HtmlExtractorInputDialog$Builder, reason: not valid java name */
        /* synthetic */ void m5681xe8d53ed(AlertDialog alertDialog, DialogInterface dialogInterface) {
            TextInputLayout textInputLayout = (TextInputLayout) alertDialog.findViewById(R.id.input);
            textInputLayout.setHint(this.keywordHint);
            textInputLayout.getEditText().setText(this.keyword);
            textInputLayout.getEditText().selectAll();
            TextInputLayout textInputLayout2 = (TextInputLayout) alertDialog.findViewById(R.id.input1);
            textInputLayout2.setHint(this.htmlHint);
            textInputLayout2.getEditText().setText(this.htmlText);
            textInputLayout2.getEditText().selectAll();
            if (this.textWatcherKeyword != null) {
                textInputLayout.getEditText().addTextChangedListener(this.textWatcherKeyword);
                this.textWatcherKeyword.setInputLayout(textInputLayout);
                this.textWatcherKeyword.setAlertDialog(alertDialog);
                alertDialog.getButton(-1).setEnabled(this.textWatcherKeyword.validateInput(textInputLayout.getEditText().getText().toString(), getContext()) == null);
            }
            if (this.textWatcherHtml != null) {
                textInputLayout.getEditText().addTextChangedListener(this.textWatcherHtml);
                this.textWatcherHtml.setInputLayout(textInputLayout2);
                this.textWatcherHtml.setAlertDialog(alertDialog);
                alertDialog.getButton(-1).setEnabled(this.textWatcherHtml.validateInput(textInputLayout.getEditText().getText().toString(), getContext()) == null);
            }
            ViewUtils.showKeyboard(textInputLayout.getEditText());
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/regexassistant/HtmlExtractorInputDialog$TextWatcher.class
      classes14.dex
     */
    public static abstract class TextWatcher implements android.text.TextWatcher {
        private AlertDialog alertDialog;
        private TextInputLayout inputLayout;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public abstract String validateInput(String input, Context context);

        /* JADX INFO: Access modifiers changed from: private */
        public void setInputLayout(TextInputLayout inputLayout) {
            this.inputLayout = inputLayout;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAlertDialog(AlertDialog alertDialog) {
            this.alertDialog = alertDialog;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable s) {
            String strValidateInput = validateInput(s.toString(), this.alertDialog.getContext());
            this.inputLayout.setError(strValidateInput);
            this.alertDialog.getButton(-1).setEnabled(strValidateInput == null);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/regexassistant/HtmlExtractorInputDialog$TextWatcherHtml.class
      classes14.dex
     */
    public static abstract class TextWatcherHtml implements android.text.TextWatcher {
        private AlertDialog alertDialog;
        private TextInputLayout inputLayout;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public abstract String validateInput(String input, Context context);

        /* JADX INFO: Access modifiers changed from: private */
        public void setInputLayout(TextInputLayout inputLayout) {
            this.inputLayout = inputLayout;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAlertDialog(AlertDialog alertDialog) {
            this.alertDialog = alertDialog;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable s) {
            String strValidateInput = validateInput(s.toString(), this.alertDialog.getContext());
            this.inputLayout.setError(strValidateInput);
            this.alertDialog.getButton(-1).setEnabled(strValidateInput == null);
        }
    }
}
