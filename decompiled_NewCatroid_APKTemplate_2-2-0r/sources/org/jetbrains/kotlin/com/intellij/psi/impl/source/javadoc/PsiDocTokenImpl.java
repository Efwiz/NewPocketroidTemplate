package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.JavaDocTokenType;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocToken;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiDocTokenImpl extends LeafPsiElement implements PsiDocToken {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "visitor";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocTokenImpl";
        } else {
            objArr[0] = ModuleXmlParser.TYPE;
        }
        if (i == 2 || i == 3) {
            objArr[1] = "getReferences";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocTokenImpl";
        }
        if (i == 1) {
            objArr[2] = "accept";
        } else if (i != 2 && i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiDocTokenImpl(IElementType iElementType, CharSequence charSequence) {
        super(iElementType, charSequence);
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocToken
    public IElementType getTokenType() {
        return getElementType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitDocToken(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference[] getReferences() {
        if (getTokenType() == JavaDocTokenType.DOC_COMMENT_DATA) {
            PsiReference[] referencesFromProviders = ReferenceProvidersRegistry.getReferencesFromProviders(this);
            if (referencesFromProviders == null) {
                $$$reportNull$$$0(2);
            }
            return referencesFromProviders;
        }
        PsiReference[] references = super.getReferences();
        if (references == null) {
            $$$reportNull$$$0(3);
        }
        return references;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiDocToken:" + getTokenType().toString();
    }
}
