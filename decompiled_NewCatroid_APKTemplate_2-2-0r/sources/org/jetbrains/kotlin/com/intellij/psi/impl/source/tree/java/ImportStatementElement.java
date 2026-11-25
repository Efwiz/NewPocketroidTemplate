package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;

/* JADX INFO: loaded from: classes3.dex */
public class ImportStatementElement extends ImportStatementBaseElement {
    public ImportStatementElement() {
        super(JavaElementType.IMPORT_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ImportStatementBaseElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        ASTNode aSTNodeFindChildByRole = super.findChildByRole(i);
        if (aSTNodeFindChildByRole != null) {
            return aSTNodeFindChildByRole;
        }
        if (i == 99) {
            return findChildByType(JavaElementType.JAVA_CODE_REFERENCE);
        }
        return null;
    }
}
