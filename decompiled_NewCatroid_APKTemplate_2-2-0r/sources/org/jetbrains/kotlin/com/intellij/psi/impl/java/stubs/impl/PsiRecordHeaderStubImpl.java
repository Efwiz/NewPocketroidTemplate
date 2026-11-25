package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiRecordHeader;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRecordHeaderStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public class PsiRecordHeaderStubImpl extends StubBase<PsiRecordHeader> implements PsiRecordHeaderStub {
    public PsiRecordHeaderStubImpl(StubElement stubElement) {
        super(stubElement, JavaStubElementTypes.RECORD_HEADER);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "PsiRecordHeaderStub";
    }
}
