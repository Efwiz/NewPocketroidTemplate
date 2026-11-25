package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiPatternVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeTestPattern;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiTypeTestPatternImpl extends CompositePsiElement implements PsiTypeTestPattern, Constants {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5) ? 3 : 2];
        if (i == 2) {
            objArr[0] = "visitor";
        } else if (i == 3) {
            objArr[0] = "processor";
        } else if (i == 4) {
            objArr[0] = "state";
        } else if (i != 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiTypeTestPatternImpl";
        } else {
            objArr[0] = "place";
        }
        if (i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiTypeTestPatternImpl";
        } else {
            objArr[1] = "getCheckType";
        }
        if (i == 2) {
            objArr[2] = "accept";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[2] = "processDeclarations";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public PsiTypeTestPatternImpl() {
        super(TYPE_TEST_PATTERN);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeTestPattern
    public PsiTypeElement getCheckType() {
        for (PsiElement firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild instanceof PsiTypeElement) {
                PsiTypeElement psiTypeElement = (PsiTypeElement) firstChild;
                if (psiTypeElement == null) {
                    $$$reportNull$$$0(0);
                }
                return psiTypeElement;
            }
            if (firstChild instanceof PsiPatternVariable) {
                PsiTypeElement typeElement = ((PsiPatternVariable) firstChild).getTypeElement();
                if (typeElement == null) {
                    $$$reportNull$$$0(1);
                }
                return typeElement;
            }
        }
        throw new IllegalStateException(getText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeTestPattern
    public PsiPatternVariable getPatternVariable() {
        return (PsiPatternVariable) PsiTreeUtil.getChildOfType(this, PsiPatternVariable.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitTypeTestPattern(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(3);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(5);
        }
        psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, this);
        PsiPatternVariable patternVariable = getPatternVariable();
        if (patternVariable != psiElement) {
            return psiScopeProcessor.execute(patternVariable, resolveState);
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiTypeTestPattern";
    }
}
