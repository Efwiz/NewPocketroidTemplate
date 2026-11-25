package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiClassReferenceListStub extends StubElement<PsiReferenceList> {
    String[] getReferencedNames();

    PsiClassType[] getReferencedTypes();

    PsiReferenceList.Role getRole();

    TypeInfo[] getTypes();
}
