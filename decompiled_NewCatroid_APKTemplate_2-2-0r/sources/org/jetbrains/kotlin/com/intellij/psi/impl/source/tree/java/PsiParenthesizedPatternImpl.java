package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiPattern;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiParenthesizedPatternImpl extends CompositePsiElement implements PsiParenthesizedPattern, Constants {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "processor";
        } else if (i == 2) {
            objArr[0] = "state";
        } else if (i != 3) {
            objArr[0] = "visitor";
        } else {
            objArr[0] = "place";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiParenthesizedPatternImpl";
        if (i == 1 || i == 2 || i == 3) {
            objArr[2] = "processDeclarations";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiParenthesizedPatternImpl() {
        super(PARENTHESIZED_PATTERN);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitParenthesizedPattern(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiParenthesizedPattern";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedPattern
    public PsiPattern getPattern() {
        return (PsiPattern) PsiTreeUtil.getChildOfType(this, PsiPattern.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
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
        PsiPattern pattern = getPattern();
        if (pattern == null) {
            return true;
        }
        return pattern.processDeclarations(psiScopeProcessor, resolveState, null, psiElement2);
    }
}
