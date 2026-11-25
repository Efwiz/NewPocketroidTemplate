package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiSwitchLabelStatementBase extends PsiStatement {
    PsiCaseLabelElementList getCaseLabelElementList();

    @Deprecated
    PsiExpressionList getCaseValues();

    PsiSwitchBlock getEnclosingSwitchBlock();

    boolean isDefaultCase();

    @Deprecated
    default PsiExpression getCaseValue() {
        PsiExpressionList caseValues = getCaseValues();
        if (caseValues == null) {
            return null;
        }
        PsiExpression[] expressions = caseValues.getExpressions();
        if (expressions.length == 1) {
            return expressions[0];
        }
        return null;
    }

    @Deprecated
    default PsiSwitchStatement getEnclosingSwitchStatement() {
        PsiSwitchBlock enclosingSwitchBlock = getEnclosingSwitchBlock();
        if (enclosingSwitchBlock instanceof PsiSwitchStatement) {
            return (PsiSwitchStatement) enclosingSwitchBlock;
        }
        return null;
    }
}
