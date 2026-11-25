package org.catrobat.catroid.formulaeditor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternFormulaTokenSelection.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class InternFormulaTokenSelection {
    private int internTokenSelectionEnd;
    private int internTokenSelectionStart;
    private InternFormula.TokenSelectionType tokenSelectionType;

    public InternFormulaTokenSelection(InternFormula.TokenSelectionType tokenSelectionType, int internTokenSelectionStart, int internTokenSelectionEnd) {
        this.tokenSelectionType = tokenSelectionType;
        this.internTokenSelectionStart = internTokenSelectionStart;
        this.internTokenSelectionEnd = internTokenSelectionEnd;
    }

    public int getStartIndex() {
        return this.internTokenSelectionStart;
    }

    public int getEndIndex() {
        return this.internTokenSelectionEnd;
    }

    public InternFormula.TokenSelectionType getTokenSelectionType() {
        return this.tokenSelectionType;
    }

    public boolean equals(Object objectToCompare) {
        if (objectToCompare instanceof InternFormulaTokenSelection) {
            InternFormulaTokenSelection internFormulaTokenSelection = (InternFormulaTokenSelection) objectToCompare;
            return this.internTokenSelectionStart == internFormulaTokenSelection.internTokenSelectionStart && this.internTokenSelectionEnd == internFormulaTokenSelection.internTokenSelectionEnd && this.tokenSelectionType == internFormulaTokenSelection.tokenSelectionType;
        }
        return super.equals(objectToCompare);
    }

    public int hashCode() {
        int i = ((1271 + this.internTokenSelectionStart) * 41) + this.internTokenSelectionEnd;
        InternFormula.TokenSelectionType tokenSelectionType = this.tokenSelectionType;
        return tokenSelectionType != null ? (i * 41) + tokenSelectionType.hashCode() : i;
    }

    public InternFormulaTokenSelection deepCopy() {
        return new InternFormulaTokenSelection(this.tokenSelectionType, this.internTokenSelectionStart, this.internTokenSelectionEnd);
    }
}
