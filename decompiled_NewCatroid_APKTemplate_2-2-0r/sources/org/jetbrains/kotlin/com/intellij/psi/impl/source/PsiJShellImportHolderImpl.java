package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJShellImportHolder;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiJShellImportHolderImpl extends ASTWrapperPsiElement implements PsiJShellImportHolder {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "processor";
        } else if (i == 2) {
            objArr[0] = "state";
        } else if (i != 3) {
            objArr[0] = "node";
        } else {
            objArr[0] = "place";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJShellImportHolderImpl";
        if (i == 1 || i == 2 || i == 3) {
            objArr[2] = "processDeclarations";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiJShellImportHolderImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(1);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(3);
        }
        PsiImportStatement importStatement = getImportStatement();
        if (importStatement != null) {
            psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, this);
            return psiScopeProcessor.execute(importStatement, resolveState);
        }
        return PsiScopesUtil.walkChildrenScopes(this, psiScopeProcessor, resolveState, psiElement, psiElement2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJShellImportHolder
    public PsiImportStatement getImportStatement() {
        return (PsiImportStatement) PsiTreeUtil.getChildOfType(this, PsiImportStatement.class);
    }
}
