package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class LightIdentifier extends LightElement implements PsiIdentifier, PsiJavaToken {
    private final String myText;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "visitor", "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightIdentifier", "accept"));
    }

    public LightIdentifier(PsiManager psiManager, String str) {
        super(psiManager, JavaLanguage.INSTANCE);
        this.myText = str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken, org.jetbrains.kotlin.com.intellij.psi.PsiComment
    public IElementType getTokenType() {
        return JavaTokenType.IDENTIFIER;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return this.myText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitIdentifier(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return new LightIdentifier(getManager(), this.myText);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiIdentifier:" + getText();
    }
}
