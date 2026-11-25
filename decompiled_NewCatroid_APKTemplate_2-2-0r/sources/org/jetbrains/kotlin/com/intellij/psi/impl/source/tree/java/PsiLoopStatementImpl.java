package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiLoopStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
abstract class PsiLoopStatementImpl extends CompositePsiElement implements PsiLoopStatement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "child", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiLoopStatementImpl", "deleteChildInternal"));
    }

    protected PsiLoopStatementImpl(IElementType iElementType) {
        super(iElementType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        if (aSTNode == getBody()) {
            replaceChildInternal(aSTNode, (TreeElement) JavaPsiFacade.getElementFactory(getProject()).createStatementFromText("{}", null));
        } else {
            super.deleteChildInternal(aSTNode);
        }
    }
}
