package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiVariable extends PomRenameableTarget<PsiElement>, PsiModifierListOwner, PsiNameIdentifierOwner, PsiTarget {
    Object computeConstantValue();

    PsiExpression getInitializer();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    PsiIdentifier mo7392getNameIdentifier();

    /* JADX INFO: renamed from: getType */
    PsiType mo6066getType();

    PsiTypeElement getTypeElement();

    boolean hasInitializer();

    void normalizeDeclaration() throws IncorrectOperationException;

    @Override // org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    PsiElement setName(String str) throws IncorrectOperationException;

    /* JADX INFO: renamed from: setInitializer */
    default void mo6096setInitializer(PsiExpression psiExpression) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }
}
