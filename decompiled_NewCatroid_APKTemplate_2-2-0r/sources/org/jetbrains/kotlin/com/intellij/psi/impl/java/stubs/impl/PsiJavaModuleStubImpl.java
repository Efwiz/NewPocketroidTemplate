package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaModuleStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public class PsiJavaModuleStubImpl extends StubBase<PsiJavaModule> implements PsiJavaModuleStub {
    private final String myName;

    public PsiJavaModuleStubImpl(StubElement stubElement, String str) {
        super(stubElement, JavaStubElementTypes.MODULE);
        this.myName = str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaModuleStub
    public String getName() {
        return this.myName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "PsiJavaModuleStub:" + getName();
    }
}
