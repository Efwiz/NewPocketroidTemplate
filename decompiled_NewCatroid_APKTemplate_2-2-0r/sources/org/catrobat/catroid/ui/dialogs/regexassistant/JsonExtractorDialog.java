package org.catrobat.catroid.ui.dialogs.regexassistant;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.InputWatcher;
import org.catrobat.catroid.utils.JsonRegexExtractor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/regexassistant/JsonExtractorDialog.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class JsonExtractorDialog extends RegularExpressionFeature {
    private FragmentManager fragmentManager;

    public JsonExtractorDialog(FragmentManager fragmentManager) {
        this.titleId = R.string.formula_editor_function_regex_json_extractor_title;
        this.fragmentManager = fragmentManager;
    }

    @Override // org.catrobat.catroid.ui.dialogs.regexassistant.RegularExpressionFeature
    public void openDialog(Context context) {
        createDialog(context);
    }

    public void createDialog(Context context) {
        TextInputDialog.Builder builder = new TextInputDialog.Builder(context);
        builder.setView(R.layout.dialog_regex_json_extractor);
        builder.setTitle(R.string.formula_editor_function_regex_json_extractor_title);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.setHint(context.getString(R.string.keyword_label));
        builder.setTextWatcher(new InputWatcher.TextWatcher() { // from class: org.catrobat.catroid.ui.dialogs.regexassistant.JsonExtractorDialog.1
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.InputWatcher.TextWatcher
            public String validateInput(String input, Context context2) {
                return null;
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.regexassistant.JsonExtractorDialog.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                JsonExtractorDialog.this.outputText(JsonRegexExtractor.getJsonParserRegex(((TextInputLayout) ((Dialog) dialog).findViewById(R.id.input)).getEditText().getText().toString()));
            }
        });
        builder.show();
    }

    private FormulaEditorFragment getFormulaEditorFragment() {
        FragmentManager fragmentManager = this.fragmentManager;
        if (fragmentManager != null) {
            return (FormulaEditorFragment) fragmentManager.findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void outputText(String text) {
        FormulaEditorFragment formulaEditorFragment = getFormulaEditorFragment();
        if (formulaEditorFragment != null) {
            formulaEditorFragment.addString(text);
        }
    }
}
