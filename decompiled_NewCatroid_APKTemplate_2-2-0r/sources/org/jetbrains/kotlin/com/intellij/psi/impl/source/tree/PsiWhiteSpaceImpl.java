package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiWhiteSpaceImpl extends LeafPsiElement implements PsiWhiteSpace {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "visitor";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/PsiWhiteSpaceImpl";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/PsiWhiteSpaceImpl";
        } else {
            objArr[1] = "getLanguage";
        }
        if (i != 1) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public PsiWhiteSpaceImpl(CharSequence charSequence) {
        super(TokenType.WHITE_SPACE, charSequence);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        psiElementVisitor.visitWhiteSpace(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiWhiteSpace";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        PsiElement parent = getLightParent();
        Language language = parent != null ? parent.getLanguage() : Language.ANY;
        if (language == null) {
            $$$reportNull$$$0(1);
        }
        return language;
    }
}
