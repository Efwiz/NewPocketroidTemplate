package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;

/* JADX INFO: loaded from: classes3.dex */
public class LightExpressionList extends LightElement implements PsiExpressionList {
    private final PsiElement myContext;
    private final PsiExpression[] myExpressions;
    private final TextRange myRange;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "language";
        } else if (i == 2) {
            objArr[0] = "context";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightExpressionList";
        } else {
            objArr[0] = "manager";
        }
        if (i == 3) {
            objArr[1] = "getExpressions";
        } else if (i == 4) {
            objArr[1] = "getExpressionTypes";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightExpressionList";
        } else {
            objArr[1] = "getContext";
        }
        if (i != 3 && i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightExpressionList(PsiManager psiManager, Language language, PsiExpression[] psiExpressionArr, PsiElement psiElement, TextRange textRange) {
        super(psiManager, language);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (language == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        this.myExpressions = psiExpressionArr;
        this.myRange = textRange;
        this.myContext = psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList
    public PsiExpression[] getExpressions() {
        PsiExpression[] psiExpressionArr = this.myExpressions;
        if (psiExpressionArr == null) {
            $$$reportNull$$$0(3);
        }
        return psiExpressionArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList
    public PsiType[] getExpressionTypes() {
        PsiExpression[] expressions = getExpressions();
        PsiType[] psiTypeArrCreateArray = PsiType.createArray(expressions.length);
        for (int i = 0; i < psiTypeArrCreateArray.length; i++) {
            psiTypeArrCreateArray[i] = expressions[i].getType();
        }
        if (psiTypeArrCreateArray == null) {
            $$$reportNull$$$0(4);
        }
        return psiTypeArrCreateArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        return this.myRange;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        PsiElement psiElement = this.myContext;
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        return psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiExpressionList";
    }
}
