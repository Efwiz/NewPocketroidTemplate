package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;

/* JADX INFO: loaded from: classes3.dex */
public class ImportStaticStatementElement extends ImportStatementBaseElement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "child", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/ImportStaticStatementElement", "getChildRole"));
    }

    public ImportStaticStatementElement() {
        super(JavaElementType.IMPORT_STATIC_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ImportStatementBaseElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        ASTNode aSTNodeFindChildByRole = super.findChildByRole(i);
        if (aSTNodeFindChildByRole != null) {
            return aSTNodeFindChildByRole;
        }
        if (i != 99) {
            return null;
        }
        ASTNode aSTNodeFindChildByType = findChildByType(JavaElementType.IMPORT_STATIC_REFERENCE);
        return aSTNodeFindChildByType != null ? aSTNodeFindChildByType : findChildByType(JavaElementType.JAVA_CODE_REFERENCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ImportStatementBaseElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        int childRole = super.getChildRole(aSTNode);
        if (childRole != 0) {
            return childRole;
        }
        return aSTNode.getElementType() == JavaElementType.IMPORT_STATIC_REFERENCE ? 99 : 0;
    }
}
