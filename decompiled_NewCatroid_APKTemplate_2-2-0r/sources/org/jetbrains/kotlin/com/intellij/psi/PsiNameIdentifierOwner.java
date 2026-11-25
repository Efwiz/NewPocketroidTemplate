package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiNameIdentifierOwner extends PsiNamedElement {
    /* JADX INFO: renamed from: getNameIdentifier */
    PsiElement mo7392getNameIdentifier();

    default PsiElement getIdentifyingElement() {
        return mo7392getNameIdentifier();
    }
}
