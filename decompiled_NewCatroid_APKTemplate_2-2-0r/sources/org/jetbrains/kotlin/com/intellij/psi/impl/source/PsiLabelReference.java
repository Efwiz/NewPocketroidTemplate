package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiLabeledStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiLabelReference implements PsiReference {
    private PsiIdentifier myIdentifier;
    private final PsiStatement myStatement;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 3 : 2];
        if (i == 2) {
            objArr[0] = "newElementName";
        } else if (i == 3 || i == 4) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiLabelReference";
        }
        if (i == 1) {
            objArr[1] = "getCanonicalText";
        } else if (i == 2 || i == 3 || i == 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiLabelReference";
        } else if (i != 5) {
            objArr[1] = "getElement";
        } else {
            objArr[1] = "getVariants";
        }
        if (i == 2) {
            objArr[2] = "handleElementRename";
        } else if (i == 3) {
            objArr[2] = "bindToElement";
        } else if (i == 4) {
            objArr[2] = "isReferenceTo";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        return false;
    }

    public PsiLabelReference(PsiStatement psiStatement, PsiIdentifier psiIdentifier) {
        this.myStatement = psiStatement;
        this.myIdentifier = psiIdentifier;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    /* JADX INFO: renamed from: getElement */
    public PsiElement getPsiElement() {
        PsiStatement psiStatement = this.myStatement;
        if (psiStatement == null) {
            $$$reportNull$$$0(0);
        }
        return psiStatement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        int startOffsetInParent = this.myIdentifier.getStartOffsetInParent();
        return new TextRange(startOffsetInParent, this.myIdentifier.getTextLength() + startOffsetInParent);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        String text = this.myIdentifier.getText();
        for (PsiElement context = this.myStatement; context != null; context = context.getContext()) {
            if ((context instanceof PsiLabeledStatement) && text.equals(((PsiLabeledStatement) context).get_name())) {
                return context;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        String text = getPsiElement().getText();
        if (text == null) {
            $$$reportNull$$$0(1);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        PsiIdentifier psiIdentifier = (PsiIdentifier) PsiImplUtil.setName(this.myIdentifier, str);
        this.myIdentifier = psiIdentifier;
        return psiIdentifier;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        if (!(psiElement instanceof PsiLabeledStatement)) {
            throw new IncorrectOperationException("Can't bind to non-labeled statement");
        }
        return handleElementRename(((PsiLabeledStatement) psiElement).get_name());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        return resolve() == psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String[] getVariants() {
        String[] stringArray = ArrayUtil.toStringArray(PsiImplUtil.findAllEnclosingLabels(this.myStatement));
        if (stringArray == null) {
            $$$reportNull$$$0(5);
        }
        return stringArray;
    }
}
