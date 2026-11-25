package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public interface StubBasedPsiElement<Stub extends StubElement> extends PsiElement {
    IStubElementType getElementType();

    Stub getStub();
}
