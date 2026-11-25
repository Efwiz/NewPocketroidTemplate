package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.com.intellij.psi.ImplicitVariable;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ImplicitVariableImpl extends LightVariableBase implements ImplicitVariable {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "visitor", "org/jetbrains/kotlin/com/intellij/psi/impl/light/ImplicitVariableImpl", "accept"));
    }

    public ImplicitVariableImpl(PsiManager psiManager, PsiIdentifier psiIdentifier, PsiType psiType, boolean z, PsiElement psiElement) {
        super(psiManager, psiIdentifier, psiType, z, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitImplicitVariable(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "Implicit variable:" + getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    /* JADX INFO: renamed from: setInitializer */
    public void mo6096setInitializer(PsiExpression psiExpression) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        return new LocalSearchScope(getDeclarationScope());
    }
}
