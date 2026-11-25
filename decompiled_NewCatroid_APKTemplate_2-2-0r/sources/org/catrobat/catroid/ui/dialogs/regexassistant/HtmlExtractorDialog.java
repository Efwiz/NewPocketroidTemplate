package org.catrobat.catroid.ui.dialogs.regexassistant;

import android.content.Context;
import android.content.DialogInterface;
import androidx.fragment.app.FragmentManager;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.utils.HtmlRegexExtractor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/regexassistant/HtmlExtractorDialog.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HtmlExtractorDialog extends RegularExpressionFeature {
    private FragmentManager fragmentManager;

    public HtmlExtractorDialog(FragmentManager fragmentManager) {
        this.titleId = R.string.formula_editor_regex_html_extractor_dialog_title;
        this.fragmentManager = fragmentManager;
    }

    @Override // org.catrobat.catroid.ui.dialogs.regexassistant.RegularExpressionFeature
    public void openDialog(final Context context) {
        HtmlExtractorInputDialog.Builder builder = new HtmlExtractorInputDialog.Builder(context);
        builder.setKeywordHint(R.string.keyword_label);
        builder.setHtmlHint(R.string.html_label);
        builder.setPositiveButton(R.string.ok, new HtmlExtractorInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.regexassistant.HtmlExtractorDialog.1
            @Override // org.catrobat.catroid.ui.dialogs.regexassistant.HtmlExtractorInputDialog.OnClickListener
            public void onPositiveButtonClick(DialogInterface dialog, String keywordInput, String htmlInput) {
                HtmlExtractorDialog.this.outputText(new HtmlRegexExtractor(context).searchKeyword(keywordInput, htmlInput));
            }
        });
        builder.setTitle(R.string.formula_editor_regex_html_extractor_dialog_title);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
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
        if (formulaEditorFragment == null || text.equals("")) {
            return;
        }
        formulaEditorFragment.addString(text);
    }
}
