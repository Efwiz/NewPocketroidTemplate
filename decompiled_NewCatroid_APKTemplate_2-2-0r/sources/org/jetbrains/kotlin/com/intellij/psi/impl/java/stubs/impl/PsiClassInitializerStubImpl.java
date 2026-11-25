package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassInitializerStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public class PsiClassInitializerStubImpl extends StubBase<PsiClassInitializer> implements PsiClassInitializerStub {
    public PsiClassInitializerStubImpl(StubElement stubElement) {
        super(stubElement, JavaStubElementTypes.CLASS_INITIALIZER);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "PsiClassInitializerStub";
    }
}
