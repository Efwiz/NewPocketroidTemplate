package org.catrobat.catroid.utils.git.merger;

import kotlin.Metadata;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/git/merger/BrickComparatorKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: BrickComparator.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u0004\u0018\u00010\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¨\u0006\u0004"}, d2 = {"getLiteralValue", "", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BrickComparatorKt {
    public static final String getLiteralValue(Formula formula) {
        if (formula == null) {
            return null;
        }
        FormulaElement formulaTree = formula.getFormulaTree();
        if (formulaTree.getElementType() == FormulaElement.ElementType.NUMBER || formulaTree.getElementType() == FormulaElement.ElementType.STRING) {
            return formulaTree.getValue();
        }
        return null;
    }
}
