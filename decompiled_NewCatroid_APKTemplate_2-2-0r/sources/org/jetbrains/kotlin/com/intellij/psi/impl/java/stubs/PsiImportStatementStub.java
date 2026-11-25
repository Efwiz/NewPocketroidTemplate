package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiImportStatementStub extends StubElement<PsiImportStatementBase> {
    String getImportReferenceText();

    PsiJavaCodeReferenceElement getReference();

    boolean isOnDemand();

    boolean isStatic();
}
