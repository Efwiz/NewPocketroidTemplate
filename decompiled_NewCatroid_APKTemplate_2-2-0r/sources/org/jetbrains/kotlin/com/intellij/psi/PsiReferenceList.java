package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiReferenceList extends PsiElement {
    public static final PsiReferenceList[] EMPTY_ARRAY = new PsiReferenceList[0];

    public enum Role {
        THROWS_LIST,
        EXTENDS_LIST,
        IMPLEMENTS_LIST,
        PERMITS_LIST,
        EXTENDS_BOUNDS_LIST,
        PROVIDES_WITH_LIST
    }

    PsiJavaCodeReferenceElement[] getReferenceElements();

    PsiClassType[] getReferencedTypes();

    Role getRole();
}
