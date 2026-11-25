package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;

/* JADX INFO: loaded from: classes3.dex */
public class PsiJavaModuleReferenceElementImpl extends CompositePsiElement implements PsiJavaModuleReferenceElement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 1 ? 2 : 3];
        if (i != 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaModuleReferenceElementImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i != 1) {
            objArr[1] = "getReferenceText";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaModuleReferenceElementImpl";
        }
        if (i == 1) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalArgumentException(str2);
        }
    }

    public PsiJavaModuleReferenceElementImpl() {
        super(JavaElementType.MODULE_REFERENCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReferenceElement
    public String getReferenceText() {
        StringBuilder sb = new StringBuilder();
        for (PsiElement firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (!(firstChild instanceof PsiWhiteSpace) && !(firstChild instanceof PsiComment)) {
                sb.append(firstChild.getText());
            }
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(0);
        }
        return string;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiJavaModuleReference getReference() {
        if (!(getMyParent() instanceof PsiJavaModule) || (getContainingFile() instanceof JavaDummyHolder)) {
            return (PsiJavaModuleReference) CachedValuesManager.getCachedValue((PsiElement) this, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaModuleReferenceElementImpl$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                public final CachedValueProvider.Result compute() {
                    return this.f$0.m6655xe92d47b5();
                }
            });
        }
        return null;
    }

    /* JADX INFO: renamed from: lambda$getReference$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-PsiJavaModuleReferenceElementImpl, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6655xe92d47b5() {
        return CachedValueProvider.Result.create(new PsiJavaModuleReferenceImpl(this), this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitModuleReferenceElement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiJavaModuleReference";
    }
}
