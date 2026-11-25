package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiProvidesStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiProvidesStatementStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public class PsiProvidesStatementStubImpl extends StubBase<PsiProvidesStatement> implements PsiProvidesStatementStub {
    private final String myInterface;

    public PsiProvidesStatementStubImpl(StubElement stubElement, String str) {
        super(stubElement, JavaStubElementTypes.PROVIDES_STATEMENT);
        this.myInterface = (String) ObjectUtils.notNull(str, "");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiProvidesStatementStub
    public String getInterface() {
        return this.myInterface;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "PsiProvidesStatementStub:" + this.myInterface;
    }
}
