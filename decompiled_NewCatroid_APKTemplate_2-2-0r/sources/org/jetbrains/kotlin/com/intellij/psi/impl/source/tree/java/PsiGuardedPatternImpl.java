package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiGuardedPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimaryPattern;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiGuardedPatternImpl extends CompositePsiElement implements PsiGuardedPattern, Constants {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiGuardedPatternImpl";
        } else if (i == 2) {
            objArr[0] = "processor";
        } else if (i == 3) {
            objArr[0] = "state";
        } else if (i != 4) {
            objArr[0] = "visitor";
        } else {
            objArr[0] = "place";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiGuardedPatternImpl";
        } else {
            objArr[1] = "getPrimaryPattern";
        }
        if (i != 1) {
            if (i == 2 || i == 3 || i == 4) {
                objArr[2] = "processDeclarations";
            } else {
                objArr[2] = "accept";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public PsiGuardedPatternImpl() {
        super(GUARDED_PATTERN);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitGuardedPattern(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiGuardedPattern";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiGuardedPattern
    public PsiPrimaryPattern getPrimaryPattern() {
        PsiPrimaryPattern psiPrimaryPattern = (PsiPrimaryPattern) Objects.requireNonNull((PsiPrimaryPattern) PsiTreeUtil.getChildOfType(this, PsiPrimaryPattern.class));
        if (psiPrimaryPattern == null) {
            $$$reportNull$$$0(1);
        }
        return psiPrimaryPattern;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiGuardedPattern
    public PsiExpression getGuardingExpression() {
        return (PsiExpression) PsiTreeUtil.getChildOfType(this, PsiExpression.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(2);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(4);
        }
        if (!getPrimaryPattern().processDeclarations(psiScopeProcessor, resolveState, null, psiElement2)) {
            return false;
        }
        PsiExpression guardingExpression = getGuardingExpression();
        if (guardingExpression == null) {
            return true;
        }
        return guardingExpression.processDeclarations(psiScopeProcessor, resolveState, psiElement, psiElement2);
    }
}
