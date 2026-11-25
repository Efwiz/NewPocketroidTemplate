package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import org.jetbrains.kotlin.com.intellij.psi.PlainTextTokenTypes;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiPlainText;

/* JADX INFO: loaded from: classes3.dex */
public class PsiPlainTextImpl extends OwnBufferLeafPsiElement implements PsiPlainText {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "text";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/PsiPlainTextImpl";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected PsiPlainTextImpl(CharSequence charSequence) {
        super(PlainTextTokenTypes.PLAIN_TEXT, charSequence);
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.OwnBufferLeafPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        psiElementVisitor.visitPlainText(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.OwnBufferLeafPsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiPlainText";
    }
}
