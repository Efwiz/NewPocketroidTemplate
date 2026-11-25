package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiProvidesStatement extends PsiStatement {
    public static final PsiProvidesStatement[] EMPTY_ARRAY = new PsiProvidesStatement[0];

    PsiReferenceList getImplementationList();

    PsiJavaCodeReferenceElement getInterfaceReference();

    PsiClassType getInterfaceType();
}
