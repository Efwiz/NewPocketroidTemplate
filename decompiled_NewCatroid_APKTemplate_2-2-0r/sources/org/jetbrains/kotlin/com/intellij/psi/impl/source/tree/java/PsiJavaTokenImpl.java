package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiJavaTokenImpl extends LeafPsiElement implements PsiJavaToken {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiJavaTokenImpl";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiJavaTokenImpl(IElementType iElementType, CharSequence charSequence) {
        super(iElementType, charSequence);
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken, org.jetbrains.kotlin.com.intellij.psi.PsiComment
    public IElementType getTokenType() {
        return getElementType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitJavaToken(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiJavaToken:" + getElementType().toString();
    }
}
