package org.jetbrains.kotlin.com.intellij.psi.stubs;

import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;

/* JADX INFO: loaded from: classes3.dex */
public interface NamedStub<T extends PsiNamedElement> extends StubElement<T> {
    String getName();
}
